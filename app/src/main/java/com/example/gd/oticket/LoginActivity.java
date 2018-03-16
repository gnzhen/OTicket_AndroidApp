package com.example.gd.oticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
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
    @Password(min = 8, message = "Password minimum 8 characters")
    private EditText loginPassword;
    private View progressBar;
    private View loginForm;
    private Button signInBtn, registerBtn;
    private MyRequest request;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        request = new MyRequest(this);

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

    @Override
    public void onValidationSucceeded() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        request.login(email, password);
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
}

