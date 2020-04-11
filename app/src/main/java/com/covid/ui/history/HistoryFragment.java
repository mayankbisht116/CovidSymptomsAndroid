package com.covid.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid.R;
import com.covid.application.CovidApp;
import com.covid.databinding.FragmentHistoryBinding;
import com.covid.model.SelfReport;

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryAdapter.onHistoryClickListener {

    private static final String TAG = HistoryFragment.class.getSimpleName();

    private RecyclerView.Adapter adapter;
    private Toolbar toolbar;
    FragmentHistoryBinding binding;
    private ReportViewModel reportVm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history,container,false);
        View view=binding.getRoot();
        initViewModel();
        toolbar=binding.toolbarHistory;
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        toolbar.setNavigationIcon(R.drawable.ic_add_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext().getApplicationContext(),GenerateReportActivity.class));
//            }
//        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(),GenerateReportActivity.class));
            }
        });

        return view;
    }

    private void initReportList(List<SelfReport> reports){
        adapter=new HistoryAdapter(reports,getContext().getApplicationContext(),this);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        binding.recyclerview.setAdapter(adapter);
    }

    LiveData<List<SelfReport>> reportListLiveData;
    private void initViewModel(){
        reportVm = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(CovidApp.getAppContext())).get(ReportViewModel.class);
        reportVm.getUserReportList().observe(this, new Observer<List<SelfReport>>() {
            @Override
            public void onChanged(List<SelfReport> selfReports) {
               for(SelfReport slfRep : selfReports){
                   Log.e(TAG,"initViewModel : observe : "+slfRep.getCreationTime()+"   "+slfRep.howImFeeling);
               }
                initReportList(selfReports);
            }
        });
    }


    @Override
    public void onHistoryClick(SelfReport report) {
        Log.e(TAG, "onHistoryClick: position is:  "+report.howImFeeling+"  "+report.getCreationTime());
        final FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("report", report);
        HistoryDetailsFragment frag = new HistoryDetailsFragment();
        frag.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container,frag);
        fragmentTransaction.commit();
    }
}
