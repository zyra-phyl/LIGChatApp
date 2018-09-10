package com.example.zyraphyl.ligchatapp;

public class ChatMessage {
    private String message;
    private String user;
    private String userId;

    public ChatMessage(String message, String user, String userId){
        this.message = message;
        this.user = user;
        this.userId = userId;

    }
    public ChatMessage(){

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
    public String getUserId(){ return  userId;}

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
