package com.example.core;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.Model.Dish;
import com.example.core.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderFinalView extends AppCompatActivity {

    private String key;
    public TextView dateValue, timeValue, personsValue, dishesValue, priceValue;
    private DatabaseReference databaseReferenceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        /*dateValue = findViewById(R.id.dateValue);
        timeValue = findViewById(R.id.timeValue);
        personsValue = findViewById(R.id.personsValue);
        dishesValue = findViewById(R.id.dishesValue);
        priceValue = findViewById(R.id.priceValue);
        databaseReferenceOrder = FirebaseDatabase.getInstance().getReference().child("Order");*/

        populateItems();
    }


    public void populateItems(){
        FirebaseDatabase.getInstance().getReference().child("Order")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Order order = snapshot.getValue(Order.class);
                            if(snapshot.getKey().equals(key)){
                                /*dateValue.setText(snapshot.child("dateTime").getValue().toString());
                                timeValue.setText(snapshot.child("time").getValue().toString());
                                personsValue.setText(snapshot.child("persons").getValue().toString());
                                priceValue.setText(snapshot.child("price").getValue().toString());*/
                                Toast.makeText(getApplicationContext(), "jaaj", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}
