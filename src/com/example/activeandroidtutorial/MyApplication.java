
package com.example.activeandroidtutorial;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class MyApplication extends Application {
    public static final String TAG = "VIVZ";

    @Override
    public void onCreate() {
        super.onCreate();

        // inicializando o ActiveAndroid
        ActiveAndroid.initialize(this);
    }
}
