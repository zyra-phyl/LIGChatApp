package com.example.zyraphyl.ligchatapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {

    private MainActivity activity;

    public MessageAdapter(MainActivity activity, FirebaseListOptions<ChatMessage> option ) {
        super(option);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
       TextView messageView = (TextView) v.findViewById(R.id.message_text);
       TextView userView = (TextView) v.findViewById(R.id.message_user);
       if (activity.getUsername().equals(model.getUser())){
           userView.setText("You");
       }else{
           userView.setText(model.getUser());
       }
       messageView.setText(model.getMessage());
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getUser().equals(activity.getUsername())) {
            view = activity.getLayoutInflater().inflate(R.layout.outbound_message, viewGroup, false);
        }else
            view = activity.getLayoutInflater().inflate(R.layout.inbound_message, viewGroup, false);
        //generating view
        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }


}
