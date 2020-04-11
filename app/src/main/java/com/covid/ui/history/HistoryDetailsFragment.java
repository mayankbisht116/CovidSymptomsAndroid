package com.covid.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.covid.R;
import com.covid.Utils;
import com.covid.databinding.FragmentHistorydetailsBinding;
import com.covid.model.SelfReport;

import java.util.ArrayList;

public class HistoryDetailsFragment extends Fragment {
    FragmentHistorydetailsBinding binding;
    Toolbar toolbar;
    ArrayList questionModels;
    private QuestionAdapter adapter;
    SelfReport report;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState=getArguments();
        report=(SelfReport)savedInstanceState.getSerializable("report");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_historydetails, container, false);
        initTimings();
        View view = binding.getRoot();
        toolbar = binding.toolbarHistoryDetails;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new HistoryFragment());
                fragmentTransaction.commit();
            }
        });

        if(report.howImFeeling.equals("well")){
            binding.checkfeltWell.setChecked(true);
            binding.checkfeltWell.setEnabled(false);
            binding.checkfeltUnwell.setEnabled(false);
            removeQuestions();
//            binding.checkfeltWell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    binding.checkfeltWell.setChecked(true);
//                    if (isChecked == true) {
//                        binding.checkfeltUnwell.setChecked(false);
//                        removeQuestions();
//                    }
//                }
//            });
        }
        else if(report.howImFeeling.equals("unwell")) {
            binding.checkfeltUnwell.setChecked(true);
            binding.checkfeltUnwell.setEnabled(false);
            binding.checkfeltWell.setEnabled(false);
            populateQuestions();
//            binding.checkfeltUnwell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    binding.checkfeltUnwell.setChecked(true);
//                    if (isChecked == true) {
//                        binding.checkfeltWell.setChecked(false);
//                        binding.checkfeltUnwell.setEnabled(false);
//                        populateQuestions();
//                    }
//                }
//            });
        }
        return view;
    }

    private void initTimings(){
        binding.tvHistoryDetailsTitle.setText(Utils.getHistoryFormatedDate(report.getCreationTime()));
        binding.setDate.setText(Utils.getHistoryFormatedDate(report.getCreationTime()));
        binding.settime.setText(Utils.getHistoryFormatedTime(report.getCreationTime()));
    }
    private void removeQuestions() {
        //  binding.listViewQuestions.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,questions));
        binding.tvwasexhibiting.setText("");
        binding.listViewQuestionsAnswered.setAdapter(null);
    }

    private void populateQuestions() {
        binding.tvwasexhibiting.setText("SYMPTOMS");
        questionModels = new ArrayList();
//        questionModels.add(new QuestionModel("Fever", false));
//        questionModels.add(new QuestionModel("Cough", false));
//        questionModels.add(new QuestionModel("Vomiting", false));
//        questionModels.add(new QuestionModel("Shortness of breath", false));
//        questionModels.add(new QuestionModel("Stomach ache", false));
//        questionModels.add(new QuestionModel("Diarrhea", false));
//        questionModels.add(new QuestionModel("Loss of smell", false));
//        questionModels.add(new QuestionModel("Loss of taste", false));
//        questionModels.add(new QuestionModel("Headache", false));
//        questionModels.add(new QuestionModel("Fatigue or Tiredness", false));
//        questionModels.add(new QuestionModel("Sneezing", false));

/************* new logic down /
 *
 */

        if(report.symptoms.Fever != null && report.symptoms.Fever.isfever==true){
            //Whatever is condition for fever
            questionModels.add(new QuestionModel("Fever", true));
        }
        if(report.symptoms.Cough != null && report.symptoms.Cough.contains("Cough")) {
//            //Whatever condn for cough
            questionModels.add(new QuestionModel("Cough", true));
        }
        if(report.symptoms.Vomiting==true){
            questionModels.add(new QuestionModel("Vomiting", true));
        }
        if(report.symptoms.Shortness_of_Breath==true){
            questionModels.add(new QuestionModel("Shortness of breath", true));
        }
        if(report.symptoms.Stomach_ache==true){
            questionModels.add(new QuestionModel("Stomach ache", true));
        }
        if(report.symptoms.Diarrhea==true){
            questionModels.add(new QuestionModel("Diarrhea", true));
        }
        if(report.symptoms.Loss_of_smell==true){
            questionModels.add(new QuestionModel("Loss of smell", true));
        }
        if(report.symptoms.Loss_of_taste==true){
            questionModels.add(new QuestionModel("Loss of taste", true));
        }
        if(report.symptoms.Headache==true){
            questionModels.add(new QuestionModel("Headache", true));
        }
        if(report.symptoms.Fatigue==true){
            questionModels.add(new QuestionModel("Fatigue or Tiredness", true));
        }
        if(report.symptoms.Sneezing==true){
            questionModels.add(new QuestionModel("Sneezing", true));
        }
        if(report.symptoms.Muscle_ache==true){
            questionModels.add(new QuestionModel("Muscle Ache", true));
        }
        if(report.symptoms.Pain_in_chest==true){
            questionModels.add(new QuestionModel("Pain in Chest with deep breaths", true));

        }

        adapter = new QuestionAdapter(questionModels, getContext().getApplicationContext());
        binding.listViewQuestionsAnswered.setAdapter(adapter);
        binding.listViewQuestionsAnswered.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                QuestionModel questionModel = (QuestionModel) questionModels.get(position);
//                questionModel.checked = !questionModel.checked;
//                adapter.notifyDataSetChanged();
            }
        });

    }
}
