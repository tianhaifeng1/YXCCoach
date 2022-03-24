package com.lovezly.coach.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lovezly.coach.R;

/**
 * Created by Administrator on 2016/11/22.
 * @author longhy
 */

public class RegisterDialog extends Dialog {


    TextView tvSubmit;

    public RegisterDialog(Context context) {
        super(context, R.style.MyDialogStyleTop);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_dialog);
        setCanceledOnTouchOutside(false);

        tvSubmit = findViewById(R.id.register_dialog_submit);
        tvSubmit.setOnClickListener(view -> {
            onOkClick();
        });
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
