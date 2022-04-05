package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.util.DemoUtils;

import java.util.List;

public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailBean.StudentBean, BaseViewHolder> {


    public OrderDetailAdapter(@Nullable List<OrderDetailBean.StudentBean> data) {
        super(R.layout.item_order_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.StudentBean item) {
        helper.setText(R.id.item_order_detail_name,item.getName()+"    "+item.getMobile());
        helper.setText(R.id.item_order_detail_idcard,"身份证  "+ DemoUtils.idMask(item.getCardNum(),4,4));
    }
}