package com.acentura.splashstart.di.module;

import com.acentura.splashstart.di.module.scope.ActivityScoped;
import com.acentura.splashstart.ui.login.LoginActivity;
import com.acentura.splashstart.ui.main.MainActivity;
import com.acentura.splashstart.ui.splash.SplashActivity;
import com.microsoft.identity.client.BrowserTabActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {


    @ActivityScoped
    @ContributesAndroidInjector()
    abstract SplashActivity bindSplashActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract LoginActivity bindLoginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract MainActivity bindMainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract BrowserTabActivity bindBrowserTabActivity();
}
