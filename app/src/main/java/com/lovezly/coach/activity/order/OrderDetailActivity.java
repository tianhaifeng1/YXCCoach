package com.lovezly.coach.activity.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.detail.DetailPresenter;
import com.lovezly.coach.activity.detail.DetailView;
import com.lovezly.coach.databinding.ActivityExaminationPayBinding;
import com.lovezly.coach.databinding.ActivityOrderDetailBinding;

public class OrderDetailActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityOrderDetailBinding> implements DetailView {


    @Override
    protected ActivityOrderDetailBinding getViewBinding() {
        return ActivityOrderDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        titleModule.initTitle("订单详情", true);
        titleModule.setTitleTextColor(com.example.module_common.R.color.color_white);
        titleModule.getTitleView().setVisibility(View.VISIBLE);
        titleModule.setTitleBackground(com.example.module_common.R.color.transparent);
        titleModule.setTitleBottomLineShow(false);
        titleModule.setBackImage(com.example.module_common.R.mipmap.ic_back_white);
    }
}