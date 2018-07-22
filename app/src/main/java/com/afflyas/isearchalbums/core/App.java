package com.afflyas.isearchalbums.core;

import android.app.Activity;
import android.app.Application;

import com.afflyas.isearchalbums.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    public static final String DEV_TAG = "myDev";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        //init Dagger injections
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
