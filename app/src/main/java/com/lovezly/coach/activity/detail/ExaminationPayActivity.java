package com.lovezly.coach.activity.detail;

import android.content.Intent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.adapter.MainAdapter;
import com.lovezly.coach.activity.adapter.StudentAdapter;
import com.lovezly.coach.activity.order.OrderDetailActivity;
import com.lovezly.coach.assist.RecycleViewDivider;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PayResultBean;
import com.lovezly.coach.databinding.ActivityExaminationPayBinding;
import com.lovezly.coach.dialog.RegisterDialog;
import com.lovezly.coach.dialog.StudentDialog;
import com.lovezly.coach.util.CommonPopupWindow;
import com.lovezly.coach.util.DemoUtils;

public class ExaminationPayActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityExaminationPayBinding> implements DetailView {

    private ExamDetailBean.ItemsBean itemsBean;
    private String itemName;

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

        itemsBean = (ExamDetailBean.ItemsBean) getIntent().getSerializableExtra("bean");
        itemName = getIntent().getStringExtra("item_name");

        if(itemsBean!=null){
            mBinding.orderDetailTitle.setText(itemName);
            mBinding.orderDetailName.setText(itemsBean.getName());
            mBinding.orderDetailDate.setText("考试日期："+itemsBean.getBook_date());
        }

        mBinding.titleBack.setOnClickListener(view -> {
            finish();
        });

        mBinding.studentAdd.setOnClickListener(view -> {
            new StudentDialog(context) {
                @Override
                public void onOkClick(String name, String phone, String idCard) {
                    super.onOkClick(name, phone, idCard);
                    getPresenter().getAddStudent(itemsBean.getId(), name, phone ,idCard);
                }
            }.show();
        });

        mBinding.orderDetailSubmit.setOnClickListener(view -> {
            getPoppurch();
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
            if(ids == R.id.item_student_jian){
                getPresenter().getDelStudent(dataBean.getId());
            }
        });
        mBinding.detailEnvironmentRv.setAdapter(studentAdapter);
    }

    private void initData() {
        if(itemsBean != null){
            getPresenter().getStudents(itemsBean.getId());
        }
    }

    @Override
    public void getStudentsSuccess(PayBean bean) {
        if(bean!=null) {
            studentAdapter.setNewData(bean.getData());
            mBinding.orderDetailRvnum.setText("共" + studentAdapter.getData().size() + "人");
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
            getPresenter().getOrder(itemsBean.getId());
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
            getPresenter().getOrder(itemsBean.getId());
            popupWindow.dismiss();
        });

        TextView popPrice = upView.findViewById(R.id.pop_price);
        TextView popNum = upView.findViewById(R.id.pop_people);

        popNum.setText("共" + studentAdapter.getData().size() + "人");
        popPrice.setText(DemoUtils.changTVsize("￥"+DemoUtils.ToDouble(itemsBean.getPrice() * studentAdapter.getData().size())));
        popwSubmit.setText("确认支付  ￥"+DemoUtils.ToDouble(itemsBean.getPrice() * studentAdapter.getData().size()));


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

    @Override
    public void getOrderSuccess(PayResultBean bean) {
        if(bean!=null){
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("id", bean.getOrder());
            skipActivity(intent);
        }
    }
}