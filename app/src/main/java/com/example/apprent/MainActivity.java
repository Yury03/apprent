package com.example.apprent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new MainFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView,
                            fragment, null)
                    .commit();
        }
//        boolean signIn=false;
//        Bundle arguments=getIntent().getExtras();
//        if(arguments!=null){
//            signIn=arguments.getBoolean("signin");
//        }
//        TextView textView=findViewById(R.id.textView3);
//        if(signIn){
//            textView.setText("Вход выполнен");
//        }else{
//            textView.setText("Вход не выполнен");
//        }
//        Button button=findViewById(R.id.button);
//        boolean finalSignIn = signIn;
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
//                intent.putExtra("signin", finalSignIn);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}