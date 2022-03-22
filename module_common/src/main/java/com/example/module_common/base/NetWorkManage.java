package com.example.module_common.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkManage{

    public NetWorkManage() {
    }

    private static NetWorkManage instance;

    public static NetWorkManage getInstance() {
        if (null == instance) {
            instance = new NetWorkManage();
        }
        return instance;
    }

    /**
     * 判断是否有手机网络连接
     *
     * @param context
     * @return 连接成功都返回true ,反之则返回false
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI连接是否可用
     *
     * @param context 上下文
     * @return 连接成功都返回true ,反之则返回false
     */
    public boolean isWifiConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 获取当前的网络连接是否可用 （1.任意一种网络连接成功都返回true 2.否则返回false）
     *
     * @param context 上下文
     * @return 任意一种网络连接成功都返回true ,反之则返回false
     */
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager
                .getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

}