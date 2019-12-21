package com.example.readysteadyeat.ui.restaurant.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Category;
import com.example.readysteadyeat.ui.guest.auth.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuRestaurantAddActivity extends AppCompatActivity {

    Toolbar toolbar;

    private CircleImageView imgvDish;
    private Button btnAddDish;
    private Spinner spnrCategory;
    private TextInputEditText txtDishName, txtDishDescription, txtPrice;
    private AppCompatCheckBox checkGluten,  checkboxDairy;

    private DatabaseReference databaseReferenceCategory;

    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter;
    ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restaurant_add);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenuEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu dish add");

        imgvDish = findViewById(R.id.imgvDish);
        btnAddDish = findViewById(R.id.btnAddDish);
        spnrCategory = findViewById(R.id.spnrCategory);
        txtDishName = findViewById(R.id.txtDishName);
        txtDishDescription = findViewById(R.id.txtDishDescription);
        txtPrice = findViewById(R.id.txtPrice);
        checkGluten = findViewById(R.id.checkGluten);
        checkboxDairy = findViewById(R.id.checkboxDairy);

        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("Category");
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        spnrCategory.setAdapter(adapter);
        retreiveData();

        imgvDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                }
                else
                {
                    openGallery();
                }
            }
        });

        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String imgUrl;
                final String category = spnrCategory.getSelectedItem().toString();
                final String name = txtDishName.getText().toString();
                final String description = txtDishDescription.getText().toString();
                final String price = txtPrice.getText().toString();
                final boolean glutenFree =  checkGluten.isChecked();
                final boolean dairyFree = checkboxDairy.isChecked();

                //napravit objekt
                //spremiti u bazu
            }
        });

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

    private void checkAndRequestPermission() {
        if(ContextCompat.checkSelfPermission(MenuRestaurantAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MenuRestaurantAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(MenuRestaurantAddActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(MenuRestaurantAddActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }
        else
        {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryItent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryItent.setType("image/*");
        startActivityForResult(galleryItent, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null && data.getData() != null){
            pickedImgUri = data.getData();
            Picasso.get().load(pickedImgUri).into(imgvDish);
        }

    }
}
