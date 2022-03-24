package com.lovezly.coach.activity;


import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.databinding.ActivityRegisterBinding;
import com.lovezly.coach.dialog.RegisterDialog;

public class RegisterActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityRegisterBinding> implements LoginView {

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        titleModule.initTitle("注册新用户", true);
        titleModule.getTitleView().setVisibility(View.VISIBLE);

        mBinding.registerYzm.setOnClickListener(view -> {
            ToastUtils.showShort("验证码预留");
        });

        mBinding.registerSubmit.setOnClickListener(view -> {
//            if (mBinding.registerEditPhone.getText().toString().trim().equals("")) {
//                ToastUtils.showShort("请输入手机号");
//            } else if (mBinding.registerEditYzm.getText().toString().trim().equals("")) {
//                ToastUtils.showShort("请输入验证码");
//            } else if (!RegexUtils.isMobileExact(mBinding.registerEditPhone.getText().toString().trim())) {
//                ToastUtils.showShort("请输入正确的手机号");
//            } else if (mBinding.registerEditName.getText().toString().trim().equals("")) {
//                ToastUtils.showShort("请输入您的姓名");
//            } else {
                new RegisterDialog(context) {
                    @Override
                    public void onOkClick() {
                        super.onOkClick();
                        finish();
                    }
                }.show();
//            }
        });
        mBinding.idCardJust.setOnClickListener(view -> {
            ToastUtils.showShort("正面");
        });
        mBinding.idCardBack.setOnClickListener(view -> {
            ToastUtils.showShort("背面");
        });
    }
}