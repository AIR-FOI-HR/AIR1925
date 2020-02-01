package foi.air.rse.Controllers.guest.restaurants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.readysteadyeat.R;
import com.google.android.material.textfield.TextInputEditText;

public class OrderSummaryActivity extends AppCompatActivity {

    public String key;
    private Button btnPersons;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        key=intent.getStringExtra("key");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary2);

        }
}

