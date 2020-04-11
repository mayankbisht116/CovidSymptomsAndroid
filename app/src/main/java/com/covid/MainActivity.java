package com.covid;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.covid.ui.history.HistoryFragment;
import com.covid.ui.home.HomeFragment;
import com.covid.ui.profile.HealthConditionFragment;
import com.covid.ui.profile.ProfileFragment;
import com.covid.ui.profile.ProfileInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static com.covid.ui.profile.ProfileInfoFragment.INFO_ASK;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new HomeFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment=new HomeFragment();
                            break;

                        case R.id.nav_profile:
                            selectedFragment=new ProfileFragment();
                            break;

                        case R.id.nav_history:
                            selectedFragment=new HistoryFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,selectedFragment)
                            .commit();
                    return true;
                }
            };

    public void onCountryClicked(View v){
        Fragment selectedFragment=getProfileInfoFrag();
        Bundle bund = getProfileInfoBundle(1);
        selectedFragment.setArguments(bund);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container,selectedFragment).addToBackStack("")
//                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,selectedFragment).addToBackStack("").addToBackStack(null)
                .commit();
    }

    public void onZipClicked(View v){
        Fragment selectedFragment=getProfileInfoFrag();
        Bundle bund = getProfileInfoBundle(2);
        selectedFragment.setArguments(bund);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,selectedFragment).addToBackStack(null)
                .commit();
    }

    public void onAgeClicked(View v){
        Fragment selectedFragment=getProfileInfoFrag();
        Bundle bund = getProfileInfoBundle(3);
        selectedFragment.setArguments(bund);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,selectedFragment).addToBackStack(null)
                .commit();
    }

    public void onGenderClicked(View v){
        Fragment selectedFragment=getProfileInfoFrag();
        Bundle bund = getProfileInfoBundle(4);
        selectedFragment.setArguments(bund);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,selectedFragment).addToBackStack(null)
                .commit();
    }

    private Fragment getProfileInfoFrag(){
        return new ProfileInfoFragment();
    }
    private Bundle getProfileInfoBundle(int no){
        Fragment selectedFragment=new ProfileInfoFragment();
        Bundle bund = new Bundle();
        bund.putInt(INFO_ASK, no);
        return bund;
    }

    public void onHealthCondClicked(View v){
        Fragment selectedFragment=new HealthConditionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,selectedFragment).addToBackStack(null)
                .commit();
    }

    public void onLogOut(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }

    public void onDelAcc(View v){

    }
}
