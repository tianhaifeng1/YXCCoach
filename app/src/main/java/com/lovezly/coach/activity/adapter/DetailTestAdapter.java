package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.util.DemoUtils;

import java.util.List;

public class DetailTestAdapter extends BaseQuickAdapter<ExamDetailBean.ItemsBean, BaseViewHolder> {


    public DetailTestAdapter(@Nullable List<ExamDetailBean.ItemsBean> data) {
        super(R.layout.item_detail_test, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamDetailBean.ItemsBean item) {
        DemoUtils.bindImageViewC(getContext(),item.getImage(),5,helper.getView(R.id.item_detail_test_image));

        helper.setText(R.id.item_driving_title, "强训练车服务");
        helper.setText(R.id.item_driving_name, item.getName());
        helper.setText(R.id.item_driving_site, "训练场次："+item.getBook_date());
        helper.setText(R.id.item_driving_price,  DemoUtils.changTVsize("￥" + DemoUtils.ToDouble(item.getPrice()!=null?item.getPrice():0)+"/人"));
        helper.setText(R.id.item_driving_dlprice, "代理商价格：￥" + DemoUtils.changTVsize((item.getAgent_price()!=null?item.getAgent_price():0)+"/人"));
    }
}