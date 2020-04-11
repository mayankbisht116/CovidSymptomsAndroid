package com.covid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.covid.databinding.FrgtPassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private static final String TAG = ForgotPassword.class.getSimpleName();
    private FrgtPassBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.frgt_pass);
    }

    public void onResetPassword(View view) {
        String email = binding.email.getText().toString();
        if (Utils.isValidEmail(email)) {
            resetPassword(email);
        } else {
            Utils.showErrorToast(this, getString(R.string.err_email));
        }
    }

    public void onBackLogin(View view) {
        startActivity(new Intent(this, SignIn.class));
        finish();
    }

    private void resetPassword(String emailAddress) {
        Utils.showLoading(ForgotPassword.this, getString(R.string.pls_wait));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            Log.d(TAG,  getString(R.string.email_sent));
                            Utils.showToast(ForgotPassword.this, getString(R.string.email_sent));
                            onBackLogin(null);
                        } else {
                            Utils.showErrorToast(ForgotPassword.this, getString(R.string.err_rest_pass));
                        }
                    }
                });
    }
}
