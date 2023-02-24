package com.example.apprent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG=this.getClass().getSimpleName();
    Button buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignUp=findViewById(R.id.signUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Sign in (setOnClickListener)");
            }
        });
    }
    public void forExample(View view){
        Log.i(TAG,"Sign up (attribute onClick)");
    }

}