package com.lovezly.coach.activity.detail;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.order.OrderDetailActivity;
import com.lovezly.coach.databinding.ActivityExaminationPayBinding;
import com.lovezly.coach.util.CommonPopupWindow;
import com.lovezly.coach.util.DemoUtils;

public class ExaminationPayActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityExaminationPayBinding> implements DetailView {


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

        mBinding.titleBack.setOnClickListener(view -> {
            finish();
        });

        mBinding.orderDetailSubmit.setOnClickListener(view -> {
            getPoppurch();
        });
    }

    private CommonPopupWindow popupWindow;
    private int payType = 1;

    private void getPoppurch() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(context).inflate(R.layout.pop_confirm, null);
        payType = 1;
        ImageView imageView = upView.findViewById(R.id.popw_back);
        imageView.setOnClickListener(view -> popupWindow.dismiss());
        ImageView imageView1 = upView.findViewById(R.id.pop_confirm_wx);
        ImageView imageView2 = upView.findViewById(R.id.pop_confirm_zfb);
        RelativeLayout relativeLayout1 = upView.findViewById(R.id.pop_confirm_wx_rel);
        RelativeLayout relativeLayout2 = upView.findViewById(R.id.pop_confirm_zfb_rel);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = 1;
                imageView1.setImageResource(R.drawable.login_yes);
                imageView2.setImageResource(R.drawable.login_no);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = 2;
                imageView1.setImageResource(R.drawable.login_yes);
                imageView2.setImageResource(R.drawable.login_no);
            }
        });

        TextView popwSubmit = upView.findViewById(R.id.popw_submit);
        popwSubmit.setOnClickListener(view -> {
            skipActivity(OrderDetailActivity.class);
            popupWindow.dismiss();
        });
        TextView popPrice = upView.findViewById(R.id.pop_price);


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