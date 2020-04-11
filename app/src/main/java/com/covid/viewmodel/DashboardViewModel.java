package com.covid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.covid.model.SelfReport;
import com.covid.model.User;
import com.covid.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardViewModel extends AndroidViewModel {

    private static final String TAG = DashboardViewModel.class.getSimpleName();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }


}
