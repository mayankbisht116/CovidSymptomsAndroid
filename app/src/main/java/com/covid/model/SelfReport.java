package com.covid.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class SelfReport extends AbstractData {

    public String howImFeeling;

    public Symptoms symptoms;

    public SelfReport() {
    }

    public SelfReport(String howImFeeling, Symptoms symptoms) {
        this.howImFeeling = howImFeeling;
        this.symptoms = symptoms;
    }
}
