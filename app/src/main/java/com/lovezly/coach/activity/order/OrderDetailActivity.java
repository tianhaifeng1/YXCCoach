package com.lovezly.coach.activity.order;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.adapter.OrderDetailAdapter;
import com.lovezly.coach.activity.adapter.StudentAdapter;
import com.lovezly.coach.activity.detail.DetailPresenter;
import com.lovezly.coach.activity.detail.DetailView;
import com.lovezly.coach.assist.RecycleViewDivider;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.databinding.ActivityOrderDetailBinding;
import com.lovezly.coach.util.CommonPopupWindow;
import com.lovezly.coach.util.DemoUtils;

public class OrderDetailActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityOrderDetailBinding> implements DetailView {

    private String id = "";

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
        titleModule.initTitle("订单详情", true);
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

    private OrderDetailBean orderDetailBean;

    @Override
    public void getOrderDetailSuccess(OrderDetailBean bean) {
        if (bean != null) {
            orderDetailBean = bean;
            if(bean.getPay_status() == 1){
                mBinding.orderDetailLin1.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_1));
            }else if(bean.getPay_status() == 0){
                mBinding.orderDetailLin0.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_0));
            }else if(bean.getPay_status() == -1){
                mBinding.orderDetailLin2.setVisibility(View.VISIBLE);
                mBinding.orderDetailPrice.setTextColor(getResources().getColor(R.color.price_1));
            }

            mBinding.orderDetailName.setText(bean.getName());
            mBinding.orderDetailTime.setText(bean.getBook_date());
            mBinding.orderDetailNumber.setText(bean.getOrder_sn());
            mBinding.orderDetailPaytime.setText(bean.getPaytime());
            mBinding.orderDetailPrice.setText(DemoUtils.changTVsize("￥"+DemoUtils.ToDouble(bean.getPrice())));
            orderDetailAdapter.setNewData(bean.getStudent());
        }
    }

    @Override
    public void getOrderCancelSuccess() {
        ToastUtils.showShort("取消成功");
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
            popupWindow.dismiss();
        });

        TextView popPrice = upView.findViewById(R.id.pop_price);
        TextView popNum = upView.findViewById(R.id.pop_people);

        popNum.setText("共" + orderDetailAdapter.getData().size() + "人");
        popPrice.setText(DemoUtils.changTVsize("￥"+DemoUtils.ToDouble(orderDetailBean.getPrice())));
        popwSubmit.setText("确认支付  ￥"+DemoUtils.ToDouble(orderDetailBean.getPrice()));

        //测量View的宽高
        DemoUtils.measureWidthAndHeight(upView);

        popupWindow = new CommonPopupWindow.Builder(context)
                .setView(upView)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                // .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗   此方法无用 必须重写
                .setAnimationStyle(R.style.MyPopupWindow_anim_style)
                .create();
        // 产生背景变暗效果，设置透明度  必须重写 否则背景无法变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.2f;
        //之前不写这一句也是可以实现的，这次突然没效果了。网搜之后加上了这一句就好了。据说是因为popUpWindow没有getWindow()方法。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }
}