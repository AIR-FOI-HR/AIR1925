package com.example.qrscanning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.core.NavigationItem;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.common.eventbus.Subscribe;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.app.Activity.RESULT_OK;

public class probniFragment extends Fragment implements NavigationItem {

    String id;
    Button button;
    private static int RC_SOME_ACTIVITY = 101;

    public static final int REQUEST_CODE = 111;
    public static final int RESULT_CODE = 12;
    public static final String EXTRA_KEY_TEST = "testKey";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_probni, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) getView().findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), probniActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            Toast.makeText(getContext(), testResult, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public Button getButton(Context context) {
        return null;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getName(Context context) {
        return "proba";
    }

    @Override
    public Drawable getIcon(Context context) {
        return null;
    }

    @Override
    public void setData(String id) {
        this.id = id;
    }

    @Override
    public void startActivity() {

    }


}
