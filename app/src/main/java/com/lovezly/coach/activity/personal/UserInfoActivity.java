package com.lovezly.coach.activity.personal;

import android.view.View;

import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.activity.detail.DetailPresenter;
import com.lovezly.coach.activity.detail.DetailView;
import com.lovezly.coach.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityUserInfoBinding> implements DetailView {


    @Override
    protected ActivityUserInfoBinding getViewBinding() {
        return ActivityUserInfoBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        titleModule.initTitle("个人信息", true);
        titleModule.getTitleView().setVisibility(View.VISIBLE);
    }
}