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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileRestarutantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileRestarutantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileRestarutantFragment extends Fragment {



    //OnClick variable

    //region Variables XML

    private TextInputEditText txtRestaurantName, txtCity, txtAdress,
            txtState, txtHouseNumber, txtCIN, txtIBAN,
            txtEmail, txtPassword, txtPasswordRepeat;
    private MaterialButton button;
    private TextInputLayout txtLayRepeatPassword;


    //endregion



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public boolean click=true;
    public ProfileRestarutantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileRestarutantFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_restarutant, container, false);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//region CONNECTING VARIABLEES WITH VIEW
        txtLayRepeatPassword=view.findViewById(R.id.txtPasswordRepeatEdit);
        txtAdress=view.findViewById(R.id.txtInputAdress);
        txtCIN=view.findViewById(R.id.txtInputCIN);
        txtCity=view.findViewById(R.id.txtInputCity);
        txtEmail=view.findViewById(R.id.txtInputEmailEdit);
        txtHouseNumber=view.findViewById(R.id.txtInputHouseNumber);
        txtIBAN=view.findViewById(R.id.txtInputIBAN);
        txtPassword=view.findViewById(R.id.txtInputPasswordEdit);
        txtPasswordRepeat=view.findViewById(R.id.txtInputPasswordRepeatEdit);
        txtRestaurantName=view.findViewById(R.id.txtInputRestaurantName);
        txtState=view.findViewById(R.id.txtInputState);
        //endregion
        button=view.findViewById(R.id.menuButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //          txtInputAdress= view.findViewById(R.id.txtInputAdress);
                //          txtInputAdress.setEnabled(false);
                //          button=view.findViewById(R.id.menuButton);

                //        if(clickCount){
                if(click) {
                    button.setText("Done");
                    //region ENABLE TEXTFIELDS
                    txtAdress.setEnabled(true);
                    txtCIN.setEnabled(true);
                    txtCity.setEnabled(true);
                    txtEmail.setEnabled(true);
                    txtHouseNumber.setEnabled(true);
                    txtIBAN.setEnabled(true);
                    txtPassword.setEnabled(true);
                    txtPasswordRepeat.setEnabled(true);
                    txtPasswordRepeat.setVisibility(View.VISIBLE);
                    txtLayRepeatPassword.setVisibility(View.VISIBLE);
                    txtRestaurantName.setEnabled(true);
                    txtState.setEnabled(true);

                    //endregion
                    click=false;
                }
                else{
                    button.setText("Edit");
                    //region DISABLE TEXTFIELDS
                    txtAdress.setEnabled(false);
                    txtCIN.setEnabled(false);
                    txtCity.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtHouseNumber.setEnabled(false);
                    txtIBAN.setEnabled(false);
                    txtPassword.setEnabled(false);
                    txtPasswordRepeat.setEnabled(false);
                    txtPasswordRepeat.setVisibility(View.INVISIBLE);
                    txtLayRepeatPassword.setVisibility(View.INVISIBLE);
                    txtRestaurantName.setEnabled(false);
                    txtState.setEnabled(false);
                    //endregion
                    click=true;
                }
                //        txtInputAdress.setEnabled(false);
                //        button.setText("Edit");
                //        clickCount=false;
                //    }else{
                //        txtInputAdress.setEnabled(true);
                //        button.setText("Delete");
                //        clickCount=true;
                //    }
            }

        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}


