package com.lovezly.coach.activity.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.module_common.adapter.TViewPagerStateAdapter;
import com.example.module_common.fragment.TFragment;
import com.example.module_common.official.OfficialMVPFragment;
import com.google.android.material.tabs.TabLayout;
import com.lovezly.coach.activity.order.OrderAllFragment;
import com.lovezly.coach.databinding.FragmentMainOneBinding;
import com.lovezly.coach.databinding.FragmentMainThreeeBinding;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;

import java.util.ArrayList;
import java.util.List;

public class MainThreeFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainThreeeBinding mBinding;

    private List<TFragment> fragments;

    public int index = 0;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainThreeeBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {
//        mBinding.smartRefresh.setOnMultiListener(new OnMultiListener() {
//            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onHeaderFinish(RefreshHeader header, boolean success) {
//
//            }
//
//            @Override
//            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {
//
//            }
//
//            @Override
//            public void onFooterFinish(RefreshFooter footer, boolean success) {
//
//            }
//
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if(fragments!=null){
//                    fragments.get(index).mPageNum++;
//                    fragments.get(index).initData();
//                }
////                refreshLayout.finishLoadMore();//结束加载
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                if(fragments!=null){
//                    fragments.get(index).mPageNum = 1;
//                    fragments.get(index).initData();
//                }
////                refreshLayout.finishRefresh();//结束刷新
//            }
//
//            @Override
//            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//
//            }
//        });

        mBinding.viewpager.setOffscreenPageLimit(4);
        initFragmentViewData();
    }

    private void initFragmentViewData(){
        List<String> stringList = getTabTitle();
        for (int i = 0; i < stringList.size(); i++) {
            mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText(stringList.get(i)));
        }
        fragments = new ArrayList<>();
        OrderAllFragment allFragment = new OrderAllFragment(0);
        fragments.add(allFragment);
        OrderAllFragment allFragment1 = new OrderAllFragment(1);
        fragments.add(allFragment1);
        OrderAllFragment allFragment2 = new OrderAllFragment(2);
        fragments.add(allFragment2);


        TViewPagerStateAdapter<TFragment> adapter = new TViewPagerStateAdapter(getChildFragmentManager(), fragments, stringList);
        mBinding.viewpager.setAdapter(adapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpager);
        mBinding.viewpager.setCurrentItem(index,true);
        mBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tian===","=============tab : " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(index !=position){
                    index = position;
                    fragments.get(index).initData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<String> getTabTitle() {
        List<String> stringList = new ArrayList<>();
        stringList.add("全部");
        stringList.add("已支付");
        stringList.add("待支付");
        return stringList;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
