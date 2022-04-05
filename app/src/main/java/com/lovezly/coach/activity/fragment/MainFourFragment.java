package com.lovezly.coach.activity.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.activity.MainNavActivity;
import com.lovezly.coach.activity.personal.UserInfoActivity;
import com.lovezly.coach.databinding.FragmentMainFourBinding;
import com.lovezly.coach.databinding.FragmentMainOneBinding;

public class MainFourFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainFourBinding mBinding;

    private MainNavActivity mainNavActivity;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainFourBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {
        mBinding.fourHeadRel.setOnClickListener(view1 -> activity.skipActivity(UserInfoActivity.class));

        mainNavActivity = (MainNavActivity) getActivity();

        mBinding.fourLin1.setOnClickListener(view1 -> mainNavActivity.skipTab(2));
        mBinding.fourLin2.setOnClickListener(view1 -> mainNavActivity.skipTab(2));
    }
}
