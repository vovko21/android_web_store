package com.example.androidstore.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class HomeApplication extends Application {
    private static HomeApplication instance;
    private static Context appContext;
    private int userId;

    public static HomeApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public boolean chechUser() {
        if(userId == -1) {
            return false;
        }
        return true;
    }

    public void setUser (int userId) {
        this.userId = userId;
    }
}
