package com.covid.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User extends AbstractData{

    public String createdAt;
    public String age;
    public String email;
    public String country;
    public String gender;
    public String zip;
    public HealthConditions healthConditions;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public User() {
    }

    public User(String createdAt, String age, String email, String country, String gender,
                String zip, HealthConditions healthConditions) {
        this.createdAt = createdAt;
        this.age = age;
        this.email = email;
        this.country = country;
        this.gender = gender;
        this.zip = zip;
        this.healthConditions = healthConditions;
    }
}