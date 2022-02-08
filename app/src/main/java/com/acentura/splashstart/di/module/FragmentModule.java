package com.acentura.splashstart.di.module;

import com.acentura.splashstart.ui.main.fragments.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment bindMainFragment();
}
