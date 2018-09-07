package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private TextView signup_link;
    private Button login_button;
    private EditText username_editText,password_editText;
    private FirebaseAuth mFirebaseAuth;
    private TextInputLayout usernameWrapper,passwordWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        LayoutElements();

    }
    private void LayoutElements(){
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        signup_link = (TextView) findViewById(R.id.signup_link);
        signup_link.setOnClickListener(this);
        username_editText = (EditText) findViewById(R.id.username);
        password_editText = (EditText) findViewById(R.id.password);
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_link:
                Intent signup = new Intent(this,SignUp.class);
                startActivity(signup);
                break;
            case R.id.login_button:
                login();
                break;
        }
    }
    private void login(){
        String username = username_editText.getText().toString();
        String password = password_editText.getText().toString();

        if(username.isEmpty() || password.isEmpty()|| (username.length()>16 && username.length()<8)
                || (password.length()>16 && password.length()<8)){
            usernameWrapper.setError("Value is incorrect");
            passwordWrapper.setError("Value is incorrect");
        }else{
            (mFirebaseAuth.signInWithEmailAndPassword(username.concat("@ligChatApp.com"),password)).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent login = new Intent(LogIn.this,MessageBoard.class);
                                startActivity(login);
                            }else{
                                usernameWrapper.setError("Value is incorrect");
                                passwordWrapper.setError("Value is incorrect");
                            }
                        }
                    });
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);

    }
}
