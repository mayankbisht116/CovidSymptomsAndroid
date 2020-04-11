package com.covid.application;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class CovidApp extends Application {

    private static final String TAG = CovidApp.class.getSimpleName();
    private static FirebaseDatabase database;
    private static Application appContext;
    @Override
    public void onCreate() {
        super.onCreate();
//        Log.e(TAG,TAG);
        appContext = this;
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:646362284161:android:8706f19f72a35cadd2cd80") // Required for Analytics.
                .setApiKey("AIzaSyAdthwZIptF8gaMt7VkKhz05mDHH5XboSk") // Required for Auth.
                .setProjectId("covid-30a9e")
                .build();
        FirebaseApp.initializeApp(this, options, "covid");
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.setLogLevel(Logger.Level.DEBUG);
    }

    public static FirebaseDatabase getFireBaseDb(){
        return database;
    }

    public static Application getAppContext(){
        return appContext;
    }
}
