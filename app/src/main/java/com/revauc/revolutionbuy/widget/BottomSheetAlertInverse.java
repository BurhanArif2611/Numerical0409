package com.revauc.revolutionbuy.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.BottomInverseAlertLayoutBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;


public class BottomSheetAlertInverse implements View.OnClickListener {
    private static final int SPINNER_COUNT = 3;
    private final int mRequestCode;

    private BottomInverseAlertLayoutBinding mBinding;
    private BottomSheetDialog mBottomSheetDialog;
    private Context mContext;
    private Calendar mCalendar = Calendar.getInstance();
    private static BottomSheetAlertInverse ourInstance;


    public static BottomSheetAlertInverse getInstance(final Context context,int requestCode, String message, String positiveText, String negativeText) {
        if (ourInstance == null) {
            synchronized (BottomSheetAlertInverse.class) {
                if (ourInstance == null) {
                    ourInstance = new BottomSheetAlertInverse(context, requestCode,message, positiveText, negativeText);
                }
            }
        }

        return ourInstance;
    }

    public void show() {
        if (mBottomSheetDialog != null && !mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.show();
        }
    }


    private BottomSheetAlertInverse(final Context context,int requestCode, String message, String positiveText, String negativeText) {
        mContext = context;
        mBottomSheetDialog = new BottomSheetDialog(mContext);
        mBottomSheetDialog.setCancelable(false);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_inverse_alert_layout, null, false);

        //Setting Values
        mRequestCode = requestCode;
        mBinding.textMessage.setText(message);
        mBinding.textPositive.setText(positiveText);
        mBinding.textNegative.setText(negativeText);

        mBottomSheetDialog.setContentView(mBinding.getRoot());
        mBottomSheetDialog.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        mBinding.textPositive.setOnClickListener(this);
        mBinding.textNegative.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_negative:
                if (mBottomSheetDialog != null) {
                    clearInstance();
                }
                break;

            case R.id.text_positive:
                EventBus.getDefault().post(new OnButtonClicked(mRequestCode));
                clearInstance();
                break;

            default:
                break;
        }
    }

    private void clearInstance() {
        mBottomSheetDialog.dismiss();
        ourInstance = null;
    }
}
