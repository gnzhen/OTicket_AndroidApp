package com.example.gd.oticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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

import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

    @Override
    public void onValidationSucceeded() {

        String name = regName.getText().toString();
        String phone = regPhone.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        String passwordConfirm = regPasswordConfirm.getText().toString().trim();

        request.register(name, email, phone, password, passwordConfirm);
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

