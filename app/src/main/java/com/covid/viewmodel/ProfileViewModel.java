package com.covid.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.covid.model.User;
import com.covid.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends AndroidViewModel {

    private static final String TAG = ProfileViewModel.class.getSimpleName();

    private UserRepository userRepo;
    private LiveData<User> userLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepository();
    }

    public LiveData<User> getUserProfile(){
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = fbUser.getUid();
        Log.e(TAG,"getUserProfile : uuid : "+uuid);
        userLiveData = userRepo.getUser(uuid);
        return  userLiveData;
    }

    public void setUserProfile(User user){
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = fbUser.getUid();
        Log.e(TAG,"getUserProfile : uuid : "+uuid);
        userRepo.insertUser(user, uuid);
    }

    public void deleteEveryThing() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Task task  = user.delete();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                }else{

                }
            }
        });
    }
}
