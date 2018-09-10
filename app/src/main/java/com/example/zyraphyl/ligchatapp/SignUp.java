package com.example.zyraphyl.ligchatapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView login,errorName,errorPassword;
    private EditText username_editText,password_editText;
    private Button signup,logout;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFirebaseAuth = FirebaseAuth.getInstance();
        LayoutElements();

    }
    private void LayoutElements(){
        login = (TextView) findViewById(R.id.login_link);
        username_editText = (EditText) findViewById(R.id.username);
        password_editText = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup_button);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);

        errorName = (TextView) findViewById(R.id.nameError);
        errorPassword = (TextView) findViewById(R.id.passwordError);

        progressBar = (ProgressBar) findViewById(R.id.signup_progress);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
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
    private void registerUser(){
        String username = username_editText.getText().toString().trim();
        String password = password_editText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(username.isEmpty() || (username.length()>16 && username.length()<8)){
            errorName.setVisibility(View.VISIBLE);
            errorPassword.setVisibility(View.VISIBLE);
        }else if (!(username.isEmpty() || (username.length()>16 && username.length()<8))){
            (mFirebaseAuth.createUserWithEmailAndPassword(username.concat("@ligChatApp.com"),password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()){

                        Toast.makeText(SignUp.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                        Intent signInIntent = new Intent(SignUp.this,LogIn.class);
                        startActivity(signInIntent);
                    }else{
                        errorName.setVisibility(View.VISIBLE);
                        errorPassword.setVisibility(View.VISIBLE);
                        username_editText.setText("");
                        password_editText.setText("");

                    }
                }
            });
        }
        if(password.isEmpty() || (password.length()>16 && password.length()<8)){
            username_editText.setError("Invalid Value");
        }
    }


    public void onBackPressed() {
        moveTaskToBack(true);

    }
}
