package com.covid.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.covid.R;
import com.covid.application.CovidApp;
import com.covid.databinding.HealthConditionsFragBinding;
import com.covid.model.HealthConditions;
import com.covid.model.User;
import com.covid.viewmodel.ProfileViewModel;

public class HealthConditionFragment extends Fragment {

    private static final String TAG = HealthConditionFragment.class.getSimpleName();

    private HealthConditionsFragBinding binding;
    private ProfileViewModel profileVm;
    private User user;
    private HealthConditions hlthConditions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initProfileViewModel() {
        profileVm = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(CovidApp.getAppContext())).get(ProfileViewModel.class);
        profileVm.getUserProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.e(TAG,"Observing User : "+user.email);
                setUserData(user);
                initSmokers();
            }
        });
    }

    private void setUserData(User user){
        this.user = user;
        if(user.healthConditions == null){
            this.user.healthConditions = new HealthConditions();
        }
        this.hlthConditions = user.healthConditions;
        binding.setHlthdata(hlthConditions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.health_conditions_frag,container,false);
        binding.setHlthfrag(this);
        initViews();
        initProfileViewModel();
        return binding.getRoot();
    }

    private void initViews(){
        binding.diab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.diabetes = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.hypr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.hypertension = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.crdio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.cardiovascularDisease = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.asthma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.asthma = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.chrncLung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.chronicLungDisease = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.chrncKdny.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.chronicKidneyDisease = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.cnr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.cancer = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.imudef.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.immunodeficiency = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.silnt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.healthConditions.idRatherNotSay = isChecked;
                profileVm.setUserProfile(user);
            }
        });

        binding.smkngYs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    user.healthConditions.smoker = "Yes";
                    profileVm.setUserProfile(user);
                    handleSmokers(buttonView);
                }
            }
        });

        binding.smkngQt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    user.healthConditions.smoker = "quit";
                    profileVm.setUserProfile(user);
                    handleSmokers(buttonView);
                }
            }
        });

        binding.smkngNvr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    user.healthConditions.smoker = "neverSmoked";
                    profileVm.setUserProfile(user);
                    handleSmokers(buttonView);
                }
            }
        });
    }
    private void initSmokers(){
        if(user.healthConditions != null && user.healthConditions.smoker != null){
            if(user.healthConditions.smoker.equalsIgnoreCase("Yes")){
                binding.smkngYs.setChecked(true);
                binding.smkngQt.setChecked(false);
                binding.smkngNvr.setChecked(false);
            }else if(user.healthConditions.smoker.equalsIgnoreCase("quit")){
                binding.smkngYs.setChecked(false);
                binding.smkngQt.setChecked(true);
                binding.smkngNvr.setChecked(false);
            }else if(user.healthConditions.smoker.equalsIgnoreCase("neverSmoked")){
                binding.smkngYs.setChecked(false);
                binding.smkngQt.setChecked(false);
                binding.smkngNvr.setChecked(true);
            }
        }
    }

    private void handleSmokers(View view){
        switch(view.getId()){
            case R.id.smkng_ys:
                binding.smkngYs.setChecked(true);
                binding.smkngQt.setChecked(false);
                binding.smkngNvr.setChecked(false);
                break;
            case R.id.smkng_qt:
                binding.smkngYs.setChecked(false);
                binding.smkngQt.setChecked(true);
                binding.smkngNvr.setChecked(false);
                break;
            case R.id.smkng_nvr:
                binding.smkngYs.setChecked(false);
                binding.smkngQt.setChecked(false);
                binding.smkngNvr.setChecked(true);
                break;
        }
    }
}
