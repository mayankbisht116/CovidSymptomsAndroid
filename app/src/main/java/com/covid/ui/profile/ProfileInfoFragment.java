package com.covid.ui.profile;

import android.graphics.Color;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covid.R;
import com.covid.application.CovidApp;
import com.covid.databinding.ProfileInfoBinding;
import com.covid.model.User;
import com.covid.viewmodel.ProfileViewModel;

import java.util.Arrays;
import java.util.List;

public class ProfileInfoFragment extends Fragment implements CountryListAdapter.CountryClickListener {

    private static final String TAG = ProfileInfoFragment.class.getSimpleName();
    public static final int COUNTRY = 1;
    public static final int ZIPCODE = 2;
    public static final int AGE = 3;
    public static final int GENDER = 4;
    public static final String INFO_ASK = "info_ask";
    private ProfileInfoBinding binding;
    private ProfileViewModel profileVm;
    private User user;
    private String gender = "";

    private void initProfileViewModel() {
        profileVm = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.
                getInstance(CovidApp.getAppContext())).get(ProfileViewModel.class);
        profileVm.getUserProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.e(TAG,"Observing User : "+user.email);
                setUserData(user);
                setExistingData(infoAsk,user);
            }
        });
    }

    private void setUserData(User user){
        this.user = user;
    }
    int infoAsk = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initActionBar();
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_info,container,false);
        binding.setInfoFrag(this);
        initProfileViewModel();
        Bundle args = getArguments();
        infoAsk = args.getInt(INFO_ASK);
        Log.e(TAG, "Info Asked : "+infoAsk);
        setViews(infoAsk);
        return binding.getRoot();
    }

    private void initActionBar(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.add_report_actionbar);
        View actionView = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView();
        ((TextView)actionView.findViewById(R.id.toolbar_title)).setText(R.string.profile);
        ((TextView)actionView.findViewById(R.id.toolbar_title)).setTextColor(Color.WHITE);
        ((AppCompatImageView)actionView.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ((AppCompatImageView)actionView.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClicked();
            }
        });
    }


    private void setExistingData(int infoAsk, User user){
        switch (infoAsk){
            case COUNTRY:
                binding.setSelected(user.country);
                break;
            case ZIPCODE:
                binding.setSelected(user.zip);
                break;
            case AGE:
                binding.setSelected(user.age);
                break;
            default:
                binding.setSelected("");
                break;
        }
    }


    private String title;
    private void setViews(int infoAsk){
        switch (infoAsk){
            case COUNTRY:
                title = getString(R.string.what_country);
                setUpCountryRecyclerView();
                hideGenderViews();
                binding.input.setEnabled(false);
                binding.cuntryList.setVisibility(View.VISIBLE);
                break;
            case ZIPCODE:
                title = getString(R.string.what_zip);
                hideGenderViews();
                break;
            case AGE:
                title = getString(R.string.what_age);
                hideGenderViews();
                break;
            case GENDER:
                title = getString(R.string.what_gender);
                showGenderViews();
                break;
        }
        binding.title.setText(title);
    }

    private void hideGenderViews(){
        binding.cuntryList.setVisibility(View.INVISIBLE);
        binding.input.setVisibility(View.VISIBLE);
        binding.input.requestFocus();
        binding.gndrLayout.setVisibility(View.INVISIBLE);
        gender = "";
    }

    private void showGenderViews(){
        binding.input.setVisibility(View.INVISIBLE);
        binding.gndrLayout.setVisibility(View.VISIBLE);
    }

    public void onMaleClicked(){
        gender = "Male";
//        binding.female.setBackgroundColor(getResources().getColor(R.color.design_default_color_background));
//        binding.male.setBackgroundColor(getResources().getColor(R.color.colorBlack));
//        binding.female.setBackgroundResource(R.drawable.ic_female);
//        binding.male.setBackgroundResource(R.drawable.ic_male_selected);
        binding.female.setImageResource(R.drawable.ic_female);
        binding.male.setImageResource(R.drawable.ic_male_selected);
    }

    public void onFemaleClicked(){
        gender = "Female";
        binding.female.setImageResource(R.drawable.ic_female_selected);
        binding.male.setImageResource(R.drawable.ic_male);
    }

    public void onDoneClicked(){
        if(user != null){
            setUpdatedData(infoAsk, user);
            profileVm.setUserProfile(user);
        }
        getActivity().onBackPressed();
    }

    private void setUpdatedData(int infoAsk, User user){
        switch (infoAsk){
            case COUNTRY:
                user.country = binding.input.getText().toString().trim();
                break;
            case ZIPCODE:
                user.zip = binding.input.getText().toString().trim();
                break;
            case AGE:
                user.age = binding.input.getText().toString().trim();
                break;
            case GENDER:
                user.gender = gender;
                break;
//            default:
//                user.country = binding.input.getText().toString().trim();
//                break;
        }
    }

    private void setUpCountryRecyclerView(){
        List<String> countryList = getCountryList();
        CountryListAdapter adapter = new CountryListAdapter(countryList, this );
        binding.cuntryList.setHasFixedSize(true);
        binding.cuntryList.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        binding.cuntryList.setAdapter(adapter);
    }

    @Override
    public void onCountryClick(String countryName) {
        user.country = countryName;
        binding.input.setText(user.country);
    }

    private List<String> getCountryList(){
        String[] countList = new String[]{
        "Afghanistan",
                "Albania",
                "Algeria",
                "America",
                "Andorra",
                "Angola",
                "Antigua",
                "Argentina",
                "Armenia",
                "Australia",
                "Austria",
                "Azerbaijan",
                "Bahamas",
                "Bahrain",
                "Bangladesh",
                "Barbados",
                "Belarus",
                "Belgium",
                "Belize",
                "Benin",
                "Bhutan",
                "Bissau",
                "Bolivia",
                "Bosnia",
                "Botswana",
                "Brazil",
                "British",
                "Brunei",
                "Bulgaria",
                "Burkina",
                "Burma",
                "Burundi",
                "Cambodia",
                "Cameroon",
                "Canada",
                "Cape Verde",
                "Central African Republic",
                "Chad",
                "Chile",
                "China",
                "Colombia",
                "Comoros",
                "Congo",
                "Costa Rica",
                "country debt",
                "Croatia",
                "Cuba",
                "Cyprus",
                "Czech",
                "Denmark",
                "Djibouti",
                "Dominica",
                "East Timor",
                "Ecuador",
                "Egypt",
                "El Salvador",
                "Emirate",
                "England",
                "Eritrea",
                "Estonia",
                "Ethiopia",
                "Fiji",
                "Finland",
                "France",
                "Gabon",
                "Gambia",
                "Georgia",
                "Germany",
                "Ghana",
                "Great Britain",
                "Greece",
                "Grenada",
                "Grenadines",
                "Guatemala",
                "Guinea",
                "Guyana",
                "Haiti",
                "Herzegovina",
                "Honduras",
                "Hungary",
                "Iceland",
                "in usa",
                "India",
                "Indonesia",
                "Iran",
                "Iraq",
                "Ireland",
                "Israel",
                "Italy",
                "Ivory Coast",
                "Jamaica",
                "Japan",
                "Jordan",
                "Kazakhstan",
                "Kenya",
                "Kiribati",
                "Korea",
                "Kosovo",
                "Kuwait",
                "Kyrgyzstan",
                "Laos",
                "Latvia",
                "Lebanon",
                "Lesotho",
                "Liberia",
                "Libya",
                "Liechtenstein",
                "Lithuania",
                "Luxembourg",
                "Macedonia",
                "Madagascar",
                "Malawi",
                "Malaysia",
                "Maldives",
                "Mali",
                "Malta",
                "Marshall",
                "Mauritania",
                "Mauritius",
                "Mexico",
                "Micronesia",
                "Moldova",
                "Monaco",
                "Mongolia",
                "Montenegro",
                "Morocco",
                "Mozambique",
                "Myanmar",
                "Namibia",
                "Nauru",
                "Nepal",
                "Netherlands",
                "New Zealand",
                "Nicaragua",
                "Niger",
                "Nigeria",
                "Norway",
                "Oman",
                "Pakistan",
                "Palau",
                "Panama",
                "Papua",
                "Paraguay",
                "Peru",
                "Philippines",
                "Poland",
                "Portugal",
                "Qatar",
                "Romania",
                "Russia",
                "Rwanda",
                "Samoa",
                "San Marino",
                "Sao Tome",
                "Saudi Arabia",
                "scotland",
                "scottish",
                "Senegal",
                "Serbia",
                "Seychelles",
                "Sierra Leone",
                "Singapore",
                "Slovakia",
                "Slovenia",
                "Solomon",
                "Somalia",
                "South Africa",
                "South Sudan",
                "Spain",
                "Sri Lanka",
                "St. Kitts",
                "St. Lucia",
                "St Kitts",
                "St Lucia",
                "Saint Kitts",
                "Santa Lucia",
                "Sudan",
                "Suriname",
                "Swaziland",
                "Sweden",
                "Switzerland",
                "Syria",
                "Taiwan",
                "Tajikistan",
                "Tanzania",
                "Thailand",
                "Tobago",
                "Togo",
                "Tonga",
                "Trinidad",
                "Tunisia",
                "Turkey",
                "Turkmenistan",
                "Tuvalu",
                "Uganda",
                "Ukraine",
                "United Kingdom",
                "Uruguay",
                "USA",
                "Uzbekistan",
                "Vanuatu",
                "Vatican",
                "Venezuela",
                "Vietnam",
                "wales",
                "welsh",
                "Yemen",
                "Zambia",
                "Zimbabwe"};
        return Arrays.asList(countList);

    }


}
