package com.covid.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.covid.application.CovidApp;
import com.covid.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserIdRepository {

    private static final String TAG = UserIdRepository.class.getSimpleName();
    DatabaseReference myRef = CovidApp.getFireBaseDb().getReference("UserIDs");

    public void insertUser(User user, String uuid){
        myRef.child(uuid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "2 insertUser:success");
                } else {
                    Log.e(TAG, "2 insertUser:failure : ", task.getException());
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG,"2 onSuccess onSuccess insertUser");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"2 onFailure onFailure insertUser : "+e.getMessage());
            }
        });
    }

    public static void updateUser(User user){

    }

}
