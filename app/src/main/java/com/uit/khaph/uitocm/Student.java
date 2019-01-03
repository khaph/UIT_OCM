package com.uit.khaph.uitocm;

public class Student {

    public String userName;
    public String passWord;
    public String dayOfBirth;
    public String className;
    public String pictureUrl;
    public String fullName;


    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Student(String userName, String passWord, String dayOfBirth, String className, String pictureUrl, String fullName) {
        this.userName = userName;
        this.passWord = passWord;
        this.dayOfBirth = dayOfBirth;
        this.className = className;
        this.pictureUrl = pictureUrl;
        this.fullName = fullName;
    }

}
