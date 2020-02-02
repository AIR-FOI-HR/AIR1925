package com.example.core.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDetails implements Parcelable {

    public String orderId;
    public String dishId;
    public String quantity;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String dishId, String quantity) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public OrderDetails(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.orderId = data[0];
        this.dishId = data[1];
        this.quantity = data[2];
    }



    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.orderId,
                this.dishId,
                this.quantity});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };

}
