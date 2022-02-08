package com.acentura.splashstart.base;

import android.content.Context;

import com.acentura.splashstart.BuildConfig;
import com.acentura.splashstart.di.component.AppComponent;
import com.acentura.splashstart.di.component.DaggerAppComponent;
import com.acentura.splashstart.util.TimberDebugTree;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class BaseApplication extends DaggerApplication {
    private AppComponent appComponent;


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        appComponent.inject(this);
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new TimberDebugTree());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
