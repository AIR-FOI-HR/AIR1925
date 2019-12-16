package com.example.readysteadyeat.ui.guest.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.ui.guest.HomeActivity;
import com.example.readysteadyeat.ui.shared.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogIn;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtEmail = findViewById(R.id.txtUserEmail);
        txtPassword = findViewById(R.id.txtUserPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this, com.example.readysteadyeat.ui.guest.HomeActivity.class);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = txtEmail.getText().toString();
                final String password = txtPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                }
                else
                {
                    signIn(email, password);
                }
            }
        });
    }

    private void signIn(String email, String password) {
         mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()) {
                     createUserSession();
                     updateUI();
                 }
                 else
                 {
                     showMessage(task.getException().getMessage());
                 }
             }
         });
    }

    private void createUserSession() {
        FirebaseUser user  = mAuth.getCurrentUser();
        //dohvatiti ime
        // i sve ostale podatke i staviti u sharedpreferneces klasu
    }

    //Metoda za SignOut koju je ne moguce dodati dok nemam uredi profil korisnika fragment
    private void userSignOut(){
        FirebaseAuth.getInstance().signOut();
        //prosljediti klase iz kojih se korisnik odjavljuje
        //startActivity(new Intent(HomeActivity.class, LogInActivity.class));
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user  = mAuth.getCurrentUser();

        if(user != null) {
            //updateUI();
        }
    }
}
