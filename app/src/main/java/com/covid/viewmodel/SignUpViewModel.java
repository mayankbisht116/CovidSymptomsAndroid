package com.covid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.covid.Utils;
import com.covid.model.User;
import com.covid.repository.UserIdRepository;
import com.covid.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends AndroidViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private UserRepository usrRepo;
    private UserIdRepository usrIdRepo;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        usrRepo = new UserRepository();
        usrIdRepo = new UserIdRepository();
    }

    public void addUserInDb(String email, String uuid){
        User user = new User();
        user.email = email;
        usrRepo.insertUser(user, uuid);
        usrIdRepo.insertUser(user, uuid);
    }
}
