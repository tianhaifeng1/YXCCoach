package com.example.module_common.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.module_common.R;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }

    /**
     * 设置Buggly 的 appid
     *
     * @param bugglyAppid
     */
    public void setBugglyAppid(String bugglyAppid) {
//        Bugly.init(this, bugglyAppid, false);
    }

    protected void initAppParameter(){
        //sharePreferences 的参数
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}