package foi.air.rse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import foi.air.rse.Controllers.shared.StartActivity;

import com.example.readysteadyeat.R;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntetnt = new Intent(MainActivity.this, StartActivity.class);
                startActivity(homeIntetnt);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}