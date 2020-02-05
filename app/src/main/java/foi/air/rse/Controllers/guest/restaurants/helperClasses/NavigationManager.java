package foi.air.rse.Controllers.guest.restaurants.helperClasses;


import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.core.NavigationItem;
import com.example.qrscanning.QrScanningFragment;
import com.example.readysteadyeat.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import foi.air.rse.Controllers.guest.restaurants.modular.RestaurantMenuFragment;


public class NavigationManager {
    //TODO - manage data

    //singleton
    private static NavigationManager instance;

    //manage modules
    private List<NavigationItem> navigationItems;

    //manage drawer
    private AppCompatActivity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private int dynamicGroupId;
    String restaurantID;

    private NavigationManager()
    {
        //modularnsot
        navigationItems = new ArrayList<>();
        navigationItems.add(new RestaurantMenuFragment());
        navigationItems.add(new QrScanningFragment());
    }

    public static NavigationManager getInstance()
    {
        if (instance == null)
            instance = new NavigationManager();

        return instance;
    }

    public void sendData(String restaurantId){
        restaurantID = restaurantId;
    }

    public void setDrawerDependencies(
            AppCompatActivity activity,
            NavigationView navigationView,
            DrawerLayout drawerLayout,
            int dynamicGroupId)
    {
        this.activity = activity;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.dynamicGroupId = dynamicGroupId;

        setupDrawer();
    }

    //modularnsot
    private void setupDrawer()
    {
        for (int i = 0; i < navigationItems.size(); i++) {
            NavigationItem item = navigationItems.get(i);
            navigationView.getMenu()
                    .add(dynamicGroupId, i, i+1, item.getName(activity))
                    .setCheckable(true)
                    .setIcon(item.getIcon(activity));
        }
    }

    public void startMainModule() {
        NavigationItem mainModule = navigationItems != null ? navigationItems.get(0) : null;
        if (mainModule != null)
            startModule(mainModule);
    }

    private void startModule(NavigationItem module) {
        FragmentManager mFragmentManager = activity.getSupportFragmentManager();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, module.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        Log.i("ime", module.getFragment().toString());
        DataManager.getInstance().sendData(module, restaurantID);
    }

    public void selectNavigationItem(MenuItem menuItem) {
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            NavigationItem selectedItem = navigationItems.get(menuItem.getItemId());
            startModule(selectedItem);

        }
    }
}