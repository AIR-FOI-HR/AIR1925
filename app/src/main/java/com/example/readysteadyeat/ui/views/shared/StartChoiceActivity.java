package com.example.readysteadyeat.ui.views.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.readysteadyeat.R;

public class StartChoiceActivity extends AppCompatActivity {
    private Button btnEnterSignUp;
    private Button btnEnterLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_choice);

        btnEnterSignUp = (Button) findViewById(R.id.btnEnterSignUp);
        btnEnterLogIn = (Button) findViewById(R.id.btnEnterLogIn);

        btnEnterSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivityUserTypeChoice();
            }
        });

        btnEnterLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivityLogIn();
            }
        });
    }

    public void openActivityUserTypeChoice(){
        Intent homeIntetnt = new Intent(StartChoiceActivity.this, UserTypeChoiceActivity.class);
        startActivity(homeIntetnt);
    }

    public void openActivityLogIn(){
        Intent homeIntetnt = new Intent(StartChoiceActivity.this, LogInActivity.class);
        startActivity(homeIntetnt);
    }
}
