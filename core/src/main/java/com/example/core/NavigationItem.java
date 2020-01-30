package com.example.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.List;

public interface NavigationItem {

    public Button getButton(Context context);
    public Fragment getFragment();

    public String getName(Context context);
    public Drawable getIcon(Context context);

}
