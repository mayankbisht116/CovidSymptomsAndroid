package com.covid.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.covid.application.CovidApp;
import com.covid.model.SelfReport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelfReportRepository {

    private static final String TAG = SelfReportRepository.class.getSimpleName();
    private DatabaseReference myRef = CovidApp.getFireBaseDb().getReference("SelfReports");


    public boolean insertSelfReport(String uuid, SelfReport report){

        String key =  myRef.push().getKey();
        myRef.child(uuid).child(key).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "2 insertSelfReport:success");
                } else {
                    Log.e(TAG, "2 insertSelfReport:failure : ", task.getException());
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG,"2 onSuccess onSuccess insertSelfReport");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"2 onFailure onFailure insertSelfReport : "+e.getMessage());
            }
        });
        return true;
    }

    public MutableLiveData<List<SelfReport>> reportList(String uuid){
        List<SelfReport> listRep = new ArrayList<>();
        MutableLiveData<List<SelfReport>> reportListLiveData = new MutableLiveData<>();
        reportListLiveData.setValue(listRep);
        Query reportQuery = myRef.child(uuid)
                .orderByChild("creationTime");

        reportQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SelfReport rep;
                for (DataSnapshot repSnapshot: dataSnapshot.getChildren()) {
                    rep = repSnapshot.getValue(SelfReport.class);
                    Log.e(TAG,"repSnapshot : "+rep.getCreationTime());
                    listRep.add(rep);
                }
                Collections.reverse(listRep);
                reportListLiveData.setValue(listRep);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return reportListLiveData;
    }
//    public MutableLiveData<List<SelfReport>> reportList(String uuid){
//        List<SelfReport> listRep = new ArrayList<>();
//        MutableLiveData<List<SelfReport>> reportListLiveData = new MutableLiveData<>();
//        reportListLiveData.setValue(listRep);
//        Query myTopPostsQuery = myRef.child(uuid)
//                .orderByChild("creationTime:");
//        myTopPostsQuery.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                listRep.add(dataSnapshot.getValue(SelfReport.class));
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                listRep.add(dataSnapshot.getValue(SelfReport.class));
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                listRep.remove(dataSnapshot.getValue(SelfReport.class));
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.e(TAG,"onChildMoved : "+s);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG,"onCancelled "+databaseError.getMessage());
//            }
//        });
//
//        return reportListLiveData;
//    }

}
