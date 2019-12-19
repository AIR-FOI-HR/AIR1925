package com.example.readysteadyeat.ui.restaurant.myProfile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileRestarutantFragment extends Fragment {

    private TextInputEditText txtRestaurantName, txtCity, txtAdress,
            txtState, txtHouseNumber, txtCIN, txtIBAN,
            txtEmail, txtPassword, txtRepeatPassword;

    private MaterialButton btnEditProfile;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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
        txtPassword = view.findViewById(R.id.txtPassword);
        txtRepeatPassword = view.findViewById(R.id.txtRepeatPassword);
        txtRestaurantName = view.findViewById(R.id.txtRestaurantName);
        txtState = view.findViewById(R.id.txtRestaurantState);
        btnEditProfile = (MaterialButton) view.findViewById(R.id.btnEditProfileRestaurant);

        btnEditProfile.setText("Edit profile");
        txtAdress.setEnabled(false);
        txtCity.setEnabled(false);
        txtEmail.setEnabled(false);
        txtHouseNumber.setEnabled(false);
        txtIBAN.setEnabled(false);
        txtPassword.setEnabled(false);
        txtRepeatPassword.setEnabled(false);
        txtRestaurantName.setEnabled(false);
        txtState.setEnabled(false);
        txtPassword.setVisibility(View.VISIBLE);
        txtRepeatPassword.setVisibility(View.VISIBLE);

        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!click) {
                    btnEditProfile.setText("Edit profile");
                    txtAdress.setEnabled(false);
                    txtCity.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtHouseNumber.setEnabled(false);
                    txtIBAN.setEnabled(false);
                    txtPassword.setEnabled(false);
                    txtRepeatPassword.setEnabled(false);
                    txtRestaurantName.setEnabled(false);
                    txtState.setEnabled(false);
                    txtPassword.setVisibility(View.VISIBLE);
                    txtRepeatPassword.setVisibility(View.VISIBLE);
                    click=true;
                }
                else if(click)
                {
                    btnEditProfile.setText("Save changes");
                    txtAdress.setEnabled(true);
                    txtCity.setEnabled(true);
                    txtEmail.setEnabled(true);
                    txtHouseNumber.setEnabled(true);
                    txtIBAN.setEnabled(true);
                    txtPassword.setEnabled(true);
                    txtRepeatPassword.setEnabled(true);
                    txtRestaurantName.setEnabled(true);
                    txtState.setEnabled(true);
                    txtPassword.setVisibility(View.VISIBLE);
                    txtRepeatPassword.setVisibility(View.VISIBLE);
                    click=false;
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}


