package com.lovezly.coach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;

import com.example.module_common.official.OfficialMVPActivity;
import com.example.module_common.rxhttp.Url;
import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.DateBean;
import com.lovezly.coach.bean.YhxyBean;
import com.lovezly.coach.databinding.ActivityWelcomeBinding;
import com.lovezly.coach.databinding.ActivityYhxyBinding;
import com.lovezly.coach.util.DemoConstant;
import com.lovezly.coach.util.SPUtils;

import java.util.Calendar;
import java.util.Date;

public class YhxyActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityYhxyBinding> implements LoginView {

    private int type;
    private String url;
    private WebSettings webSettings;

    @Override
    protected ActivityYhxyBinding getViewBinding() {
        return ActivityYhxyBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            titleModule.initTitle("用户协议", true);
            url = Url.baseUrl + "h5/xy.html";
        } else if (type == 2) {
            titleModule.initTitle("关于我们", true);
            url = Url.baseUrl + "h5/gy.html";
        }
        titleModule.setTitleBottomLineShow(false);

        webSettings = mBinding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        //是否可以前进
        mBinding.webview.canGoForward();
        //是否可以后退
        mBinding.webview.canGoBack();
        mBinding.webview.loadUrl(url);
    }

    private void initData() {
        if (type == 1) {
            getPresenter().getPolicy();
        } else if (type == 2) {
            getPresenter().getAbooutUs();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.webview.canGoBack()) {
                mBinding.webview.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}