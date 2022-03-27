package com.lovezly.coach.activity.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.activity.fragment.FragmentPresenter;
import com.lovezly.coach.activity.fragment.FragmentView;
import com.lovezly.coach.activity.fragment.MainThreeFragment;
import com.lovezly.coach.databinding.FragmentMainThreeeBinding;
import com.lovezly.coach.databinding.FragmentOrderBinding;

import java.util.List;

public abstract class OrderInitFragment<Adapter extends BaseQuickAdapter>  extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    protected Adapter adapter;

    private MainThreeFragment threeFragment;

    private FragmentOrderBinding mBinding;

    private int tabStatus;

    public OrderInitFragment(int tabStatus) {
        this.tabStatus = tabStatus;
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentOrderBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {
        threeFragment = (MainThreeFragment) getParentFragment();

        adapter = initAdapter();


        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(activity.context));
        mBinding.recyclerview.setAdapter(adapter);

        //初始化数据
        int pos = 0;
        switch (threeFragment.index) {
            case 0:
                //全部
                pos = 0;
                break;
            case 1:
                pos = 2;
                break;
            case 2:
                pos = 3;
                break;
            case 3:
                pos = 4;
                break;
        }

        if (tabStatus == pos) {
            initData();
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    protected abstract Adapter initAdapter();

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }
}