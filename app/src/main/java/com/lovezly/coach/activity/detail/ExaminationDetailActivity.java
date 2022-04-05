package com.lovezly.coach.activity.detail;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.activity.adapter.DetailPhotoAdapter;
import com.lovezly.coach.activity.adapter.DetailTestAdapter;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.databinding.ActivityExaminationDetailBinding;
import com.lovezly.coach.util.DemoUtils;

public class ExaminationDetailActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityExaminationDetailBinding> implements DetailView {

    private int id;

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

        id = getIntent().getIntExtra("id",0);

        initAdapter();
        initData();
    }

    private void initData() {
        if(id > 0){
            getPresenter().getExamDetail(id);
        }else{
            ToastUtils.showShort("数据错误，请返回重试");
        }
    }

    private DetailPhotoAdapter itemImageAdapter;
    private DetailTestAdapter detailTestAdapter;

    private void initAdapter() {
        GridLayoutManager ms = new GridLayoutManager(context, 4);
        mBinding.detailEnvironmentRv.setLayoutManager(ms);
        itemImageAdapter = new DetailPhotoAdapter(null);
        itemImageAdapter.setOnItemClickListener((adapter, view, position) -> {
            skipActivity(ExaminationPayActivity.class);
        });
        mBinding.detailEnvironmentRv.setAdapter(itemImageAdapter);

        mBinding.detailTestRv.setLayoutManager(new LinearLayoutManager(context));
        detailTestAdapter = new DetailTestAdapter(null);
        detailTestAdapter.setOnItemClickListener((adapter, view, position) -> {
            ExamDetailBean.ItemsBean itemsBean = (ExamDetailBean.ItemsBean) adapter.getData().get(position);
            Intent intent = new Intent(context, ExaminationPayActivity.class);
            intent.putExtra("bean", itemsBean);
            intent.putExtra("item_name", examDetailBean.getItem_name());
            skipActivity(intent);
        });
        mBinding.detailTestRv.setAdapter(detailTestAdapter);
    }

    private ExamDetailBean examDetailBean;

    @Override
    public void getExamDetailSuccess(ExamDetailBean bean) {
        if(bean!=null){
            examDetailBean = bean;
            DemoUtils.bindImageViewC(context,bean.getImage(),5,mBinding.detailImage);
            mBinding.detailName.setText(bean.getName());
            mBinding.detailAddress.setText("地址："+bean.getAddress());
            detailTestAdapter.setNewData(bean.getItems());
            itemImageAdapter.setNewData(bean.getImages());
        }else{
            ToastUtils.showShort("暂无考场数据");
        }
    }
}