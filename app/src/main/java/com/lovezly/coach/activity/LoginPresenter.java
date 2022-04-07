package com.lovezly.coach.activity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialPresenter;
import com.example.module_common.rxhttp.OnError;
import com.lovezly.coach.bean.UploadBean;
import com.lovezly.coach.bean.UserBean;

import java.io.File;

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
                .setAssemblyEnabled(false)   //设置是否添加公共参数/头部，默认为true
                .add("mobile", phoneNumber)
                .add("captcha", pwd)
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
    protected void getRegCode(String phoneNumber, String code) {
        showDialog("请求中···");
        RxHttp.postForm("api/sms/send")       //发送表单形式的post请求
                .add("mobile", phoneNumber)
                .add("event", code)
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

    /*** 上传图片 */
    @SuppressLint("CheckResult")
    public void getUpload(File file) {
        RxHttp.postForm("api/Common/upload")       //发送表单形式的post请求
                .addFile("file", file)
                .asResponse(UploadBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getUploadlSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }
    /*** 注册 */
    @SuppressLint("CheckResult")
    public void getRegister(String nickname, String mobile, String code, String front, String backend) {
        showDialog("请求中···");
        RxHttp.postForm("api/user/register")       //发送表单形式的post请求
                .add("nickname", nickname)
                .add("mobile", mobile)
                .add("code", code)
                .add("IDCard_front", front)
                .add("IDCard_backend", backend)
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    hideDialog();
                    getView().getRegisterSuccess();
                }, (OnError) error -> {
                    hideDialog();
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

}
