package foi.air.rse.Controllers.guest.restaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.readysteadyeat.R;
import com.google.android.material.navigation.NavigationView;

public class OrderingModulActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{

    //private Util util = new Util();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_modul);

        initializeLayout();

        initializeLayout();

        initializeNavigationManager();
        startMainModule();
    }

    private void initializeLayout()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //this listener has to be after the drawerToggle is initialized
        toolbar.setNavigationOnClickListener(navigationClick);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initializeNavigationManager() {
        NavigationManager nm = NavigationManager.getInstance();

        nm.setDrawerDependencies(
                this,
                navigationView,
                drawerLayout,
                R.id.dynamic_group);
    }

    private void startMainModule() {
        NavigationManager.getInstance().startMainModule();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId())
        {
            case R.id.menu_about:
                //do something
                break;
            default:
                NavigationManager.getInstance().selectNavigationItem(menuItem);
                break;
        }
        return false;
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

}
