package com.example.readysteadyeat.data.models.guest;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    public String userId;
    public String firstNsme;
    public String lastNsme;
    @SuppressWarnings("WeakerAccess")
    public String userEmail;
    public String userPhone;
    @Exclude
    public boolean isAuthenticated;
    @Exclude
    boolean isNew, isCreated;

    public User() {}

    User(String userId, String firstNsme, String lastNsme, String userEmail, String userPhone) {
        this.userId = userId;
        this.firstNsme = firstNsme;
        this.lastNsme = lastNsme;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }
}
