package com.covid.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.covid.SignIn;
import com.covid.application.CovidApp;
import com.covid.model.HealthConditions;
import com.covid.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

public class UserRepository {

    private static final String TAG = UserRepository.class.getSimpleName();

//    private static final UserRepository repo = new UserRepository();
//
//    public static UserRepository getInstance(){
//        return repo;
//    }



    public MutableLiveData<User> getUser(String uuid){
        DatabaseReference myRef = CovidApp.getFireBaseDb().getReference("Users");
        final MutableLiveData<User> userLd = new MutableLiveData<>();
        myRef.child(uuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userLd.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG,"Error Getting User In Dashboard");
            }
        });
        return userLd;
    }


    public void insertUser(User user, String uuid){
        DatabaseReference myRef = CovidApp.getFireBaseDb().getReference("Users");
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


//    public void updateUserHealthConditions(String email, HealthConditions hlthCon){
//        DatabaseReference myRef = CovidApp.getFireBaseDb().getReference("Users");
//        myRef.child("Users").child(email).child("healthConditions").setValue(hlthCon).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "2 updateUserHealthConditions:success");
//                } else {
//                    Log.e(TAG, "2 updateUserHealthConditions:failure : ", task.getException());
//                }
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.e(TAG,"2 onSuccess onSuccess updateUserHealthConditions");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG,"2 onFailure onFailure updateUserHealthConditions : "+e.getMessage());
//            }
//        });
//    }


}
