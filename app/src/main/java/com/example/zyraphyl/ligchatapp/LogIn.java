package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private TextView signup_link;
    private Button login_button;
    private EditText username_editText,password_editText;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mFirebaseAuth = FirebaseAuth.getInstance();

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        signup_link = (TextView) findViewById(R.id.signup_link);
        signup_link.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_link:
                Intent signup = new Intent(this,SignUp.class);
                startActivity(signup);
                break;
            case R.id.login_button:

                break;
        }
    }

}
