package com.uit.khaph.uitocm;

public class Status {
    public String creator;
    public String status;
    public String date;
    public String className;


    public Status() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Status(String creator,String status, String date, String className) {
        this.creator = creator;
        this.status = status;
        this.date = date;
        this.className = className;
    }

    public String getStatus(){
        return this.status;
    }

    public String getCreator(){
        return this.creator;
    }

    public String getDate(){
        return this.date;
    }

    public String getClassName(){ return this.className; }
}
