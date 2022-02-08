package com.acentura.splashstart.util;

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
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

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

    private String title;
    private String message;
    private String animation;
    private String textButton1;
    private String textButton2;
    private DialogClickListener listener;
    private String type;

    public CustomDialog(Activity activity, String type, String title, String message,
                        String animation) {
        super(activity);
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.animation = animation;
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

        tvTitle.setText(title);
        tvMessage.setText(message);
        if(animation !=null && !animation.isEmpty()) {
            try {
                animationView.setVisibility(View.VISIBLE);
                animationView.setAnimation(animation);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(type == null || type.isEmpty()) type = DialogActionType.OK_CANCEL;

        switch (type){
            case DialogActionType.OK_CANCEL:
                tvPositiveButton.setText(DialogActionType.OK);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            case DialogActionType.RETRY_CANCEL:
                tvPositiveButton.setText(DialogActionType.RETRY);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            case DialogActionType.CONTINUE_CANCEL:
                tvPositiveButton.setText(DialogActionType.CONTINUE);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            case DialogActionType.CONFIRM_CANCEL:
                tvPositiveButton.setText(DialogActionType.CONFIRM);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            case DialogActionType.SELECT_CANCEL:
                tvPositiveButton.setText(DialogActionType.SELECT);
                tvNegativeButton.setText(DialogActionType.CANCEL);
                break;
            case DialogActionType.NO_ACTION:
                llButtons.setVisibility(View.GONE);
                horizontalDivider.setVisibility(View.GONE);
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
                    listener.onPositiveClicked(CustomDialog.this);
                break;
            case R.id.cvNegative:
                if(listener!=null)
                    listener.onNegativeClicked(CustomDialog.this);
                break;
            case R.id.btnOption3:
                dismiss();
                break;
            default:
                break;
        }

        if(DialogActionType.NO_ACTION.equals(type)){
           dismiss();
        }
    }

    public interface DialogClickListener {
        void onPositiveClicked(Dialog dialog);
        void onNegativeClicked(Dialog dialog);
    }
}