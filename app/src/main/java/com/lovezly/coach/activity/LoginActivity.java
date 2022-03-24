package com.lovezly.coach.activity;

import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.databinding.ActivityLoginBinding;

public class LoginActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityLoginBinding> implements LoginView {

    private int yxType = 1;

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        mBinding.loginXy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yxType == 1) {
                    yxType = 2;
                    mBinding.loginXy.setImageDrawable(getResources().getDrawable(R.drawable.login_no));
                } else {
                    yxType = 1;
                    mBinding.loginXy.setImageDrawable(getResources().getDrawable(R.drawable.login_yes));
                }
            }
        });
        mBinding.loginYhxy.setOnClickListener(view -> {
            ToastUtils.showShort("协议预留");
        });
        mBinding.loginYzm.setOnClickListener(view -> {
            ToastUtils.showShort("验证码预留");
        });
        mBinding.loginSubmit.setOnClickListener(view -> {
//            if (mBinding.loginEditPhone.getText().toString().trim().equals("")) {
//                ToastUtils.showShort("请输入手机号");
//            } else if (mBinding.loginEditYzm.getText().toString().trim().equals("")) {
//                ToastUtils.showShort("请输入验证码");
//            } else if (!RegexUtils.isMobileExact(mBinding.loginEditPhone.getText().toString().trim())) {
//                ToastUtils.showShort("请输入正确的手机号");
//            } else {
                skipActivity(MainNavActivity.class);
//            }
        });
        mBinding.loginRegister.setOnClickListener(view -> {
            skipActivity(RegisterActivity.class);
        });
    }
}