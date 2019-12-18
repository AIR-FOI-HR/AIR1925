package com.example.readysteadyeat.data.models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Guest implements Serializable {

    public String userId;
    public String firstNsme;
    public String lastNsme;
    public String email;
    public String phone;
    public boolean userType;
    public String imgUrl;

    public Guest() {}

    public Guest(String userId, String firstName,
                 String lastNsme, String email,
                 String phone, boolean userType,
                 String imgUrl) {

        this.userId = userId;
        this.firstNsme = firstName;
        this.lastNsme = lastNsme;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstNsme() {
        return firstNsme;
    }

    public void setFirstNsme(String firstNsme) {
        this.firstNsme = firstNsme;
    }

    public String getLastNsme() {
        return lastNsme;
    }

    public void setLastNsme(String lastNsme) {
        this.lastNsme = lastNsme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
