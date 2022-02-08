package com.acentura.splashstart.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

public abstract class BaseBindingActivity <B extends ViewDataBinding, V extends ViewModel>
        extends BaseActivity<B> {

    private V mViewModel;

    @LayoutRes
    protected abstract int layoutResId();

    protected abstract V bindViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(bindViewModel() != null) {
            mViewModel = bindViewModel();
        }
    }

    protected V getViewModel() {
        return mViewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
