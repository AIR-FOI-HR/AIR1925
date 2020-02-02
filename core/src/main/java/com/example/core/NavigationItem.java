package com.example.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.List;

public interface NavigationItem {

    public Fragment getFragment();
    public String getName(Context context);
    public Drawable getIcon(Context context);
    public void setData(String id);
    public void iterateRcv();
    public void makeOrder();
    public void updateOrderDetails(String key);
    public void openTimeAndPersons(String key);

}
