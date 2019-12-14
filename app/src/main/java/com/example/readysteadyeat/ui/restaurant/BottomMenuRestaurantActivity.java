package com.example.readysteadyeat.ui.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.ui.restaurant.menu.RestaurantMenuEditFragment;
import com.example.readysteadyeat.ui.restaurant.myProfile.ProfileRestarutantFragment;
import com.example.readysteadyeat.ui.restaurant.orders.OrdersRestaurantFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu_restaurant);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_orders_restaurants);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.navigation_orders_restaurants:
                            selectedFragment=new OrdersRestaurantFragment();
                            break;
                        case R.id.navigation_menu_restaurants:
                            selectedFragment=new RestaurantMenuEditFragment();
                            break;
                        case R.id.navigation_profile_restaurant:
                            selectedFragment=new ProfileRestarutantFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                    return true;
                }
            };
}
