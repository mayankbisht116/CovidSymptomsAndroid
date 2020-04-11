package com.covid.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HealthConditions extends AbstractData{

    public boolean asthma;

    public boolean cancer;

    public boolean cardiovascularDisease;

    public boolean chronicKidneyDisease;

    public boolean chronicLungDisease;

    public boolean diabetes;

    public boolean hypertension;

    public boolean idRatherNotSay;

    public boolean immunodeficiency;

    public String smoker;

    public HealthConditions() {
    }

    public HealthConditions(boolean asthma, boolean cancer, boolean cardiovascularDisease,
                            boolean chronicKidneyDisease, boolean chronicLungDisease, boolean diabetes,
                            boolean hypertension, boolean idRatherNotSay, boolean immunodeficiency, String smoker) {
        this.asthma = asthma;
        this.cancer = cancer;
        this.cardiovascularDisease = cardiovascularDisease;
        this.chronicKidneyDisease = chronicKidneyDisease;
        this.chronicLungDisease = chronicLungDisease;
        this.diabetes = diabetes;
        this.hypertension = hypertension;
        this.idRatherNotSay = idRatherNotSay;
        this.immunodeficiency = immunodeficiency;
        this.smoker =  smoker;
    }
}
