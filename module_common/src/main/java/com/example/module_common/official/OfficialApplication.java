package com.example.module_common.official;

import android.content.Context;

import com.example.module_common.base.InitApplication;
import com.example.module_common.rxhttp.LoggingInterceptor;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.ssl.HttpsUtils;

/**
 * 需要继承InitApplication
 */
public class OfficialApplication extends InitApplication {

    private static Context context;

    public static final int DEFAULT_TIMEOUT = 20;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        RxHttpPlugins.init(getOkHttpClientBuilder());
    }

    public static Context getContext() {
        return context;
    }

    public static OkHttpClient getOkHttpClientBuilder() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .hostnameVerifier(new AllowAllHostnameVerifier())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .retryOnConnectionFailure(true).build();
    }
}
