package com.lovezly.coach.base;

import android.content.Context;

import com.example.module_common.base.InitApplication;
import com.example.module_common.rxhttp.LoggingInterceptor;
import com.lovezly.coach.R;
import com.lovezly.coach.util.DemoConstant;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.bugly.Bugly;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;
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

        RxHttpPlugins.init(getOkHttpClientBuilder())
                .setOnParamAssembly(new Function<Param<?>, Param<?>>() {  //设置公共参数/请求头回调
            @Override
            public Param<?> apply(Param<?> p) throws Exception {
                //主线程执行
                Method method = p.getMethod();
                if (method.isGet()) {     //可根据请求类型添加不同的参数
                } else if (method.isPost()) {
                }
                return p.addHeader("token", DemoConstant.token); //添加公共请求头
            }
        });

        Bugly.init(getApplicationContext(), "0aa4da964f", false);
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

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, com.example.module_common.R.color.color_name);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
