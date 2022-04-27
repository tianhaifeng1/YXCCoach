package com.lovezly.coach.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lovezly.coach.R;


/**
 * Created by Administrator on 2016/11/22.
 * @author longhy
 */

public class CommonDialog extends Dialog {

    TextView quxiao;
    TextView queding;

    public CommonDialog(Context context) {
        super(context, R.style.MyDialogStyleTop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(false);

        quxiao = findViewById(R.id.tv_quxiao);
        queding = findViewById(R.id.tv_queding);
        quxiao.setOnClickListener(view -> {
            onCancleClick();
        });

        queding.setOnClickListener(view -> {
            onOkClick();
        });
    }

    /**
     * 隐藏软键盘
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void onCancleClick() {
        dismiss();
    }

    public void onOkClick() {
        dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
