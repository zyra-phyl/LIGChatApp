package com.example.zyraphyl.ligchatapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MessageBoard extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton send;
    private EditText inputMessage;
    private TextView messageView,userView;
    private ListView listOfMessages;
    private String message;
    private FirebaseListAdapter<ChatMessage> chatAdapter;
    private Query query;
    private FirebaseListOptions<ChatMessage> chatOptions;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);

        LayoutElements();
        displayMessages();
    }
    private void LayoutElements(){
        send = (FloatingActionButton) findViewById(R.id.send);
        send.setOnClickListener(this);
        inputMessage = (EditText) findViewById(R.id.message);
        messageView = (TextView) findViewById(R.id.message_text);
        userView = (TextView) findViewById(R.id.message_user);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);
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
}
