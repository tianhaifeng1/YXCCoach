package com.lovezly.coach.activity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.base.NavBottomActivity;
import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.fragment.MainFourFragment;
import com.lovezly.coach.activity.fragment.MainOneFragment;
import com.lovezly.coach.activity.fragment.MainThreeFragment;
import com.lovezly.coach.activity.fragment.MainTwoFragment;
import com.lovezly.coach.util.DemoConstant;

public class MainNavActivity extends NavBottomActivity {


    @Override
    protected void initFragmentData() {
        fragmentList.add(new MainOneFragment());
        fragmentList.add(new MainTwoFragment());
        fragmentList.add(new MainThreeFragment());
        fragmentList.add(new MainFourFragment());

        DemoConstant.token = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_token, "");
        DemoConstant.userId = (int) SharedPreferencesUtils.getParam(context, DemoConstant.user_id, 0);
        DemoConstant.avatar = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_avatar, "");
        DemoConstant.nickname = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_nickname, "");
        DemoConstant.mobile = (String) SharedPreferencesUtils.getParam(context, DemoConstant.user_mobile, "");
    }

    @Override
    protected int initTabMenu() {
        return R.menu.navigation_main;
    }

    @Override
    protected boolean backBefore() {
        ToastUtils.showShort("再次点击退出系统");
        activityManager.exitApp(true);
        return false;
    }
}