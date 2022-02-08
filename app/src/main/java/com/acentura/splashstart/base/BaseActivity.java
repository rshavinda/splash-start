package com.acentura.splashstart.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import com.acentura.splashstart.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity <B extends ViewDataBinding> extends DaggerAppCompatActivity {

    private B mBinding;
    private ViewModel mViewModel;
    private Dialog mProgressDialog;

    @LayoutRes
    protected abstract int layoutResId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        bindViewDataBinding(layoutResId());
    }

    protected B getBinding() {
        return mBinding;
    }

    protected void bindViewDataBinding(int layoutResId) {
        mBinding = DataBindingUtil.setContentView(this, layoutResId());
        mBinding.executePendingBindings();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showProgress() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this, R.style.Progressbar);
                mProgressDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(android.graphics.Color.TRANSPARENT));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void dismissProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    public void showMessage(String message) {
//        if (message != null) {
//            hideKeyboard(this);
//            Snackbar.make(mBinding.getRoot(), message, BaseTransientBottomBar.LENGTH_LONG).show();
//        }
//    }

    public void showMessage(String message) {
        if (message != null) {
            hideKeyboard(this);

            Snackbar snackbar = Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            TextView snackTextView = (TextView) snackBarView.findViewById(
                    com.google.android.material.R.id.snackbar_text);
            snackTextView.setMaxLines(3);
            snackbar.show();
        }
    }

    public void addFragment(int containerId, Fragment fragment, boolean isBackStackEnabled){
        String fragmentTag = fragment.getClass().getName();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(isBackStackEnabled? fragmentTag : null);
        transaction.commit();
    }
}
