package com.lovezly.coach.activity.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.adapter.MainAdapter;
import com.lovezly.coach.activity.adapter.StudentAdapter;
import com.lovezly.coach.activity.order.OrderDetailActivity;
import com.lovezly.coach.assist.RecycleViewDivider;
import com.lovezly.coach.assist.ReturnPayResult;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PayResultBean;
import com.lovezly.coach.bean.PaymentWeChat;
import com.lovezly.coach.databinding.ActivityExaminationPayBinding;
import com.lovezly.coach.dialog.RegisterDialog;
import com.lovezly.coach.dialog.StudentDialog;
import com.lovezly.coach.util.AuthResult;
import com.lovezly.coach.util.CommonPopupWindow;
import com.lovezly.coach.util.DemoConstant;
import com.lovezly.coach.util.DemoUtils;
import com.lovezly.coach.util.PayResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import rxhttp.wrapper.utils.GsonUtil;

public class ExaminationPayActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityExaminationPayBinding> implements DetailView {

    private ExamDetailBean.ItemsBean itemsBean;
    private String itemName;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected ActivityExaminationPayBinding getViewBinding() {
        return ActivityExaminationPayBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);

        itemsBean = (ExamDetailBean.ItemsBean) getIntent().getSerializableExtra("bean");
        itemName = getIntent().getStringExtra("item_name");

        if (itemsBean != null) {
            mBinding.orderDetailTitle.setText(itemName);
            mBinding.orderDetailName.setText(itemsBean.getName());
            mBinding.orderDetailDate.setText("???????????????" + itemsBean.getBook_date());
        }

        mBinding.titleBack.setOnClickListener(view -> {
            finish();
        });

        mBinding.studentAdd.setOnClickListener(view -> {
            new StudentDialog(context) {
                @Override
                public void onOkClick(String name, String phone, String idCard) {
                    super.onOkClick(name, phone, idCard);
                    getPresenter().getAddStudent(itemsBean.getId(), name, phone, idCard);
                }
            }.show();
        });

        mBinding.orderDetailSubmit.setOnClickListener(view -> {
            if (studentAdapter.getData() != null && studentAdapter.getData().size() > 0) {
                getPresenter().getOrder(itemsBean.getId());
            } else {
                ToastUtils.showShort("??????????????????");
            }
        });

        initData();
        initAdapter();
    }

    private StudentAdapter studentAdapter;

    private void initAdapter() {
        mBinding.detailEnvironmentRv.setLayoutManager(new LinearLayoutManager(context));
        mBinding.detailEnvironmentRv.addItemDecoration(new RecycleViewDivider(
                context, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(com.example.module_common.R.color.color_whitesmoke)));
        studentAdapter = new StudentAdapter(null);
        studentAdapter.addChildClickViewIds(R.id.item_student_jian);
        studentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PayBean.DataBean dataBean = (PayBean.DataBean) adapter.getData().get(position);
            int ids = view.getId();
            if (ids == R.id.item_student_jian) {
                getPresenter().getDelStudent(dataBean.getId());
            }
        });
        mBinding.detailEnvironmentRv.setAdapter(studentAdapter);
    }

    private void initData() {
        if (itemsBean != null) {
            getPresenter().getStudents(itemsBean.getId());
        }
    }

    @Override
    public void getStudentsSuccess(PayBean bean) {
        if (bean != null) {
            studentAdapter.setNewData(bean.getData());
            mBinding.orderDetailRvnum.setText("???" + studentAdapter.getData().size() + "???");
            mBinding.orderDetailPrice.setText(DemoUtils.changTVsize(DemoUtils.ToDouble(itemsBean.getPrice() * studentAdapter.getData().size())));
        }
    }

    @Override
    public void getAddStudentSuccess() {
        initData();
    }

    @Override
    public void getDelStudentSuccess() {
        initData();
    }

    private CommonPopupWindow popupWindow;
    private int payType = 1;

    private void getPoppurch() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(context).inflate(R.layout.pop_confirm, null);
        payType = 1;
        ImageView imageView = upView.findViewById(R.id.popw_back);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("id", order);
            skipActivity(intent);
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
            if(payType == 1){  //??????
                getPresenter().getPaymentById(order,"weixin");
            } else if (payType == 2) {
                getPresenter().getPaymentById(order, "alipay");
            }
            popupWindow.dismiss();
        });

        TextView popPrice = upView.findViewById(R.id.pop_price);
        TextView popNum = upView.findViewById(R.id.pop_people);

        popNum.setText("???" + studentAdapter.getData().size() + "???");
        popPrice.setText(DemoUtils.changTVsize("???" + DemoUtils.ToDouble(itemsBean.getPrice() * studentAdapter.getData().size())));
        popwSubmit.setText("????????????  ???" + DemoUtils.ToDouble(itemsBean.getPrice() * studentAdapter.getData().size()));


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

    private String order = "";

    @Override
    public void getOrderSuccess(PayResultBean bean) {
        if (bean != null) {
            order = bean.getOrder();
            DemoConstant.order = bean.getOrder();
            getPoppurch();
//            Intent intent = new Intent(context, OrderDetailActivity.class);
//            intent.putExtra("id", bean.getOrder());
//            skipActivity(intent);
//            finish();
        }
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
                    PayTask alipay = new PayTask(ExaminationPayActivity.this);
                    Map<String, String> result = alipay.payV2(s, true);

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

    @Override
    public void getPaymentWeChatError() {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("id", order);
            skipActivity(intent);
            finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(ReturnPayResult result){
        if(result.getStatus().equals("WXPayEntryActivity")){
            finish();
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
                    /**
                     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    Log.d("111", resultInfo+","+resultStatus+"");
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        ToastUtils.showShort("????????????");
                        getPaymentWeChatError();
                    } else {
                        // ???????????????????????????????????????????????????????????????????????????
                        ToastUtils.showShort("????????????");
                        getPaymentWeChatError();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
                        showAlert(ExaminationPayActivity.this, "??????===" + authResult);
                        getPaymentWeChatError();
                    } else {
                        // ?????????????????????????????????
                        showAlert(ExaminationPayActivity.this, "??????===" + authResult);
                        getPaymentWeChatError();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //??????????????????
        EventBus.getDefault().unregister(this);
    }
}