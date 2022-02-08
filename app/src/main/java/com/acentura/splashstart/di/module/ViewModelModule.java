package com.acentura.splashstart.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.acentura.splashstart.di.module.scope.AppScoped;
import com.acentura.splashstart.di.module.util.ViewModelKey;
import com.acentura.splashstart.di.module.viewmodelfactory.ViewModelFactory;
import com.acentura.splashstart.ui.login.LoginViewModel;
import com.acentura.splashstart.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @AppScoped
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

}
