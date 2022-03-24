package com.lovezly.coach.activity.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.databinding.FragmentMainOneBinding;
import com.lovezly.coach.databinding.FragmentMainThreeeBinding;

public class MainThreeFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainThreeeBinding mBinding;

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
    }

    @Override
    public void initData() {
        Log.d("111","333");
        super.initData();
    }
}
