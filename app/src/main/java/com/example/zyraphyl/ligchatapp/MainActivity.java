package com.example.zyraphyl.ligchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.signin.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseListOptions<ChatMessage> chatOptions;
    private FirebaseDatabase database;
    private FirebaseListAdapter<ChatMessage> chatAdapter;
    private Query query;
    private Button send;
    private EditText inputMessage;
    private TextView messageView,userView;
    private ListView listOfMessages;
    private String message,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        username = mFirebaseUser.getDisplayName();
        if (mFirebaseUser == null){
            Intent loadDash = new Intent(this,Dashboard.class);
            startActivity(loadDash);
        }else{
            displayMessages();
        }
    }
    private void LayoutElements(){
        send = (Button) findViewById(R.id.sendButton);
        send.setOnClickListener(this);
        inputMessage = (EditText) findViewById(R.id.message);
        messageView = (TextView) findViewById(R.id.message_text);
        userView = (TextView) findViewById(R.id.message_user);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        mFirebaseAuth.signOut();
        mFirebaseUser = null;
        startActivity(new Intent(this, Dashboard.class));
        finish();
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                SendMessage();
                break;
        }
    }
    private void SendMessage(){
        message = inputMessage.getText().toString();
        database.getReference().push()
                .setValue(new ChatMessage(message, FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName()));
        inputMessage.setText("");
    }
    private void displayMessages(){
        database = FirebaseDatabase.getInstance();
        query = database.getReference().child("message").limitToLast(50);
        chatOptions = new FirebaseListOptions.Builder<ChatMessage>().setQuery(query,ChatMessage.class).setLayout(R.layout.message).build();
        chatAdapter = new FirebaseListAdapter<ChatMessage>(chatOptions) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                messageView.setText(model.getMessage());
                userView.setText(model.getUser());
            }
        };
        listOfMessages.setAdapter(chatAdapter);
    }
    public void onBackPressed() {
        moveTaskToBack(true);

    }
}
