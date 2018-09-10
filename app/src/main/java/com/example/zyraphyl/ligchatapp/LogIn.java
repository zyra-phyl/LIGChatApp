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


public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private TextView signup_link,errorName,errorPassword;
    private Button login_button;
    private EditText username_editText,password_editText;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //Firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        //initialize elements
        LayoutElements();

    }
    private void LayoutElements(){
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        signup_link = findViewById(R.id.signup_link);
        signup_link.setOnClickListener(this);
        username_editText = findViewById(R.id.username);
        password_editText =  findViewById(R.id.password);
        errorName = findViewById(R.id.nameError);
        errorPassword = findViewById(R.id.passwordError);
        progressBar = findViewById(R.id.login_progress);
        //set custom actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

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
        progressBar.setVisibility(View.VISIBLE);
        if(username.isEmpty() || password.isEmpty()|| (username.length()>16 || username.length()<8)
                || (password.length()>16 || password.length()<8)){
                //show error message
                errorName.setVisibility(View.VISIBLE);
                errorPassword.setVisibility(View.VISIBLE);
                //stop showing progress bar
                progressBar.setVisibility(View.INVISIBLE);
        }else{
            //firebase authentication
            (mFirebaseAuth.signInWithEmailAndPassword(username.concat("@ligChatApp.com"),password)).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if(task.isSuccessful()){
                                Toast.makeText(LogIn.this,"Login successful",Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(LogIn.this,MainActivity.class);
                                startActivity(login);
                            }else{
                                errorName.setVisibility(View.VISIBLE);
                                errorPassword.setVisibility(View.VISIBLE);
                                username_editText.setText("");
                                password_editText.setText("");

                            }
                        }
                    });
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);

    }
}
