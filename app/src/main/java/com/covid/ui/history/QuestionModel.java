package com.covid.ui.history;

public class QuestionModel {

    public String ques;
    boolean checked;

    public QuestionModel(String ques, boolean checked) {
        this.ques = ques;
        this.checked = checked;
    }

    public String getQues() {
        return ques;
    }

    public boolean isChecked() {
        return checked;
    }
}
