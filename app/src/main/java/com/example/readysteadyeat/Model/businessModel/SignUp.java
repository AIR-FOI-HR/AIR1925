package com.example.readysteadyeat.Model.businessModel;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.readysteadyeat.Model.data.Guest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUp {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public void a(){

    }
    public  void CreateUserAccount(final Guest newUser, final String password, final Uri pickedImgUrl) {
        mAuth.createUserWithEmailAndPassword(newUser.email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            ///lsdfdk
                            myRef.child(newUser.userId).setValue(newUser);
                        }
                        else
                        {
                        }
                    }
                });
    }
}
