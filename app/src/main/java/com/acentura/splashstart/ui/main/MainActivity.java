package com.acentura.splashstart.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;

import com.acentura.splashstart.R;
import com.acentura.splashstart.base.BaseBindingActivity;
import com.acentura.splashstart.databinding.ActivityMainBinding;
import com.acentura.splashstart.di.module.viewmodelfactory.ViewModelFactory;
import com.acentura.splashstart.di.types.DialogActionType;
import com.acentura.splashstart.ui.main.fragments.MainFragment;
import com.acentura.splashstart.util.CustomDialog;
import com.acentura.splashstart.util.ResponseHandler;
import com.acentura.splashstart.util.constant.AppConstants;
import com.google.gson.Gson;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainViewModel> {

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel bindViewModel() {
        return new ViewModelProvider(this, viewModelFactory)
                .get(MainViewModel.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().tvText.setText("BatMan");

        showProgress();
        observeViewModel();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgress();

                getViewModel().getPosts();
                getBinding().tvText.setText(getViewModel().getData());
            }
        },3000);

        addFragment(R.id.container, new MainFragment(), false);
    }

    private void observeViewModel() {
        getViewModel().getResponseLiveData().observe(this, response -> {
            dismissProgress();
            if (response == null) return;

            switch (response.status) {
                case LOADING:
                    showProgress();
                    break;
                case SUCCESS:
                    if (response.data != null) {
                        Timber.v(" Data %s", new Gson().toJson(response.data));
                        showMessage(response.data.get(0).toString());
                    }
                    else{
                        Timber.v(" Data  NULL");
                    }
                    break;
                case ERROR:
                    showMessage(response.message);
                    Timber.d("Data ERROR\n %s", new Gson().toJson(response));
                    break;
                case NOT_CONNECTED:
                    CustomDialog dialog =new CustomDialog(MainActivity.this,
                            DialogActionType.RETRY_CANCEL,
                            ResponseHandler.StatusCode.CUSTOM_ERROR_NOT_CONNECTED.getType(),
                            response.message,
                           "lottie/hub_connecting.json" );
                    dialog.setDialogClickListener(new CustomDialog.DialogClickListener() {
                        @Override
                        public void onPositiveClicked(Dialog dialog) {

                        }

                        @Override
                        public void onNegativeClicked(Dialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    Timber.d("Data ERROR\n NO INTERNET");
                    break;
            }
        });
    }
}