package com.example.gd.oticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gd.oticket.myrequest.MyRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator validator;

    // UI references.
    @NotEmpty
    private EditText regName;
    @Email
    private EditText regEmail;
    @Length(min = 9, max = 12, message = "Input must be between 9 and 12 digits")
    private EditText regPhone;
    @Password(min = 8, message = "Password minimum 8 characters")
    private EditText regPassword;
    @ConfirmPassword
    private EditText regPasswordConfirm;

    private View progressBar;
    private View loginForm;
    private Button createBtn, backLoginBtn;
    private RequestQueue queue;
    private MyRequest request;
    private Toast toast;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pref = getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE);
        editor = pref.edit();
        checkAuth();

        // Validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        // Set up the login form.
        regName = findViewById(R.id.register_name);
        regEmail = findViewById(R.id.register_email);
        regPhone = findViewById(R.id.register_phone);
        regPassword = findViewById(R.id.register_password);
        regPasswordConfirm = findViewById(R.id.register_password_confirm);
        createBtn = findViewById(R.id.register_create_button);
        loginForm = findViewById(R.id.register_form_scroll_view);
        progressBar = findViewById(R.id.register_progress);
        backLoginBtn = findViewById(R.id.register_back_button);

        // Get a RequestQueue
//        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this);

        createBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                validator.validate();
            }
        });

        backLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

        String name = regName.getText().toString();
        String phone = regPhone.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        String passwordConfirm = regPasswordConfirm.getText().toString().trim();

        register(name, email, phone, password, passwordConfirm);
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

    public void register(String name, String email, String phone, String password, String passwordConfirm){
        /* Register */
        request.register(name, email, phone, password, passwordConfirm, new MyRequest.VolleyCallback(){

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
            Log.d("check Auth Register", "user already login");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        } else{
            Log.d("check Auth Register", "user haven't login");
        }
    }

    public void showToast(String text){
        toast = Toast.makeText(getApplicationContext() ,text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }
}

