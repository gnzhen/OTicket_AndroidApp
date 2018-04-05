package com.example.gd.oticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gd.oticket.myrequest.MyRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator validator;
    // UI references.
    @Email
    private EditText loginEmail;
    @Password
    private EditText loginPassword;
    private View progressBar;
    private View loginForm;
    private Button signInBtn, registerBtn;
    private MyRequest request;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Toast toast;
    private FrameLayout progressBarHolder;
    private ProgressBar spinner;
    private MyFirebaseInstanceIdService myFirebaseInstanceIdService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();
        checkAuth();

        // Validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        // Set up the login form.
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        signInBtn = findViewById(R.id.login_sign_in_button);
        registerBtn = findViewById(R.id.login_register_button);
        loginForm = findViewById(R.id.login_form_scroll_view);
        progressBar = findViewById(R.id.login_progress);
        progressBarHolder = findViewById(R.id.progressBarHolder);
        spinner = findViewById(R.id.login_progress);

        request = new MyRequest(this);
        myFirebaseInstanceIdService = new MyFirebaseInstanceIdService();
        showSpinner(false);

        signInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                validator.validate();
            }
        });

        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();

        checkAuth();
    }

    @Override
    public void onValidationSucceeded() {
        final String email = loginEmail.getText().toString().trim();
        final String password = loginPassword.getText().toString().trim();

        login(email, password);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void login(String email, String password){

        showSpinner(true);

        /* Login */
        request.login(email, password, new MyRequest.VolleyCallback(){

            @Override
            public void onSuccess(String result) {
                JSONObject jResponse = null;
                try {
                    jResponse = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(jResponse.has("success")) {

                        saveCredentials(
                                jResponse.getJSONObject("user").get("id").toString(),
                                jResponse.getJSONObject("user").get("name").toString(),
                                jResponse.getJSONObject("user").get("email").toString()
                        );

                        checkAuth();
                        showToast(jResponse.get("success").toString());

                    } else if(jResponse.has("fail")){

                        showToast(jResponse.get("fail").toString());
                    } else {
                        //Show first validation error from server
                        Iterator<String> keys = jResponse.keys();
                        String str_Name = keys.next();
                        JSONArray value = null;
                        try {
                            value = jResponse.getJSONArray(str_Name);
                            showToast(value.get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showSpinner(false);
            }
            @Override
            public void onFailure(String error) {
                Log.d("onFailure", error);
                showToast(error);
                showSpinner(false);
            }
        });
    }

    public void saveCredentials(String id, String name, String email){

        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.commit();
    }

    public void checkAuth(){
        if(pref.getString("id", null) != null){
            Log.d("check Auth login", "user alredy login");
            myFirebaseInstanceIdService.sendTokenToServer(pref.getString("id", null), FirebaseInstanceId.getInstance().getToken());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            Log.d("check Auth Login", "user haven't login");
        }
    }

    public void showToast(String text){
        toast = Toast.makeText(getApplicationContext() ,text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }

    public void showSpinner(boolean show){
        if(show) {
            Log.d("spinner", "true");
            spinner.setVisibility(View.VISIBLE);
            progressBarHolder.setVisibility(View.VISIBLE);

            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    //
                }

                public void onFinish() {
                    showSpinner(false);
                }
            }.start();
        }
        else {
            Log.d("spinner", "false");
            spinner.setVisibility(View.GONE);
            progressBarHolder.setVisibility(View.GONE);
        }
    }
}

