package com.lovezly.coach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.module_common.official.OfficialMVPActivity;
import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.bean.DateBean;
import com.lovezly.coach.databinding.ActivityLoginBinding;
import com.lovezly.coach.databinding.ActivityWelcomeBinding;
import com.lovezly.coach.util.DemoConstant;
import com.lovezly.coach.util.SPUtils;

import java.util.Calendar;
import java.util.Date;

public class WelcomeActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityWelcomeBinding> implements LoginView {

    private Handler handler;

    @Override
    protected ActivityWelcomeBinding getViewBinding() {
        return ActivityWelcomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        DemoConstant.token = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_token, "");
        DemoConstant.userId = (int) SharedPreferencesUtils.getParam(context, DemoConstant.user_id, 0);
        DemoConstant.avatar = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_avatar, "");
        DemoConstant.nickname = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_nickname, "");
        DemoConstant.mobile = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_mobile, "");

        DateBean dateBean = SPUtils.getDateBean(this);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, -1);//至少每月登录一次
        Date date = now.getTime();

        handler = new Handler();

        handler.postDelayed(() -> {
            if (DemoConstant.token != null && !DemoConstant.token.equals("") && dateBean != null && !dateBean.getLastLoginTime().before(date)) {
                skipActivity(MainNavActivity.class);
            } else {
                skipActivity(LoginActivity.class);
            }
        }, 1000);
    }
}