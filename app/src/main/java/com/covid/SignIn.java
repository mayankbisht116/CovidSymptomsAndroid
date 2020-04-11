package com.covid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.covid.databinding.SignInBinding;
import com.covid.databinding.SignUpBinding;
import com.covid.model.HealthConditions;
import com.covid.model.User;
import com.covid.repository.UserRepository;
import com.covid.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {

    private static final String TAG = SignIn.class.getSimpleName();
    private SignInBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_in);
    }

    public void onLogIn(View view){
        loggedIn();
    }

    private void loggedIn(){
        String email = binding.email.getText().toString();
        String pass = binding.pass.getText().toString();
        if(Utils.isValidEmail(email)){
            if(TextUtils.isEmpty(pass)){
                Utils.showErrorToast(this, getString(R.string.err_pass));
                binding.email.setError(getString(R.string.err_pass));
            }else{
                doFirebaseLogin(email, pass);
            }
        }else{
            Utils.showErrorToast(this, getString(R.string.err_email));
        }
    }


    public void onForgotPass(View view){
        startActivity(new Intent(SignIn.this, ForgotPassword.class));
        finish();
    }

    public void onSignUp(View view){
        startActivity(new Intent(SignIn.this, SignUp.class));
        finish();
    }

    private void doFirebaseLogin(String email, String pass){

        Utils.showLoading(this,"Please wait...");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e(TAG,"User : "+user.getUid()+"  "+user.getEmail()+"   "+user.getDisplayName());
                            loginSuccessful();
                        } else {
                            Log.e(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            loginFailed("Authentication failed.");
                        }
                    }
                });
    }

    private void loginSuccessful(){
        startActivity(new Intent(SignIn.this, MainActivity.class));
        finish();
    }

    private void loginFailed(String msg){
//        startActivity(new Intent(SignIn.this, HomeFragment));
    }



}
