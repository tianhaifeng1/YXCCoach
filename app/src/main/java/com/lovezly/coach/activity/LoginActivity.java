package com.lovezly.coach.activity;

import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.databinding.ActivityLoginBinding;

public class LoginActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityLoginBinding> implements LoginView {

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

}