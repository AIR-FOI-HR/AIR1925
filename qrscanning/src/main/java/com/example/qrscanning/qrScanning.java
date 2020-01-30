package com.example.qrscanning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.core.NavigationItem;


public class qrScanning extends Fragment implements NavigationItem {

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

}
