package com.lovezly.coach.activity.detail;

import androidx.recyclerview.widget.GridLayoutManager;

import android.view.View;

import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.activity.adapter.DetailPhotoAdapter;
import com.lovezly.coach.databinding.ActivityExaminationDetailBinding;
import com.lovezly.coach.util.DemoUtils;

public class ExaminationDetailActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityExaminationDetailBinding> implements DetailView {


    @Override
    protected ActivityExaminationDetailBinding getViewBinding() {
        return ActivityExaminationDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        titleModule.initTitle("考场详情", true);
        titleModule.getTitleView().setVisibility(View.VISIBLE);
        titleModule.setTitleBottomLineShow(false);

        initAdapter();
    }

    private DetailPhotoAdapter itemImageAdapter;

    private void initAdapter() {
        GridLayoutManager ms = new GridLayoutManager(context, 4);
        mBinding.detailEnvironmentRv.setLayoutManager(ms);
        itemImageAdapter = new DetailPhotoAdapter(DemoUtils.getList());
        itemImageAdapter.setOnItemClickListener((adapter, view, position) -> {
            skipActivity(ExaminationPayActivity.class);
        });
        mBinding.detailEnvironmentRv.setAdapter(itemImageAdapter);
    }
}