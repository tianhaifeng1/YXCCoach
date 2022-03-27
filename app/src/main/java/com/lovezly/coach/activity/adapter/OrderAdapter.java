package com.lovezly.coach.activity.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderAdapter(@Nullable List<String> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}