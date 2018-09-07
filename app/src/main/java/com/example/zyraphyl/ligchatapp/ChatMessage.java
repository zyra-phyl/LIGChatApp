package com.example.zyraphyl.ligchatapp;

public class ChatMessage {
    private String message;
    private String user;

    public ChatMessage(String message, String user){
        this.message = message;
        this.user = user;

    }
    public String getMessage(){
        return message;
    }

    public String getUser() {
        return user;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setUser(String user){
        this.user = user;
    }
}
