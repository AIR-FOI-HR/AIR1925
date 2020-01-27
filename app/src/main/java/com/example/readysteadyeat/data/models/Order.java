package com.example.readysteadyeat.data.models;

public class Order {
    public String dateTime;
    public String persons;
    public String price;
    public String raiting;
    public String status;
    public String restaurantId;
    public String guestId;


    public Order(){}

    public Order(String dateTime, String persons, String price, String raiting, String status, String restaurantId, String guestId) {
        this.dateTime = dateTime;
        this.persons = persons;
        this.price = price;
        this.raiting = raiting;
        this.status = status;
        this.restaurantId = restaurantId;
        this.guestId = guestId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
