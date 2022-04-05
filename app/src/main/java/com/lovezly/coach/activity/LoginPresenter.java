package com.lovezly.coach.activity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialPresenter;
import com.example.module_common.rxhttp.OnError;
import com.lovezly.coach.bean.UserBean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;

public class LoginPresenter extends OfficialPresenter<LoginView> {

    public LoginPresenter(@NonNull LoginView view) {
        super(view);
    }

    /*** 登录 */
    @SuppressLint("CheckResult")
    public void getLogin(String phoneNumber, String pwd) {
        showDialog("请求中···");
        RxHttp.postForm("api/user/mobilelogin")       //发送表单形式的post请求
                .add("mobile", phoneNumber)
                .add("captcha", EncryptUtils.encryptMD5ToString(pwd))
                .add("type", "1")
                .asResponse(UserBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    hideDialog();
                    getView().getLoginSuccess(bean);
                }, (OnError) error -> {
                    hideDialog();
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 验证码 */
    @SuppressLint("CheckResult")
    protected void getRegCode(String phoneNumber) {
        showDialog("请求中···");
        RxHttp.postForm("api/sms/send")       //发送表单形式的post请求
                .add("mobile", phoneNumber)
                .add("event", "1")
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    hideDialog();
                    getView().eventDjs();
                }, (OnError) error -> {
                    hideDialog();
                    ToastUtils.showShort(error.getErrorMsg());
                });

    }
}
