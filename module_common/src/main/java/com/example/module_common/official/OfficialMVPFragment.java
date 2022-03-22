package com.example.module_common.official;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.module_common.base.TView;
import com.example.module_common.mvp.BaseMVPFragment;
import com.example.module_common.mvp.TModel;
import com.example.module_common.mvp.TPresenter;

public abstract class OfficialMVPFragment<V extends TView, P extends TPresenter<V, TModel>>
        extends BaseMVPFragment<V, TModel, P> {

    public OfficialMVPFragment() {
    }

    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindingView(inflater, container);
        rootView = getLayoutId();
        initFragmentView(rootView);
        return rootView;
    }

    public abstract void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    public abstract View getLayoutId();

    @Override
    protected TModel initModel() {
        return new TModel();
    }


    //初始化View
    protected abstract void initFragmentView(View view);

    @Override
    public void tPostFail(int resultState) {
        super.tPostFail(resultState);
        switch (resultState) {

        }

    }

    protected boolean pdRefershPage() {
        return false;
    }

}
