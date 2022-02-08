package com.acentura.splashstart.di.module;

import android.app.Application;
import android.content.Context;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
//    private Context context;

    @Binds
    abstract Context bindContext(Application application);


//    public AppModule(Context context) {
//        this.context = context;
//    }
//
//    @Provides
//    @Singleton
//    Context provideContext() {
//        return context;
//    }
}
