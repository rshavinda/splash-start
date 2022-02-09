package com.acentura.splashstart.di.module;

import com.acentura.splashstart.ui.main.fragments.EventFragment;
import com.acentura.splashstart.ui.main.fragments.HomeFragment;
import com.acentura.splashstart.ui.main.fragments.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment bindMainFragment();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract EventFragment bindEventFragment();
}
