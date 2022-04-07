package com.lovezly.coach.activity.fragment;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialPresenter;
import com.example.module_common.rxhttp.OnError;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.OrderListBean;
import com.lovezly.coach.bean.UserInfoBean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;

public class FragmentPresenter extends OfficialPresenter<FragmentView> {

    public FragmentPresenter(@NonNull FragmentView view) {
        super(view);
    }

    /*** 首页 */
    @SuppressLint("CheckResult")
    public void getExamIndex(String date,String key,int page,int type) {
        RxHttp.postForm("api/exam/index")       //发送表单形式的post请求
                .add("book_date", date)
                .add("key", key)
                .add("page", page)
                .add("item_id", type)
                .asResponse(ExamIndexBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getExamIndexSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 首页 */
    @SuppressLint("CheckResult")
    public void getOrderList(String status,String key,int page) {
        RxHttp.postForm("api/exam/order_list")       //发送表单形式的post请求
                .add("pay_status", status)
                .add("key", key)
                .add("page", page)
                .asResponse(OrderListBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getOrderListSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 个人信息 */
    @SuppressLint("CheckResult")
    public void getOrdersAnalysis() {
        RxHttp.postForm("api/user/index")       //发送表单形式的post请求
                .asResponse(UserInfoBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getOrdersAnalysisSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }
}
