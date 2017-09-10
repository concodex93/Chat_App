package com.example.conorbyrne.bitchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.conorbyrne.bitchapp.R.id.user_status_btn;

public class Settings extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    // Android layout

    private TextView mDisplayName;
    private TextView mStatus;
    private Button mStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayName = (TextView) findViewById(R.id.user_display_name);
        mStatus = (TextView) findViewById(R.id.user_status);
        mStatusButton = (Button) findViewById(R.id.user_status_btn);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                // String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                //String thumb_nail = dataSnapshot.child("thumb_nail").getValue().toString();

                mDisplayName.setText(name);
                mStatus.setText(status);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status_value = mStatus.getText().toString();

                Intent statusIntent = new Intent(Settings.this, StatusActivity.class);
                // Send data through Intent
                statusIntent.putExtra("status_value", status_value);
                startActivity(statusIntent);

            }
        });
    }




}
