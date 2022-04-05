package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.ExamIndexBean;

import java.util.List;

public class MainAdapter extends BaseQuickAdapter<ExamIndexBean.DataBean, BaseViewHolder> {


    public MainAdapter(@Nullable List<ExamIndexBean.DataBean> data) {
        super(R.layout.item_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamIndexBean.DataBean item) {
        helper.setText(R.id.item_main_name,item.getName());
        helper.setText(R.id.item_main_book_date, "训练场次："+item.getBook_date());
        helper.setText(R.id.item_main_address,item.getAddress());
        helper.setText(R.id.item_main_trainingtime,item.getTrainingtime());
    }
}
