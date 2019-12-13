package com.example.readysteadyeat.ui.guest.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.readysteadyeat.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class SignUpTypeActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_type);

        initSignInButton();
        initAuthViewModel();
        initGoogleSignInClient();
    }

        private void initSignInButton() {
            SignInButton googleSignInButton = findViewById(R.id.btnGoogleSignUp);
            googleSignInButton.setOnClickListener(v -> signIn());
        }

        private void initAuthViewModel() {
            ViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        }

        private void initGoogleSignInClient() {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        }



    private void signIn() {
        //Intent signInIntent = googleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
