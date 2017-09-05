package com.example.conorbyrne.bitchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity {

    // Firebase Auth
    private FirebaseAuth mAuth;
    // Progress bar
    private AVLoadingIndicatorView mAvi;

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        mAvi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mAuth = FirebaseAuth.getInstance();
        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.reg_id);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmail = (EditText) findViewById(R.id.log_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.login_button);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!email.isEmpty() || !password.isEmpty()) {
                    mAvi.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "waiting ...",
                            Toast.LENGTH_SHORT).show();
                    loginUser(email, password);
                } else {

                    Toast.makeText(LoginActivity.this, "Missing required fields",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loginUser(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           mAvi.setVisibility(View.GONE);
                           Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(mainIntent);
                           Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
//                           finish();

                       } else {

                           mAvi.setVisibility(View.GONE);
                           Toast.makeText(LoginActivity.this, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
