package com.covid.ui.history;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.covid.model.SelfReport;
import com.covid.model.User;
import com.covid.repository.SelfReportRepository;
import com.covid.repository.UserRepository;
import com.covid.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private static final String TAG = ProfileViewModel.class.getSimpleName();

    private SelfReportRepository repRepo;
    private LiveData<List<SelfReport>> reportListLiveData;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        this.repRepo = new SelfReportRepository();
    }

    public LiveData<List<SelfReport>> getUserReportList(){
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = fbUser.getUid();
        Log.e(TAG,"getUserReports : uuid : "+uuid);
        reportListLiveData = repRepo.reportList(uuid);
        return reportListLiveData;
    }

    public void addReport(SelfReport report){
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = fbUser.getUid();
        Log.e(TAG,"setReport : uuid : "+uuid);
        repRepo.insertSelfReport(uuid, report);
    }

}
