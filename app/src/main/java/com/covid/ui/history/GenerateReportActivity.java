package com.covid.ui.history;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.covid.R;
import com.covid.application.CovidApp;
import com.covid.databinding.ActivityGenerateReportBinding;
import com.covid.model.Fever;
import com.covid.model.SelfReport;
import com.covid.model.Symptoms;
import com.covid.ui.FeverSeekbarActivity;

import java.util.ArrayList;

public class GenerateReportActivity extends AppCompatActivity {

    public int temperature = 0;
    public boolean checkSkipped=false;
    public boolean coughCancelled=false;
    public String typeOfCough = "";
    ActivityGenerateReportBinding binding;
    Toolbar toolbar;
    ArrayList questionModels;
    private QuestionAdapter adapter;
    private SelfReport report;
    // String[] questions= new String[]{"Fever","Cough","Vomiting","Shortness of breath","Stomach ache","Diarrhea","Loss of smell","Loss of taste","Headache"};
    private ReportViewModel reportVm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_report);
//        toolbar = binding.toolbar;
//        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_cancel_black_24dp);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        report = new SelfReport();
        report.symptoms = new Symptoms();
        report.symptoms.Fever = new Fever();
        binding.unwellLayout.setVisibility(View.GONE);
        binding.checkUnwell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.checkWell.setChecked(false);
                    populateQuestions();
                    report.howImFeeling = "unwell";
                } else {
                    removeQuestions();
                }
            }
        });

        binding.checkWell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.checkUnwell.setChecked(false);
                    removeQuestions();
                    report.howImFeeling = "well";
                }
            }
        });
    }


    private void initActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.add_report_actionbar);
        View actionView = getSupportActionBar().getCustomView();
        actionView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckedSymptoms();
                finish();
            }
        });
        actionView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewModel(){
        reportVm = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(CovidApp.getAppContext())).get(ReportViewModel.class);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.cancel) {
//            finish();
//            return true;
//        } else if (id == R.id.done) {
//            //do whatever
//            getCheckedSymptoms();
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void getCheckedSymptoms() {
        //gets the checked items from the list of questions

        //Logic to perform when DONE button is clicked..

//        StringBuffer sb = new StringBuffer();
//        for (QuestionModel bean ) {
//
//            if (bean.isChecked()) {
//                sb.append(bean.getQues());
//                sb.append(",");
//            }
//        }
        reportVm.addReport(report);
    }

    private void removeQuestions() {
        binding.unwellLayout.setVisibility(View.GONE);
        binding.tvexhibiting.setText("");
        binding.listViewQuestions.setAdapter(null);
    }

    static final String FEVER = "Running Fever";
    static final String COUGH = "Cough";
    static final String VOMITING = "Vomiting";
    static final String BREATH_SHORTNESS = "Shortness of breath";
    static final String STOMACH_ACHE = "Stomach ache";
    static final String DIARRHEA = "Diarrhea";
    static final String SMELL_LOSS = "Loss of smell";
    static final String TASTE_LOSS = "Loss of taste";
    static final String HEADACHE = "Headache";
    static final String FATIGUE = "Fatigue or Tiredness";
    static final String SNEEZING = "Sneezing";
    static final String MUSCLE_ACHES = "Muscle aches";
    static final String PAIN_IN_CHEST = "Pain in chest with deep breaths";

    private void populateQuestions() {
        binding.unwellLayout.setVisibility(View.VISIBLE);
        binding.tvexhibiting.setText(R.string.what_sym_do_u_hv);
        questionModels = new ArrayList();

        questionModels.add(new QuestionModel("Running Fever", false));
        questionModels.add(new QuestionModel("Cough", false));
        questionModels.add(new QuestionModel("Vomiting", false));
        questionModels.add(new QuestionModel("Shortness of breath", false));
        questionModels.add(new QuestionModel("Stomach ache", false));
        questionModels.add(new QuestionModel("Diarrhea", false));
        questionModels.add(new QuestionModel("Loss of smell", false));
        questionModels.add(new QuestionModel("Loss of taste", false));
        questionModels.add(new QuestionModel("Headache", false));
        questionModels.add(new QuestionModel("Fatigue or Tiredness", false));
        questionModels.add(new QuestionModel("Sneezing", false));
        questionModels.add(new QuestionModel("Muscle aches", false));
        questionModels.add(new QuestionModel("Pain in chest with deep breaths", false));

        adapter = new QuestionAdapter(questionModels, getApplicationContext());
        binding.listViewQuestions.setAdapter(adapter);
        binding.listViewQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuestionModel questionModel = (QuestionModel) questionModels.get(position);
                questionModel.checked = !questionModel.checked;
                if (questionModel.getQues().toString() == "Running Fever" && questionModel.isChecked() == true) {
                    //start new activity to capture fever
                    showFeverActivity();
                }
                if (questionModel.getQues().toString() == "Cough" && questionModel.isChecked() == true) {
                    //show alert for cough
                    showCoughAlertBox();
                }
                setSymptoms(questionModel.getQues().toString());
                adapter.notifyDataSetChanged();
            }
        });

        report.symptoms.Cough="";
    }

    private void setSymptoms(String question){

        switch (question){
            case FEVER:

                QuestionModel questionModel=(QuestionModel)questionModels.get(0);

                if(questionModel.isChecked()==true){
                    // if fever checkbox is checked then only set fever to---->
                    report.symptoms.Fever.isfever=true;
                    report.symptoms.Fever.temp=String.valueOf(temperature) ;
                }
                else{
                    report.symptoms.Fever.isfever=false;
                    temperature=0;
                    report.symptoms.Fever.temp=String.valueOf(temperature) ;
                }


                break;
//            case COUGH:
//                break;
            case VOMITING:
                report.symptoms.Vomiting = true;
                break;
            case BREATH_SHORTNESS:
                report.symptoms.Shortness_of_Breath = true;
                break;
            case STOMACH_ACHE:
                report.symptoms.Stomach_ache = true;
                break;
            case DIARRHEA:
                report.symptoms.Diarrhea = true;
                break;
            case SMELL_LOSS:
                report.symptoms.Loss_of_smell = true;
                break;
            case TASTE_LOSS:
                report.symptoms.Loss_of_taste = true;
                break;
            case HEADACHE:
                report.symptoms.Headache = true;
                break;
            case FATIGUE:
                report.symptoms.Fatigue = true;
                break;
            case SNEEZING:
                report.symptoms.Sneezing = true;
                break;
            case MUSCLE_ACHES:
                report.symptoms.Muscle_ache = true;
                break;
            case PAIN_IN_CHEST:
                report.symptoms.Pain_in_chest = true;
                break;
        }
    }


    private void showFeverActivity() {
        Intent intent = new Intent(GenerateReportActivity.this, FeverSeekbarActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //set the temperature recieved via intent from FeverSeekbar activity
            int temp = data.getIntExtra("Temp", 95);
            Toast.makeText(this, "Temperature recv:" + temperature, Toast.LENGTH_SHORT).show();
            if(report.symptoms.Fever == null)
                report.symptoms.Fever = new Fever();
            report.symptoms.Fever.temp = String.valueOf(temp);
        }else if(resultCode==4){

            //if user clicks skip on feverseekbaractivity set checkSkipped boolean as true
            boolean skipped=data.getBooleanExtra("Skipped",true);
            if(skipped==true){
                Toast.makeText(getApplicationContext(),"Skipped true",Toast.LENGTH_SHORT).show();
                checkSkipped=true;

                if(checkSkipped){
                    questionModels.set(0,new QuestionModel("Running Fever",false));

                    //add the new updated object of list item for fever to adapter
                    adapter = new QuestionAdapter(questionModels, getApplicationContext());
                    binding.listViewQuestions.setAdapter(adapter);

                    checkSkipped=false;
                    return;
                }
                else {
                    questionModels.set(0,new QuestionModel("Running Fever",true));
                    checkSkipped=false;
                    return;
                }

            }

        }
    }

    private void showCoughAlertBox() {
        AlertDialog alertdialog;
        CharSequence[] values = {"Dry Cough", "Wet Cough"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("What's type of your cough?");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        //set the  public string typeOfCough
                        // report.symptoms.Cough = "Dry Cough";
                        typeOfCough="Dry Cough";
                        break;
                    case 1:
                        // report.symptoms.Cough = "Wet Cough";
                        typeOfCough="Wet Cough";
                        break;
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                report.symptoms.Cough=typeOfCough;
            }
        });

        //if user clicks cancel set boolean coughCancelled=true;
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                coughCancelled=true;
                report.symptoms.Cough="none";
                if(coughCancelled){
                    questionModels.set(1,new QuestionModel("Cough",false));
                    //add the new updated object of list item for cough to adapter
                    adapter = new QuestionAdapter(questionModels, getApplicationContext());
                    binding.listViewQuestions.setAdapter(adapter);
                    coughCancelled=false;
                    return;
                }
                else {
                    questionModels.set(1,new QuestionModel("Cough",true));
                    coughCancelled=false;
                    return;
                }
            }
        });
        alertdialog = builder.create();
        alertdialog.show();
    }

}
