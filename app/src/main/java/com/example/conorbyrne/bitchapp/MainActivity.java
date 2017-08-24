package com.example.conorbyrne.bitchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chat App");


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Checks is user is signed in (not null)
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser == null){

            sendToSatart();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_log_out_button){

            FirebaseAuth.getInstance().signOut();
            sendToSatart();

        }

        return true;
    }

    private void sendToSatart(){

        Intent startIntent =  new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}
