package com.example.module_common.rxhttp;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * User: tian
 * Date: 2021/1/4
 * Time: 11:48
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        String method = request.method();
        if ("POST".equals(method)) {
            Buffer buffer = new Buffer();
            try {
                request.body().writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = request.body().contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                String params = buffer.readString(charset);
                Log.e("tian===",String.format("请求URL------%s%n请求参数------%s%n请求头------%s",
                        request.url(), params, request.headers()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.e("tian===",String.format("请求URL------%s%n请求头------%s",
                    request.url(), request.headers()));
        }

        long t1 = System.nanoTime();//请求发起的时间

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        if (!response.request().url().toString().contains("user.heartbeat"))
            Log.e("tian====",String.format("响应URL-------: %s %n响应数据------%s 请求用时--------%.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

        return response;
    }
}