package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.signin.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button signup,login;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loadDash = new Intent(this,Dashboard.class);
        startActivity(loadDash);

        //Initialize Layouts
        LayoutElements();
    }
    private void LayoutElements(){
        Typeface arial = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        login = (Button) findViewById(R.id.loginButtonMain);
        login.setTypeface(arial);
        signup = (Button) findViewById(R.id.signUpButtonMain);
        signup.setTypeface(arial);
        appName = (TextView) findViewById(R.id.appName);
        appName.setTypeface(arial);
    }
}
