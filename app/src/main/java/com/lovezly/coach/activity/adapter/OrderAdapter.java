package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.OrderListBean;
import com.lovezly.coach.util.DemoUtils;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<OrderListBean.DataBean, BaseViewHolder> {


    public OrderAdapter(@Nullable List<OrderListBean.DataBean> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean item) {
        helper.setText(R.id.item_order_name, item.getName());
        String status = "";
        if (item.getPay_status() == 0) {
            status = "未支付";
        } else if (item.getPay_status() == 1) {
            status = "已支付";
        } else if (item.getPay_status() == -1) {
            status = "已取消";
        }
        helper.setText(R.id.item_order_status, status);
        helper.setText(R.id.item_order_number, item.getOrder_sn());
        helper.setText(R.id.item_order_time, item.getBook_date());

        String students = "";

        for (OrderListBean.DataBean.StudentBean bean : item.getStudent()) {
            students += bean.getName() + "、";
        }
        helper.setText(R.id.item_order_student, students);
        helper.setText(R.id.item_order_paytime, item.getPaytime().equals("0") ? "无" : DemoUtils.timeStamp2Date(Long.parseLong(item.getPaytime()), null));
        helper.setText(R.id.item_order_price, "￥" + DemoUtils.ToDouble(item.getTotal_price()));
    }
}