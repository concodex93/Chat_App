package com.example.conorbyrne.bitchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.x;
import static com.example.conorbyrne.bitchapp.R.id.avi;

public class RegisterActivity extends AppCompatActivity {


    // Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Progress bar
    private AVLoadingIndicatorView mAvi;

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateButton;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.reg_id);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAvi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        mDisplayName = (EditText) findViewById(R.id.reg_display);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mCreateButton = (Button) findViewById(R.id.reg_button);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String display_name = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    mAvi.setVisibility(View.VISIBLE);
                    registerUser(display_name,email,password);

                }

            }
        });


    }

    private void registerUser(final String display_name, final String email, final String password){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

                    String uid = current_user.getUid();

                    // Write a message to the database
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference().child("Users").child(uid);

                    HashMap<String, String>  user_map = new HashMap<String, String>();
                    user_map.put("name", display_name);
                    user_map.put("status", "Hi there I'm using your App!");
                    user_map.put("image", "default");
                    user_map.put("thumb_image", "default");


                    myRef.setValue(user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mAvi.setVisibility(View.GONE);

                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                }

                else{
                    mAvi.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
