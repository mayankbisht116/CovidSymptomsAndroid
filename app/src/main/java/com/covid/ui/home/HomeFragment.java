package com.covid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.covid.BuildConfig;
import com.covid.R;
import com.covid.ui.history.GenerateReportActivity;

public class HomeFragment extends Fragment {

    AppCompatImageView well,unwell;
    AppCompatImageView share;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initActionBar();
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        well=view.findViewById(R.id.btn_well);
        unwell=view.findViewById(R.id.btn_unwell);
        share=view.findViewById(R.id.btn_share);
        well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWellClicked();
            }
        });

        unwell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUnwellClicked();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initProfileViewModel();
    }

    private void initActionBar(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View actionView = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView();
        ((TextView)actionView.findViewById(R.id.cus_title)).setText(getString(R.string.title_home));
    }
    private void onUnwellClicked() {
        startActivity(new Intent(getContext().getApplicationContext(), GenerateReportActivity.class));

    }

    private void onWellClicked() {


    }

    private void share(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= getString(R.string.share_txt);
//            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {

        }
    }


}
