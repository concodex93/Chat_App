package com.example.conorbyrne.bitchapp;

import android.content.Intent;
import android.renderscript.Byte2;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mStatus;
    private Button mButton;

    // Firebase Reference
    private DatabaseReference mStatusDatatbase;
    private FirebaseUser mCurrentUser;

    // Progress bar
    private AVLoadingIndicatorView mAvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mStatusDatatbase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mAvi = (AVLoadingIndicatorView) findViewById(R.id.avi_status);

        mToolbar = (Toolbar) findViewById(R.id.status_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_value = getIntent().getStringExtra("status_value");

        mStatus = (EditText) findViewById(R.id.status_input);
        mButton = (Button) findViewById(R.id.status_save_btn);

        mStatus.setText(status_value);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAvi.setVisibility(View.VISIBLE);
                String status = mStatus.getText().toString();

                // Set Status to user input
                mStatusDatatbase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mAvi.setVisibility(View.INVISIBLE);
                            Intent settingsIntent = new Intent(StatusActivity.this, Settings.class);
                            startActivity(settingsIntent);

                        }

                        else {
                            mAvi.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Error saving changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
