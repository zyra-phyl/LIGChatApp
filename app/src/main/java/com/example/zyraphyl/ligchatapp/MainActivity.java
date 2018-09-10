package com.example.zyraphyl.ligchatapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Firebase instance variables
    final String TAG = "Main Activity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseListOptions<ChatMessage> chatOption;
    private DatabaseReference database;
    private FirebaseListAdapter<ChatMessage> chatAdapter;
    private Query query;
    private Button send,logout;
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
        database = FirebaseDatabase.getInstance().getReference();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/arial.ttf",true);
        LayoutElements();
        displayMessages();
        if (mFirebaseUser == null){
            Intent loadDash = new Intent(this,Dashboard.class);
            startActivity(loadDash);
        }else{
            username = mFirebaseUser.getEmail().replace("@ligchatapp.com","");

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listOfMessages.setSelection(chatAdapter.getCount() - 1);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
    private void LayoutElements(){
        send = (Button) findViewById(R.id.sendButton);
        send.setOnClickListener(this);
        inputMessage = (EditText) findViewById(R.id.message);
        messageView = (TextView) findViewById(R.id.message_text);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);
        listOfMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        logout = (Button) findViewById(R.id.logout_button);
        logout.setVisibility(View.VISIBLE);
        logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendButton:
                SendMessage();
                break;
            case R.id.logout_button:
                mFirebaseAuth.signOut();
                mFirebaseUser = null;
                startActivity(new Intent(this, Dashboard.class));
                finish();
                break;
        }
    }
    private void SendMessage(){
        message = inputMessage.getText().toString();

        database.push()
                .setValue(new ChatMessage(message, username,mFirebaseUser.getUid()));
        inputMessage.setText("");
    }
    private void displayMessages(){
        query = database.limitToLast(50);
        chatOption = new FirebaseListOptions.Builder<ChatMessage>().setQuery(query,ChatMessage.class).setLayout(R.layout.outbound_message).build();
        chatAdapter = new MessageAdapter(this,chatOption);
        listOfMessages.setAdapter(chatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }

    public String getUsername() {
        return username;
    }

    public void onBackPressed() {
        moveTaskToBack(true);

    }
}
