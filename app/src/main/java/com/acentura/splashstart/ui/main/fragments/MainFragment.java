package com.acentura.splashstart.ui.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acentura.splashstart.R;
import com.acentura.splashstart.base.BaseBindingFragment;
import com.acentura.splashstart.databinding.FragmentMainBinding;
import com.acentura.splashstart.di.module.viewmodelfactory.ViewModelFactory;
import com.acentura.splashstart.ui.main.MainViewModel;

import javax.inject.Inject;

public class MainFragment extends BaseBindingFragment<FragmentMainBinding, MainViewModel> {

    @Inject
    ViewModelFactory viewModelFactory;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    protected int layoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected MainViewModel bindViewModel() {
        return new ViewModelProvider(this,
                viewModelFactory).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().tvDetails.setText("Data Test");
    }
}