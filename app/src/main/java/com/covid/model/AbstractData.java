package com.covid.model;

import com.covid.Utils;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class AbstractData implements Serializable {

    @Exclude
    protected String creationTime = Utils.formatDateTime(System.currentTimeMillis(),DATE_FORMAT);

//    public static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss Z";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime() {
        this.creationTime = Utils.formatDateTime(System.currentTimeMillis(),DATE_FORMAT);
    }

}
