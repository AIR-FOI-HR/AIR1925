package com.example.readysteadyeat.ui.restaurant.myProfile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Guest;
import com.example.readysteadyeat.data.models.Restaurant;
import com.example.readysteadyeat.ui.guest.auth.SignUpActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProfileRestarutantFragment extends Fragment {

    private ImageView imgvUserProfile;
    private TextInputEditText txtRestaurantName, txtCity, txtAdress,
            txtState, txtHouseNumber, txtIBAN,
            txtEmail;

    private MaterialButton btnEditProfile;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    private OnFragmentInteractionListener mListener;
    public boolean click = false;
    public ProfileRestarutantFragment() {
    }

    public static ProfileRestarutantFragment newInstance(String param1, String param2) {
        ProfileRestarutantFragment fragment = new ProfileRestarutantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_restarutant, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAdress = view.findViewById(R.id.txtRestaurantStreet);
        txtCity = view.findViewById(R.id.txtRestaturantCity);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtHouseNumber = view.findViewById(R.id.txtRestaturantHouseNumber);
        txtIBAN = view.findViewById(R.id.txtIBAN);
        txtRestaurantName = view.findViewById(R.id.txtRestaurantName);
        txtState = view.findViewById(R.id.txtRestaurantState);
        btnEditProfile = (MaterialButton) view.findViewById(R.id.btnEditProfileRestaurant);
        imgvUserProfile = (ImageView) view.findViewById(R.id.imgvUserProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databseReference = firebaseDatabase.getReference("User").child("Restaurant").child(firebaseAuth.getCurrentUser().getUid());

        btnEditProfile.setText("Edit profile");
        txtAdress.setEnabled(false);
        txtCity.setEnabled(false);
        txtEmail.setEnabled(false);
        txtHouseNumber.setEnabled(false);
        txtIBAN.setEnabled(false);
        txtRestaurantName.setEnabled(false);
        imgvUserProfile.setFocusable(false);
        txtState.setEnabled(false);

        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!click) {
                    btnEditProfile.setText("Save changes");
                    txtAdress.setEnabled(true);
                    txtCity.setEnabled(true);
                    txtEmail.setEnabled(true);
                    txtHouseNumber.setEnabled(true);
                    txtIBAN.setEnabled(true);
                    txtRestaurantName.setEnabled(true);
                    imgvUserProfile.setFocusable(true);
                    txtState.setEnabled(true);
                    imgvUserProfile.setEnabled(true);
                    click=true;
                }
                else if(click)
                {
                    btnEditProfile.setText("Edit profile");
                    txtAdress.setEnabled(false);
                    txtCity.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtHouseNumber.setEnabled(false);
                    txtIBAN.setEnabled(false);
                    txtRestaurantName.setEnabled(false);
                    txtState.setEnabled(false);
                    imgvUserProfile.setFocusable(false);
                    imgvUserProfile.setEnabled(false);
                    click=false;
                }
            }
        });

        databseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant restaurantInfo = dataSnapshot.getValue(Restaurant.class);
                txtRestaurantName.setText(restaurantInfo.getName());
                txtEmail.setText(restaurantInfo.getEmail());
                txtState.setText(restaurantInfo.getEmail());
                txtCity.setText(restaurantInfo.getCity());
                txtAdress.setText(restaurantInfo.getStreet());
                txtHouseNumber.setText(restaurantInfo.getHouseNumber());
                txtIBAN.setText(restaurantInfo.getIban());
                Picasso.get().load(restaurantInfo.getImgUrl()).into(imgvUserProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Activity activity = getActivity();
                Toast.makeText(activity, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        imgvUserProfile.setOnClickListener(new View.OnClickListener() {
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
        Activity activity = getActivity();
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null && data.getData() != null){
            pickedImgUri = data.getData();
            Picasso.get().load(pickedImgUri).into(imgvUserProfile);
        }
    }
}


