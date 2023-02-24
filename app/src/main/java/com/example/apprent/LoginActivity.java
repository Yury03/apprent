package com.example.apprent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private final String TAG=this.getClass().getSimpleName();
    Button buttonSignUp;
    final boolean[] signInStatus = new boolean[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle arguments=getIntent().getExtras();
        if(arguments!=null){
            signInStatus[0] =arguments.getBoolean("signin");
        }
        buttonSignUp=findViewById(R.id.signUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Sign Up (setOnClickListener)");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("signin", signInStatus[0]);
                startActivity(intent);
finish();
            }
        });

    }
    public void forExample(View view){
        Log.i(TAG,"Sign In (attribute onClick)");
        signInStatus[0] =!signInStatus[0];
    }
}