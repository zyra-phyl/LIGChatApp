package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView login;
    private EditText username_editText,password_editText;
    private Button signup;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        LayoutElements();

    }
    private void LayoutElements(){
        login = (TextView) findViewById(R.id.login_link);
        username_editText = (EditText) findViewById(R.id.username);
        password_editText = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup_button);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    private void registerUser(){
        String username = username_editText.getText().toString().trim();
        String password = password_editText.getText().toString().trim();

        if(username.isEmpty() || (username.length()>16 && username.length()<8)){
            username_editText.setError("Invalid Value");
        }else if (!(username.isEmpty() || (username.length()>16 && username.length()<8))){
//            (mFirebaseAuth.createUserWithEmailAndPassword(username.concat("@ligChatApp"),password)).addOnCompleteListener();
        }
        if(password.isEmpty() || (password.length()>16 && password.length()<8)){
            username_editText.setError("Invalid Value");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_link:
                Intent login = new Intent(this,LogIn.class);
                startActivity(login);
                break;
            case R.id.signup_button:
                registerUser();
                break;
        }
    }
}
