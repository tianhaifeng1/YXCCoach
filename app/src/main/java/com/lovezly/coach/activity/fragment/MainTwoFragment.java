package com.lovezly.coach.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.activity.adapter.MainAdapter;
import com.lovezly.coach.activity.detail.ExaminationDetailActivity;
import com.lovezly.coach.databinding.FragmentMainOneBinding;
import com.lovezly.coach.databinding.FragmentMainTwoBinding;
import com.lovezly.coach.util.DemoUtils;

public class MainTwoFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainTwoBinding mBinding;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainTwoBinding.inflate(inflater);
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
        mainAdapter.setOnItemClickListener((adapter, view, position) ->
                activity.skipActivity(ExaminationDetailActivity.class));
        mBinding.oneRv.setAdapter(mainAdapter);
    }
}
