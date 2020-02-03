package com.example.core;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderFinal extends AppCompatActivity {
    private TextView dateValue, timeValue, personsValue, dishesValue, priceValue;
    private String key;
    private DatabaseReference databaseReferenceOrder;
    private List<String> listDishId = new ArrayList<>();
    private List<String> listDishName = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        dateValue = findViewById(R.id.dateValue);
        timeValue = findViewById(R.id.timeValue);
        personsValue = findViewById(R.id.personsValue);
        priceValue = findViewById(R.id.priceValue);
        databaseReferenceOrder = FirebaseDatabase.getInstance().getReference().child("Order");

        populateItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_final);
    }

    public void populateItems(){
        FirebaseDatabase.getInstance().getReference().child("Order")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Order order = snapshot.getValue(Order.class);
                            if(snapshot.getKey().equals(key)){
                                dateValue.setText(snapshot.child("dateTime").getValue().toString());
                                timeValue.setText(snapshot.child("time").getValue().toString());
                                personsValue.setText(snapshot.child("persons").getValue().toString());
                                priceValue.setText(snapshot.child("price").getValue().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        iterateOrderDetails();

    }

    public void iterateOrderDetails(){
        FirebaseDatabase.getInstance().getReference().child("OrderDetails")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                            //Order order = snapshot.getValue(Order.class);
                            if(snapshot2.child("orderId").getValue().toString().equals(key)){
                                listDishId.add(snapshot2.child("dishId").getValue().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        iterateDishes();
    }

    public void iterateDishes(){
        FirebaseDatabase.getInstance().getReference().child("Dish")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {
                            //Order order = snapshot.getValue(Order.class);
                            for(int i=0; i< listDishId.size(); i++){
                                if(snapshot3.getKey().equals(listDishId.get(i))){
                                    listDishName.add(snapshot3.child("name").getValue().toString());
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

}
