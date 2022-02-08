package com.acentura.splashstart.di.component;

import android.app.Application;

import com.acentura.splashstart.base.BaseApplication;
import com.acentura.splashstart.di.module.ActivityModule;
import com.acentura.splashstart.di.module.AppModule;
import com.acentura.splashstart.di.module.ViewModelModule;
import com.acentura.splashstart.di.module.network.NetModule;
import com.acentura.splashstart.di.module.scope.AppScoped;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

@AppScoped
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        ActivityModule.class, ViewModelModule.class, NetModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(BaseApplication baseApplication);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
