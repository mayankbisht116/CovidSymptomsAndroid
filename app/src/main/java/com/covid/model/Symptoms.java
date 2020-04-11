package com.covid.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Symptoms{

    public String Cough;
    public boolean Diarrhea;
    public boolean Fatigue;
    public boolean Headache;
    public boolean Loss_of_smell;
    public boolean Loss_of_taste;
    public boolean Shortness_of_Breath;
    public boolean Sneezing;
    public boolean Stomach_ache;
    public boolean Vomiting;
    public boolean Muscle_ache;
    public boolean Pain_in_chest;
    public Fever Fever;

    public Symptoms() {
    }

    public Symptoms(String cough, boolean diarrhea, boolean fatigue, boolean headache, boolean loss_of_smell, boolean loss_of_taste, boolean shortness_of_Breath, boolean sneezing, boolean stomach_ache, boolean vomiting, boolean muscle_ache, boolean pain_in_chest, com.covid.model.Fever fever) {
        Cough = cough;
        Diarrhea = diarrhea;
        Fatigue = fatigue;
        Headache = headache;
        Loss_of_smell = loss_of_smell;
        Loss_of_taste = loss_of_taste;
        Shortness_of_Breath = shortness_of_Breath;
        Sneezing = sneezing;
        Stomach_ache = stomach_ache;
        Vomiting = vomiting;
        Muscle_ache = muscle_ache;
        Pain_in_chest = pain_in_chest;
        Fever = fever;
    }

    public Symptoms(String cough, boolean diarrhea, boolean fatigue, boolean headache, boolean loss_of_smell,
                    boolean loss_of_taste, boolean shortness_of_Breath, boolean sneezing, boolean stomach_ache,
                    boolean vomiting, com.covid.model.Fever fever) {
        Cough = cough;
        Diarrhea = diarrhea;
        Fatigue = fatigue;
        Headache = headache;
        Loss_of_smell = loss_of_smell;
        Loss_of_taste = loss_of_taste;
        Shortness_of_Breath = shortness_of_Breath;
        Sneezing = sneezing;
        Stomach_ache = stomach_ache;
        Vomiting = vomiting;
        Fever = fever;
    }
}
