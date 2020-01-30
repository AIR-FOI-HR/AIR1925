package foi.air.rse.Controllers.guest.restaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qrscanning.qrScanning;
import com.example.readysteadyeat.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import com.example.core.NavigationItem;

import java.util.ArrayList;
import java.util.List;

import foi.air.rse.Controllers.guest.BottomMenuGuestActivity;

public class RestaurantProfileActivity extends AppCompatActivity{

    private String restaurantId;
    private String restaurantName;
    private String restaurantStreet;
    private String restaurantHouseNumber;
    private String restaurantCity;
    private String restaurantState;
    private String restaurantEmail;
    private String restaurantImgUrl;

    private List<NavigationItem> navigationItems;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    Button button;

    //manage drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        restaurantId = getIntent().getExtras().get("restaurant_id").toString();
        restaurantName = getIntent().getExtras().get("restaurant_name").toString();
        restaurantStreet = getIntent().getExtras().get("restaurant_street").toString();
        restaurantHouseNumber = getIntent().getExtras().get("restaurant_houseNumber").toString();
        restaurantCity = getIntent().getExtras().get("restaurant_city").toString();
        restaurantState = getIntent().getExtras().get("restaurant_state").toString();
        restaurantEmail = getIntent().getExtras().get("restaurant_email").toString();
        restaurantImgUrl = getIntent().getExtras().get("restaurant_imgUrl").toString();

        populateItems();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modularActivity = new Intent(getApplicationContext(), OrderingModulActivity.class);
                startActivity(modularActivity);
                finish();
            }
        });

    }

    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            else{
                onBackPressed();
            }
        }
    };



    private void populateItems(){
        ImageView restaurant_imgUrl = findViewById(R.id.profileImageView);
        TextView restaurant_name = findViewById(R.id.restaurantNameTextView);
        TextView restaurant_street = findViewById(R.id.restaurantStreetTextView);
        TextView restaurant_houseNumber = findViewById(R.id.restaurantHouseNumberTextView);
        TextView restaurant_city = findViewById(R.id.restaurantCityTextView);
        TextView restaurant_email = findViewById(R.id.restaurantEmailTextView);

        //RelativeLayout sw = findViewById(R.id.scrollViewRestaurantProfile);
        //RelativeLayout rl = findViewById(R.id.modularButtonLayout);
        navigationItems = new ArrayList<>();
        navigationItems.add(new RestaurantDishList());
        navigationItems.add(new qrScanning());

        restaurant_name.setText(restaurantName);
        restaurant_street.setText(restaurantStreet);
        restaurant_houseNumber.setText(restaurantHouseNumber);
        restaurant_city.setText(restaurantCity);
        restaurant_email.setText(restaurantEmail);
        Picasso.get().load(restaurantImgUrl).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(restaurant_imgUrl);

        button = findViewById(R.id.btnNaruci);

        for (int i = 0; i < navigationItems.size(); i++) {
            NavigationItem item = navigationItems.get(i);
            //rl.addView(item.getButton(this));
        }

    }
}
