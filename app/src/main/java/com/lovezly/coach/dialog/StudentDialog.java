package com.lovezly.coach.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lovezly.coach.R;

public class StudentDialog extends Dialog {


    EditText name;
    EditText idCard;
    EditText phone;
    TextView quxiao;
    TextView queding;

    public StudentDialog(Context context) {
        super(context, R.style.MyDialogStyleTop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_student);
        setCanceledOnTouchOutside(false);

        name = findViewById(R.id.et_name);
        idCard = findViewById(R.id.et_idcard);
        phone = findViewById(R.id.et_phone);
        quxiao = findViewById(R.id.tv_quxiao);
        queding = findViewById(R.id.tv_queding);

        quxiao.setOnClickListener(view -> {
            HideSoftInput(name.getWindowToken());
            HideSoftInput(idCard.getWindowToken());
            onCancleClick();
        });

        queding.setOnClickListener(view -> {
            HideSoftInput(name.getWindowToken());
            HideSoftInput(phone.getWindowToken());
            HideSoftInput(idCard.getWindowToken());
            if (name.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入姓名");
            } else if (!RegexUtils.isMobileSimple(phone.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的手机号");
            } else if (!RegexUtils.isIDCard18(idCard.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的身份证号码");
            } else {
                onOkClick(name.getText().toString().trim(),phone.getText().toString().trim(), idCard.getText().toString().trim());
            }
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

    public void onOkClick(String name, String phone, String idCard) {
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