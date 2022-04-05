package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.util.DemoUtils;

import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<PayBean.DataBean, BaseViewHolder> {


    public StudentAdapter(@Nullable List<PayBean.DataBean> data) {
        super(R.layout.item_student, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayBean.DataBean item) {
        helper.setText(R.id.item_student_name,item.getName()+"    "+item.getMobile());
        helper.setText(R.id.item_student_idcard,"身份证  "+ DemoUtils.idMask(item.getCardNum(),4,4));
    }
}