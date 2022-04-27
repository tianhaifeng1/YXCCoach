package com.lovezly.coach.activity.detail;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialPresenter;
import com.example.module_common.rxhttp.OnError;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PayResultBean;
import com.lovezly.coach.bean.PaymentWeChat;
import com.lovezly.coach.bean.UploadBean;
import com.lovezly.coach.util.DemoConstant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;

public class DetailPresenter extends OfficialPresenter<DetailView> {

    public DetailPresenter(@NonNull DetailView view) {
        super(view);
    }

    /*** 详情 */
    @SuppressLint("CheckResult")
    public void getExamDetail(int id) {
        RxHttp.postForm("api/exam/detail")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(ExamDetailBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getExamDetailSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 添加学生 */
    @SuppressLint("CheckResult")
    public void getAddStudent(int id, String name, String mobile, String cardNum) {
        RxHttp.postForm("api/exam/addStudent")       //发送表单形式的post请求
                .add("id", id)
                .add("name", name)
                .add("mobile", mobile)
                .add("cardNum", cardNum)
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().getAddStudentSuccess();
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 删除学生 */
    @SuppressLint("CheckResult")
    public void getDelStudent(int id) {
        RxHttp.postForm("api/exam/delStudent")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().getDelStudentSuccess();
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 详情 */
    @SuppressLint("CheckResult")
    public void getStudents(int id) {
        RxHttp.postForm("api/exam/students")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(PayBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getStudentsSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 删除学生 */
    @SuppressLint("CheckResult")
    public void getOrder(int id) {
        RxHttp.postForm("api/exam/order")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(PayResultBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getOrderSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort("支付调起失败，请重试");
                });
    }

    /*** 订单详情 */
    @SuppressLint("CheckResult")
    public void getOrderDetail(String id) {
        RxHttp.postForm("api/exam/orderDetail")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(OrderDetailBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    getView().getOrderDetailSuccess(bean);
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 订单取消 */
    @SuppressLint("CheckResult")
    public void getOrderCancel(String id) {
        RxHttp.postForm("api/exam/cancel")       //发送表单形式的post请求
                .add("id", id)
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().getOrderCancelSuccess();
                }, (OnError) error -> {
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

    /*** 修改个人信息 */
    @SuppressLint("CheckResult")
    public void getProfile(String nickname, String avatar) {
        RxHttp.postForm("api/user/profile")       //发送表单形式的post请求
                .add("nickname", nickname)
                .add("avatar", avatar)
                .asResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    getView().getProfileSuccess();
                }, (OnError) error -> {
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 订单id支付 */
    @SuppressLint("CheckResult")
    public void getPaymentById(String Id, String type) {
        showDialog("请求中···");
        RxHttp.postForm("api/payment/index")       //发送表单形式的post请求
                .add("type", type)
                .add("order_id", Id)
                .asClass(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    hideDialog();
                    getView().getPaymentWeChatSuccess(s);
                }, (OnError) error -> {
                    getView().getPaymentWeChatError();
                    hideDialog();
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    /*** 订单码支付 */
    @SuppressLint("CheckResult")
    public void getPaymentWeChat(String orderSn, String type) {
        showDialog("请求中···");
        RxHttp.postForm("api/payment/index")       //发送表单形式的post请求
                .add("type", type)
                .add("order_sn", orderSn)
                .asClass(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    hideDialog();
                    getView().getPaymentWeChatSuccess(s);
                }, (OnError) error -> {
                    getView().getPaymentWeChatError();
                    hideDialog();
                    ToastUtils.showShort(error.getErrorMsg());
                });
    }

    private IWXAPI wxAPI;
    public void registerWxApp(Context context){
        wxAPI = WXAPIFactory.createWXAPI(context, DemoConstant.wx_app_id, true);
        wxAPI.registerApp(DemoConstant.wx_app_id);
    }

    public void unRegisterWxApp(){
        if (wxAPI != null) {
            wxAPI.unregisterApp();
            wxAPI = null;
        }
    }

    public void getWxPayInfo(Context context, PaymentWeChat infoBean) {
        registerWxApp(context);
        //这里注意要放在子线程
//        Runnable payRunnable = () -> {
        if (wxAPI == null) {
            return;
        }
        if (!wxAPI.isWXAppInstalled()) {
            ToastUtils.showShort("您还未安装微信客户端");
            return;
        }
        PayReq request = new PayReq(); //调起微信APP的对象
        //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
        request.appId = infoBean.getAppid();
        request.partnerId = infoBean.getPartnerid();
        request.prepayId = infoBean.getPrepayid();
        request.packageValue = infoBean.getPackageX();
        request.nonceStr = infoBean.getNoncestr();
        request.timeStamp = infoBean.getTimestamp();
        request.sign = infoBean.getSign();
        wxAPI.sendReq(request);//发送调起微信的请求
    }
}
