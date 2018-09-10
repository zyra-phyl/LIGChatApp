package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private Button signup,login;
    private TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        LayoutElements();
        Functionality();
    }
    private void LayoutElements(){
        login = (Button) findViewById(R.id.loginButtonMain);
        signup = (Button) findViewById(R.id.signUpButtonMain);
        appName = (TextView) findViewById(R.id.appName);
    }
    public void Functionality(){
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButtonMain:
                Intent login = new Intent(this,LogIn.class);
                startActivity(login);
                finish();
                break;
            case R.id.signUpButtonMain:
                Intent signup = new Intent(this,SignUp.class);
                startActivity(signup);
                finish();
                break;
        }
    }
}
