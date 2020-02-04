package foi.air.rse.Controllers.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;

import foi.air.rse.Controllers.restaurant.menu.MenuRestaurantFragment;
import foi.air.rse.Controllers.restaurant.myProfile.ProfileRestarutantFragment;
import foi.air.rse.Controllers.restaurant.orders.OrdersRestaurantFragment;
import com.example.readysteadyeat.R;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuRestaurantActivity extends AppCompatActivity implements MenuRestaurantFragment.OnFragmentInteractionListener {
    Toolbar toolbar;







    androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
    int broj;

    @Override
    public void onBackPressed(){

        if (fm.getBackStackEntryCount() > 0) {
            String imeFragmneta="";
            broj=fm.getBackStackEntryCount();
            String broj1=String.valueOf(broj);

            fm.popBackStack();

            int index = fm.getBackStackEntryCount()-2;
            if(index<0){
                setSupportActionBar(toolbar);
                imeFragmneta = "Orders";
                getSupportActionBar().setTitle(imeFragmneta);
            }else{
                FragmentManager.BackStackEntry ime =  fm.getBackStackEntryAt(index);
                imeFragmneta = ime.getName();

                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(imeFragmneta);
            }
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
            //bottomNavigationView.setSelectedItemId(R.id.navigation_orders_restaurants);

            if(imeFragmneta.equals("Orders")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_orders_restaurants);
                fm.popBackStack();
            }else if (imeFragmneta.equals("Menu")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_menu_restaurants);
                fm.popBackStack();
            }else{
                bottomNavigationView.setSelectedItemId(R.id.navigation_profile_restaurant);
                fm.popBackStack();
            }
        } else {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment=new OrdersRestaurantFragment();
        setContentView(R.layout.activity_bottom_menu_restaurant);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_orders_restaurants);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        toolbar=(Toolbar)findViewById(R.id.toolbarBottomRestaurant);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    toolbar=(Toolbar)findViewById(R.id.toolbarBottomRestaurant);
                    String fragmentTag="";
                    switch (menuItem.getItemId()){
                        case R.id.navigation_orders_restaurants:
                            selectedFragment=new OrdersRestaurantFragment();
                            setSupportActionBar(toolbar);
                            getSupportActionBar().setTitle("Orders");
                            fragmentTag="Orders";
                            break;
                        case R.id.navigation_menu_restaurants:
                            selectedFragment=new MenuRestaurantFragment();
                            setSupportActionBar(toolbar);
                            getSupportActionBar().setTitle("Menu");
                            fragmentTag="Menu";
                            break;
                        case R.id.navigation_profile_restaurant:
                            selectedFragment=new ProfileRestarutantFragment();
                            setSupportActionBar(toolbar);
                            getSupportActionBar().setTitle("Profile");
                            fragmentTag="Profile";
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1,selectedFragment, fragmentTag).addToBackStack(fragmentTag).commit();

                    return true;
                }
            };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

