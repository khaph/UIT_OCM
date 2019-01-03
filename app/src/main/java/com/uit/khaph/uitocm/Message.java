package com.uit.khaph.uitocm;

public class Message {
    public String message;
    public String authorName;
    public String authorPictureUrl;

    public Message(){

    }

    public Message(String message, String authorName, String authorPictureUrl){
        this.authorName = authorName;
        this.authorPictureUrl = authorPictureUrl;
        this.message = message;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public String getMessage() {
        return this.message;
    }
    public String getPicture(){
        return this.authorPictureUrl;
    }
}
