package com.example.readysteadyeat.ui.restaurant.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManuRestaurantEditActivity extends AppCompatActivity {

    private String dishId;
    private String dishName;
    private String dishCategory;
    private String dishDescription;
    private String dishDairyFree;
    private String dishGlutenFree;
    private String dishPrice;
    private String dishImgUrl;
    private String categoryName;
    CheckBox dairyFree;
    CheckBox glutenFree;
    Spinner spinnerCategory;
    ImageView restaurantImage;
    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter;
    ValueEventListener listener;

    //povuci oba bzutton
    //povuci sve iz boxova
    //dva onClick listenera
    //napraviti deltee
    //napravit update

    private DatabaseReference databaseReferenceCategory;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_rest_edit);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenuEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu dish edit");

        dairyFree = findViewById(R.id.checkboxDairy);
        glutenFree = findViewById(R.id.checkGluten);
        spinnerCategory = findViewById(R.id.spnrCategory);
        restaurantImage = findViewById(R.id.imgvUserPictureGuest);


        dishId = getIntent().getExtras().get("dish_id").toString();
        dishName = getIntent().getExtras().get("dish_name").toString();
        dishCategory = getIntent().getExtras().get("dish_category").toString();
        dishDescription = getIntent().getExtras().get("dish_description").toString();
        dishDairyFree = getIntent().getExtras().get("dish_dairy_free").toString();
        dishGlutenFree = getIntent().getExtras().get("dish_gluten_free").toString();
        dishPrice = getIntent().getExtras().get("dish_price").toString();
        dishImgUrl = getIntent().getExtras().get("dish_img_url").toString();

        populateItems();

        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("Category");
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        spinnerCategory.setAdapter(adapter);
        retreiveData();
        //int selectionPosition = adapter.getPosition(dishCategory);
        //spinnerCategory.setSelection(5);
    }

    public void retreiveData(){
        listener = databaseReferenceCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Category category = item.getValue(Category.class);
                    spinnerDataList.add(category.name);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateItems(){
        EditText dish_name = findViewById(R.id.txtDishName);
        EditText dish_description = findViewById(R.id.txtDishDescription);
        EditText dish_price = findViewById(R.id.txtPrice);
        Picasso.get().load(dishImgUrl).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(restaurantImage);

        dish_name.setText(dishName);
        dish_description.setText(dishDescription);
        dish_price.setText(dishPrice);
        if(dishDairyFree.equals("true")){
            dairyFree.setChecked(true);
        }
        else{
            dairyFree.setChecked(false);
        }
        if(dishGlutenFree.equals("true")){
            glutenFree.setChecked(true);
        }
        else{
            glutenFree.setChecked(false);
        }
    }
}
