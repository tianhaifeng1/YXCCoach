package com.lovezly.coach.activity.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.activity.adapter.MainAdapter;
import com.lovezly.coach.databinding.FragmentMainOneBinding;
import com.lovezly.coach.util.DemoUtils;

public class MainOneFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainOneBinding mBinding;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainOneBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {

        initAdapter();
    }

    private MainAdapter mainAdapter;

    private void initAdapter() {
        mBinding.oneRv.setLayoutManager(new LinearLayoutManager(activity));
        mainAdapter = new MainAdapter(DemoUtils.getList());
        mBinding.oneRv.setAdapter(mainAdapter);
    }

    @Override
    public void initData() {
        super.initData();
    }
}
