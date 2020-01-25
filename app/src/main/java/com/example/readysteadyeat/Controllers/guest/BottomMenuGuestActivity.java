package com.example.readysteadyeat.ui.guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.ui.guest.Orders.OrdersGuestFragment;
import com.example.readysteadyeat.ui.guest.myProfile.ProfileGuestFragment;
import com.example.readysteadyeat.ui.guest.restaurants.RestaurantFragmentList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuGuestActivity extends AppCompatActivity  {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
    int broj;

    @Override
    public void onBackPressed(){

        if (fm.getBackStackEntryCount() > 0) {
            String imeFragmneta="";
            broj=fm.getBackStackEntryCount();
            String broj1=String.valueOf(broj);
            Log.i("Broj", "Broj: " + broj1);
            fm.popBackStack();

            int index = fm.getBackStackEntryCount()-2;
            if(index<0){
                setSupportActionBar(toolbar);
                imeFragmneta = "Orders";
                getSupportActionBar().setTitle(imeFragmneta);
            }else{
                FragmentManager.BackStackEntry ime =  fm.getBackStackEntryAt(index);
                imeFragmneta = ime.getName();
                Log.i("MainActivity", "popping backstack--------------->" + imeFragmneta);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(imeFragmneta);
            }
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

            if(imeFragmneta.equals("Orders")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_orders_guest);
                fm.popBackStack();
            }else if (imeFragmneta.equals("Restaurants")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_list_guest);
                fm.popBackStack();
            }else{
                bottomNavigationView.setSelectedItemId(R.id.navigation_profile_guest);
                fm.popBackStack();
            }
        } else {
            Log.i("MainActivity", "nothing on backstack");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_menu_guest);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        Fragment fragment =new OrdersGuestFragment();

        bottomNavigationView.setSelectedItemId(R.id.navigation_orders_guest);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        toolbar=(Toolbar)findViewById(R.id.toolbarBottomGuest);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Orders");

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            toolbar=(Toolbar)findViewById(R.id.toolbarBottomGuest);
            String fragmentTag="";
            switch(menuItem.getItemId()){
                case R.id.navigation_profile_guest:
                    selectedFragment=new ProfileGuestFragment();
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Profile");
                    fragmentTag = "Profile";
                break;
                case R.id.navigation_orders_guest:
                    selectedFragment=new OrdersGuestFragment();
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Orders");
                    fragmentTag = "Orders";
                break;
                case R.id.navigation_list_guest:
                    selectedFragment=new RestaurantFragmentList();
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Restaurants");
                    fragmentTag = "Restaurants";
                break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment, fragmentTag).addToBackStack(fragmentTag).commit();            return true;
        }
    };

}
