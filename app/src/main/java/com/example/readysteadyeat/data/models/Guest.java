package com.example.readysteadyeat.data.models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Guest implements Serializable {

    public String userId;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public boolean userType;
    public String imgUrl;

    public Guest() {}

    public Guest(String userId, String firstName,
                 String lastName, String email,
                 String phone, boolean userType,
                 String imgUrl) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = getLastName();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNsme) {
        this.firstName = firstNsme;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNsme) {
        this.lastName = lastNsme;
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
