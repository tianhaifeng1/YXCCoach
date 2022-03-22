package com.example.module_common.official;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.CallSuper;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.LogUtils;
import com.example.module_common.base.TView;
import com.example.module_common.mvp.BaseMVPActivity;
import com.example.module_common.mvp.TModel;
import com.example.module_common.mvp.TPresenter;
import com.example.module_common.titlemodule.TitleListenter;
import com.example.module_common.titlemodule.TitleModule;


public abstract class OfficialMVPActivity<V extends TView, P extends TPresenter<V, TModel>, B extends ViewBinding>
        extends BaseMVPActivity<V, TModel, P> implements TitleListenter {

    public TitleModule titleModule;
    protected B mBinding;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意！！！在setContentView之前！！！
        mBinding = getViewBinding();
        setContentView(mBinding.getRoot());
        LogUtils.d("-------------init-------------");
        initWork();
        initView();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void initView() {
        titleModule = new TitleModule(context, rootView);
        titleModule.setListenter(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

//        if(!NetworkUtils.isConnected()){
//            ToastUtils.make().setGravity(Gravity.CENTER, 0, 0).show("当前无网络");
//        }else if(!NetworkUtils.getWifiEnabled()){
//            ToastUtils.make().setGravity(Gravity.CENTER, 0, 0).show("当前为4G/5G网络，请注意流量使用");
//        }
    }

    protected abstract B getViewBinding();

    @Override
    protected TModel initModel() {
        return new TModel();
    }

    @Override
    public void tPostFail(int resultState) {
        super.tPostFail(resultState);
        switch (resultState) {

        }
    }

    /**
     * 获取点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏
     */
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打电话
     */
    public void callPhone(String phoneNum) {
        //我们需要告诉系统，我们的动作：我要打电话
        //创建意图对象
        Intent intent = new Intent();
        //  直接打电话的动作
        //intent.setAction(Intent.ACTION_CALL);
        //  跳转到拨号界面
        intent.setAction(Intent.ACTION_DIAL);
        //设置打给谁
        intent.setData(Uri.parse("tel:" + phoneNum));//这个tel：必须要加上，表示我要打电话。否则不会有打电话功能，由于在打电话清单文件里设置了这个“协议”
        //把动作告诉系统,启动系统打电话功能。
        startActivity(intent);
    }
}
