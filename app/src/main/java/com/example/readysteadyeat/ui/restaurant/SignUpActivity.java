package com.example.readysteadyeat.ui.restaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {

    ImageView imgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rest);

        imgUserPhoto = findViewById(R.id.imgvUserProfile);

        imgUserPhoto.setOnClickListener(new View.OnClickListener() {
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
    }

    private void checkAndRequestPermission() {
        if(ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(SignUpActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }
        else
        {
            openGallery();
        }
    }

    private void openGallery(){
        Intent galleryItent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryItent.setType("image/*");
        startActivityForResult(galleryItent, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            pickedImgUri = data.getData();
            Picasso.get().load(pickedImgUri).into(imgUserPhoto);
        }
    }

}