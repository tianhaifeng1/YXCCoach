package com.lovezly.coach.activity.order;

import static com.tencent.mm.opensdk.diffdev.a.d.c;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.adapter.OrderDetailAdapter;
import com.lovezly.coach.activity.adapter.StudentAdapter;
import com.lovezly.coach.activity.detail.DetailPresenter;
import com.lovezly.coach.activity.detail.DetailView;
import com.lovezly.coach.assist.RecycleViewDivider;
import com.lovezly.coach.assist.ReturnPayResult;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PaymentWeChat;
import com.lovezly.coach.databinding.ActivityOrderDetailBinding;
import com.lovezly.coach.util.AuthResult;
import com.lovezly.coach.util.CommonPopupWindow;
import com.lovezly.coach.util.DemoUtils;
import com.lovezly.coach.util.PayResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import rxhttp.wrapper.utils.GsonUtil;

public class OrderDetailActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityOrderDetailBinding> implements DetailView {

    private String id = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected ActivityOrderDetailBinding getViewBinding() {
        return ActivityOrderDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        titleModule.initTitle("????????????", true);
        titleModule.setTitleTextColor(com.example.module_common.R.color.color_white);
        titleModule.getTitleView().setVisibility(View.VISIBLE);
        titleModule.setTitleBackground(R.color.price_1);
        titleModule.setTitleBottomLineShow(false);
        titleModule.setBackImage(com.example.module_common.R.mipmap.ic_back_white);

        mBinding.orderDetailQuxiao.setOnClickListener(view -> {
            getPresenter().getOrderCancel(id);
        });

        mBinding.orderDetailZhifu.setOnClickListener(view -> {
            getPoppurch();
        });

        id = getIntent().getStringExtra("id");
        initData();
        initAdapter();
    }


    private OrderDetailAdapter orderDetailAdapter;

    private void initAdapter() {
        mBinding.orderDetailRv.setLayoutManager(new LinearLayoutManager(context));
        orderDetailAdapter = new OrderDetailAdapter(null);
        mBinding.orderDetailRv.setAdapter(orderDetailAdapter);
    }

    private void initData() {
        if (!id.equals("")) {
            getPresenter().getOrderDetail(id);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(ReturnPayResult result){
        if(result.getStatus().equals("WXPayEntryActivity")){
            initData();
        }
    }

    private OrderDetailBean orderDetailBean;

    @Override
    public void getOrderDetailSuccess(OrderDetailBean bean) {
        if (bean != null) {
            orderDetailBean = bean;
            if (bean.getPay_status() == 1) {
                mBinding.orderDetailLin1.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_1));
            } else if (bean.getPay_status() == 0) {
                mBinding.orderDetailLin0.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_0));
            } else if (bean.getPay_status() == -1) {
                mBinding.orderDetailLin2.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_1));
            }

            mBinding.orderDetailName.setText(bean.getName());
            mBinding.orderDetailTime.setText(bean.getBook_date());
            mBinding.orderDetailNumber.setText(bean.getOrder_sn());
            mBinding.orderDetailPaytime.setText(bean.getPaytime().equals("0") ? "???" :DemoUtils.timeStamp2Date(Long.parseLong(bean.getPaytime()),null));
            mBinding.orderDetailPrice.setText(DemoUtils.changTVsize("???" + DemoUtils.ToDouble(bean.getTotal_price())));
            orderDetailAdapter.setNewData(bean.getStudent());
        }
    }

    @Override
    public void getOrderCancelSuccess() {
        ToastUtils.showShort("????????????");
        finish();
    }

    private CommonPopupWindow popupWindow;
    private int payType = 1;

    private void getPoppurch() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(context).inflate(R.layout.pop_confirm, null);
        payType = 1;
        ImageView imageView = upView.findViewById(R.id.popw_back);
        imageView.setOnClickListener(view -> {
            popupWindow.dismiss();
        });
        ImageView imageView1 = upView.findViewById(R.id.pop_confirm_wx);
        ImageView imageView2 = upView.findViewById(R.id.pop_confirm_zfb);
        RelativeLayout relativeLayout1 = upView.findViewById(R.id.pop_confirm_wx_rel);
        RelativeLayout relativeLayout2 = upView.findViewById(R.id.pop_confirm_zfb_rel);
        relativeLayout1.setOnClickListener(view -> {
            payType = 1;
            imageView1.setImageResource(R.drawable.login_yes);
            imageView2.setImageResource(R.drawable.login_no);
        });
        relativeLayout2.setOnClickListener(view -> {
            payType = 2;
            imageView1.setImageResource(R.drawable.login_no);
            imageView2.setImageResource(R.drawable.login_yes);
        });

        TextView popwSubmit = upView.findViewById(R.id.popw_submit);
        popwSubmit.setOnClickListener(view -> {
            if (payType == 1) {  //??????
                getPresenter().getPaymentWeChat(orderDetailBean.getOrder_sn(), "weixin");
            } else if (payType == 2) {
                getPresenter().getPaymentWeChat(orderDetailBean.getOrder_sn(), "alipay");
            }
            popupWindow.dismiss();
        });

        TextView popPrice = upView.findViewById(R.id.pop_price);
        TextView popNum = upView.findViewById(R.id.pop_people);

        popNum.setText("???" + orderDetailBean.getStudent().size() + "???");
        popPrice.setText(DemoUtils.changTVsize("???" + DemoUtils.ToDouble(orderDetailBean.getPrice() * orderDetailBean.getStudent().size())));
        popwSubmit.setText("????????????  ???" + DemoUtils.ToDouble(orderDetailBean.getPrice() * orderDetailBean.getStudent().size()));


        //??????View?????????
        DemoUtils.measureWidthAndHeight(upView);

        popupWindow = new CommonPopupWindow.Builder(context)
                .setView(upView)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                // .setBackGroundLevel(0.5f)//????????????0.0f-1.0f ???????????????   ??????????????? ????????????
                .setAnimationStyle(R.style.MyPopupWindow_anim_style)
                .create();
        // ??????????????????????????????????????????  ???????????? ????????????????????????
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.2f;
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????popUpWindow??????getWindow()?????????
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getPaymentWeChatSuccess(String s) {
        if (payType == 1) {
            PaymentWeChat wechetPayInfo = GsonUtil.getObject(s, PaymentWeChat.class);
            getPresenter().getWxPayInfo(context, wechetPayInfo);
        } else if (payType == 2) {
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(OrderDetailActivity.this);
                    Map<String, String> result = alipay.payV2("\n" +
                            "app_id=2021003127652265&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&return_url=http%3A%2F%2Fjl.xazbwl.com%2Fapi%2Fpayment%2Freturn%2Ftype%2Falipay%2Fout_trade_no%2F&notify_url=http%3A%2F%2Fjl.xazbwl.com%2Fapi%2Fpayment%2Fnotify%2Ftype%2Falipay&timestamp=2022-04-27+11%3A38%3A16&biz_content=%7B%22out_trade_no%22%3A%222022042680406%22%2C%22total_amount%22%3A0.02%2C%22subject%22%3A%22test%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&method=alipay.trade.app.pay&sign=6bPBfmpDqIOWdsOgNClsc4AKXFzbOOWaWsDtMf9euRoR5ii0l%2FydVZdQLnyMcZtHqdM2CZ2Ew%2BRY9njWvoJ0%2FNlGL6Up7h6sUEif42oU2qf%2FUVAREBu1%2F4cysuJ7q3FyVDa0LE9I5ib%2BKNOpwMPnHBI0jK0M4R%2FYdWqz%2FxRjk24YhwB5k%2FlqE40puhxICgBidh1uSu8aWc8ripxv%2Bi28hrJ2JqqYuZfGHfEshhttfyf8xQV%2FA9mP%2FSyPQawk4T9Z1QjiUFi7REstbQqOt2i%2FRuVcJkifp%2Frtpz1%2FVbNErjWpsjgE5Kla3tOxpGGOoysIA4jYkW7jBuc68VoL58ID7A%3D%3D", true);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // ??????????????????
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    initData();
                    /**
                     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        ToastUtils.showShort("????????????");
                    } else {
                        // ???????????????????????????????????????????????????????????????????????????
                        ToastUtils.showShort("????????????");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    initData();
                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
                        showAlert(OrderDetailActivity.this, "??????===" + authResult);
                    } else {
                        // ?????????????????????????????????
                        showAlert(OrderDetailActivity.this, "??????===" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}