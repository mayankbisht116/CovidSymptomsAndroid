package com.covid.ui.profile;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.covid.MainActivity;
import com.covid.R;
import com.covid.SignIn;
import com.covid.Utils;
import com.covid.application.CovidApp;
import com.covid.databinding.ProfileBinding;
import com.covid.model.User;
import com.covid.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static com.covid.Utils.PRIVACY_POLICY;
import static com.covid.Utils.TERMS_CONDITIONS;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private ProfileBinding binding;
    private ProfileViewModel profileVm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile,container,false);
        binding.setFrag(this);
        initActionBar();
        initProfileViewModel();
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initActionBar(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View actionView = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView();
        ((TextView)actionView.findViewById(R.id.cus_title)).setText(R.string.profile);
    }
    private void initProfileViewModel() {
        profileVm = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(CovidApp.getAppContext())).get(ProfileViewModel.class);
        profileVm.getUserProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                    Log.e(TAG,"Observing User : "+user.email+"   "+user.country);
                binding.setUserdata(user);
            }
        });
    }

    public void onCountryClicked(View v){
        ((MainActivity)getActivity()).onCountryClicked(v);
    }

    public void onZipClicked(View v){
        ((MainActivity)getActivity()).onZipClicked(v);
    }

    public void onAgeClicked(View v){
        ((MainActivity)getActivity()).onAgeClicked(v);
    }

    public void onGenderClicked(View v){
        ((MainActivity)getActivity()).onGenderClicked(v);
    }

    public void onHealthCondClicked(View v){
        ((MainActivity)getActivity()).onHealthCondClicked(v);
    }

    public void onLogOut(View v){
        ((MainActivity)getActivity()).onLogOut(v);
    }

    public void onDelAcc(View v){
        showDeleteAccountDlg();
    }

    private void showDeleteAccountDlg(){
        String title = "Are you sure?";
        String msg = "All your account information will be lost.";
        String pstStrng = "Delete Account";
        String ngtvStrng = "Cancel";
        DialogInterface.OnClickListener pstvLstnr = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDonateDlg();
            }
        };

        DialogInterface.OnClickListener ngtvLstnr = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        Utils.showDoubleBtnDialog(getActivity(), title, msg, pstStrng, pstvLstnr, ngtvStrng, ngtvLstnr);
    }

    private void showDonateDlg(){
        String title = "Donate your history for medical research?";
        String msg = "All your information will be anonymized. All your profile information will still get deleted in our database.";
        String pstStrng = "Yes, donate my histoy!";
        String ngtvStrng = "Delete Everything";
        DialogInterface.OnClickListener pstvLstnr = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                ((MainActivity)getActivity()).onLogOut(null);
                dialog.dismiss();
                showAccountDeletedDlg();
            }
        };

        DialogInterface.OnClickListener ngtvLstnr = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteEveryThing();
                showAccountDeletedDlg();
            }
        };
        Utils.showDoubleBtnDialog(getActivity(), title, msg, pstStrng, pstvLstnr, ngtvStrng, ngtvLstnr);
    }

    private void showAccountDeletedDlg(){
        String title = "Your account has been deleted.";
        String msg = "We hope you found us useful.";
        String pstStrng = getString(R.string.ok);
        DialogInterface.OnClickListener pstvLstnr = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((MainActivity)getActivity()).onLogOut(null);
            }
        };
        Utils.showSingleBtnDialog(getActivity(), title, msg, pstStrng, pstvLstnr);
    }

    private void deleteEveryThing(){
        profileVm.deleteEveryThing();
    }

    public void onTermsClicked(){
        openInBrowser(TERMS_CONDITIONS);
    }

    public void onPrivacyPolicyClicked(){
        openInBrowser(PRIVACY_POLICY);
    }

    private void openInBrowser(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}
