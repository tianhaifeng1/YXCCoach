package com.lovezly.coach.activity.order;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.fragment.FragmentPresenter;
import com.lovezly.coach.activity.fragment.FragmentView;
import com.lovezly.coach.activity.fragment.MainThreeFragment;
import com.lovezly.coach.bean.OrderListBean;
import com.lovezly.coach.databinding.FragmentOrderBinding;

public abstract class OrderInitFragment<Adapter extends BaseQuickAdapter> extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

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
        View view1 = LayoutInflater.from(activity).inflate(R.layout.default_view, null);
        adapter.setEmptyView(view1);

        adapter.setOnItemClickListener((adapter, view2, position) -> {
            OrderListBean.DataBean dataBean = (OrderListBean.DataBean) adapter.getData().get(position);
            Intent intent = new Intent(activity, OrderDetailActivity.class);
            intent.putExtra("id", dataBean.getId()+"");
            activity.skipActivity(intent);
        });


        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(activity.context));
        mBinding.recyclerview.setAdapter(adapter);

        //初始化数据
        int pos = 0;
        switch (threeFragment.index) {
            case 0:
                //全部
                pos = 99;
                break;
            case 1:
                pos = 1;
                break;
            case 2:
                pos = 0;
                break;
        }

        if (tabStatus == pos) {
            initData();
        }
    }

    @Override
    public void initData() {
        super.initData();
        String status = tabStatus + "";
        if (tabStatus == 99)
            status = "";
        getPresenter().getOrderList(status, threeFragment.mBinding.threeSearch.getText().toString().trim(), mPageNum);
    }

    protected abstract Adapter initAdapter();

    @Override
    public void getOrderListSuccess(OrderListBean bean) {
        if(bean!=null){
            if (mPageNum == 1) {
                threeFragment.mBinding.smartRefresh.finishRefresh();
                adapter.setNewData(bean.getData());
            } else {
                threeFragment.mBinding.smartRefresh.finishLoadMore();
                adapter.addData(bean.getData());
            }
        }
    }

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }
}