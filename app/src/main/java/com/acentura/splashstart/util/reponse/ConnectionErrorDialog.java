package com.acentura.splashstart.util.reponse;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acentura.splashstart.R;
import com.acentura.splashstart.di.types.DialogActionType;
import com.acentura.splashstart.util.constant.AppConstants;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;

public class ConnectionErrorDialog extends Dialog implements View.OnClickListener {

    public static final int TYPE_NO_ACTION = 0;
    public static final int TYPE_INTERNET_NOT_CONNECTED = 1;


    private Activity activity;
    private TextView tvTitle;
    private TextView tvMessage;
    private LottieAnimationView animationView;
    private MaterialCardView cvPositive;
    private MaterialCardView cvNegative;
    private TextView tvPositiveButton;
    private TextView tvNegativeButton;
    private Button btnOption3;
    private LinearLayout llButtons;
    private View verticalDivider;
    private View horizontalDivider;

    private DialogClickListener listener;
    private int type;

    public ConnectionErrorDialog(Activity activity, int type) {
        super(activity);
        this.activity = activity;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog_v1);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cvPositive = (MaterialCardView) findViewById(R.id.cvPositive);
        cvNegative = (MaterialCardView) findViewById(R.id.cvNegative);
        btnOption3 = (Button) findViewById(R.id.btnOption3);

        tvPositiveButton = (TextView) findViewById(R.id.tvPositiveButton);
        tvNegativeButton = (TextView) findViewById(R.id.tvNegativeButton);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        animationView = (LottieAnimationView) findViewById(R.id.animationView);
        llButtons = (LinearLayout) findViewById(R.id.llButtons);
        verticalDivider = (View) findViewById(R.id.verticalDivider);
        horizontalDivider = (View) findViewById(R.id.horizontalDivider);

        tvTitle.setText(ResponseCode.CUSTOM_ERROR_NOT_CONNECTED.getType());
        tvMessage.setText("Make sure that Wi-Fi or mobile data is turned on that try again!");

        try {
            animationView.setVisibility(View.VISIBLE);
            animationView.setAnimation(AppConstants.AnimPath.NET_CONNECTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (type){
            case TYPE_INTERNET_NOT_CONNECTED:
                tvPositiveButton.setText(DialogActionType.RETRY);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            default:
                tvPositiveButton.setText(type);
                tvNegativeButton.setVisibility(View.GONE);
                verticalDivider.setVisibility(View.GONE);
                break;
        }



        cvPositive.setOnClickListener(this);
        cvNegative.setOnClickListener(this);
        btnOption3.setOnClickListener(this);

    }

    public void setDialogClickListener(DialogClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvPositive:
                if(listener!=null)
                    listener.onPositiveClicked(ConnectionErrorDialog.this);
                break;
            case R.id.cvNegative:
                if(listener!=null)
                    listener.onNegativeClicked(ConnectionErrorDialog.this);
                break;
            case R.id.btnOption3:
                dismiss();
                break;
            default:
                break;
        }

        if(TYPE_NO_ACTION == type){
           dismiss();
        }
    }

    public interface DialogClickListener {
        void onPositiveClicked(Dialog dialog);
        void onNegativeClicked(Dialog dialog);
    }
}