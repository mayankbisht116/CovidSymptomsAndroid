package com.covid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.covid.application.CovidApp;
import com.covid.databinding.SignUpBinding;
import com.covid.model.User;
import com.covid.viewmodel.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.covid.Utils.PRIVACY_POLICY;
import static com.covid.Utils.TERMS_CONDITIONS;

public class SignUp extends AppCompatActivity {

    private static final String TAG = SignUp.class.getSimpleName();
    private SignUpBinding binding;
    private SignUpViewModel signUpVm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_up);
        initViewModel();
        setUpViews();
    }

    private void initViewModel(){
        signUpVm = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(CovidApp.getAppContext())).get(SignUpViewModel.class);
    }

    private void setUpViews() {
        URLSpan termUrlSpan = new URLSpan(TERMS_CONDITIONS);
        URLSpan ppUrlSpan = new URLSpan(PRIVACY_POLICY);
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(getString(R.string.sign_up_agree));
        spanTxt.append(getString(R.string.terms_of_srvcs));
        spanTxt.setSpan(termUrlSpan, 37, spanTxt.length(), 0);
        spanTxt.append(" "+getString(R.string.and)+" ");
        spanTxt.append(getString(R.string.privacy_policy));
        spanTxt.setSpan(ppUrlSpan, spanTxt.length() - getString(R.string.privacy_policy).length(), spanTxt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.terms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.terms.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public void onSignUpPressed(View view){
        String email = binding.email.getText().toString().trim();
        if(Utils.isValidEmail(email)){
            String pass = binding.pass.getText().toString();
            String cnfrmPass = binding.cp.getText().toString();
            if(TextUtils.isEmpty(pass) || TextUtils.isEmpty(cnfrmPass)){
                Utils.showErrorToast(this, getString(R.string.err_pass));
            }else if(pass.contentEquals(cnfrmPass)){
                doSignUp(email, pass);
            }else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        }else{
            Utils.showErrorToast(this, getString(R.string.err_email));
        }
    }

    private void doSignUp(String email, String pass){
        Utils.showLoading(this,"Please wait...");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createNewUser((user));
                            onLogIn(null);
                        } else {

                            Log.e(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(SignUp.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createNewUser(FirebaseUser user){
        signUpVm.addUserInDb(user.getEmail(), user.getUid());
    }

    public void onLogIn(View view){
        startActivity(new Intent(this, SignIn.class));
        finish();
    }

}
