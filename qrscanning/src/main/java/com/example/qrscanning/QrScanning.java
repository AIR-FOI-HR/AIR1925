package com.example.qrscanning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.core.NavigationItem;


public class QrScanning extends Fragment implements NavigationItem {

    String id;
    Button button;
    private static int RC_SOME_ACTIVITY = 101;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public Button getButton(Context context) {
        int radius = 20;
        int color = Color.parseColor("#00A6FF");
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(color);
        Button button = new Button(context);
        button.setLayoutParams(new LinearLayout.LayoutParams(400,65));
        button.setTextSize(15);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setBackground(gd);
        button.setText("Scann QR code");
        return button;
        
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getName(Context context) {
        return "Scan QR code";
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(android.R.drawable.ic_menu_agenda);
    }

    @Override
    public void setData(String id) {
        this.id = id;
        //tryToDisplayData();

    }

    private void tryToDisplayData() {
        //startActivityForResult(new Intent(getContext(), QrScanningActivity.class), RC_SOME_ACTIVITY);
    }

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void startActivity() {
        //Intent profileIntent = new Intent().setClass(getActivity(), probniActivity.class);
        //Intent profileIntent = new Intent(getContext(), QrScanningActivity.class);
        //profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Intent intent = new Intent(context, QrScanningActivity.class);
        //startActivity(intent);
        //startActivityForResult(new Intent(getContext(), QrScanningActivity.class), RC_SOME_ACTIVITY);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QrScanningActivity.class);
                startActivity(intent);
            }
        });
    }
}
