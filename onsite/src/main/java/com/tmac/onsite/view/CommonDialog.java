package com.tmac.onsite.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tmac.onsite.R;

/**
 * Created by user on 16/10/13.
 */

public class CommonDialog extends Dialog {


    private static final boolean DBG = true;
    private static final String TAG = "LC-CommonDialog";
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private Context context;
    private int situation;
    private OnDialogListenerInterface clickListenerInterface;

    public interface OnDialogListenerInterface {

        void doConfirm(int situation);

    }

    public CommonDialog(Context context, String title, String confirmButtonText,
                        String cacelButtonText, int situation, OnDialogListenerInterface clickListenerInterface) {
        super(context, R.style.dialog);
        this.context = context;
        this.title = title;
        this.clickListenerInterface = clickListenerInterface;
        this.confirmButtonText = confirmButtonText;
        this.situation = situation;
        this.cacelButtonText = cacelButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.com_dialog, null);
        setContentView(view);


        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView tvConfirm = (TextView) view.findViewById(R.id.positive_btn);
        TextView tvCancel = (TextView) view.findViewById(R.id.negative_btn);

        tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);

        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);


    }


    class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.positive_btn:
                    clickListenerInterface.doConfirm(situation);
                    dismiss();
                    break;
                case R.id.negative_btn:
                    dismiss();
                    break;

            }

        }
    }

    public static void lightOn(Window window) {
        // TODO Auto-generated method stub
            if(DBG) Log.d(TAG, "lightOn");
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1.0f;
            window.setAttributes(lp);
    }


    public static void lightOff(Window window){
            if(DBG) Log.d(TAG, "lightOff");
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.5f;
            window.setAttributes(lp);
    }


    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}
