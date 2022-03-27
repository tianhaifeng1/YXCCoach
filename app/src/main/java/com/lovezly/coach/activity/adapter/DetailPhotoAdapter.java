package com.lovezly.coach.activity.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lovezly.coach.R;

import java.util.List;

public class DetailPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public DetailPhotoAdapter(@Nullable List<String> data) {
        super(R.layout.item_detail_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.item_detail_img);
        ViewGroup.LayoutParams params =  imageView.getLayoutParams();
        int width=(ScreenUtils.getScreenWidth()-200)/4;
        int height=width;
        params.width = width;
        params.height = height;
        imageView.setLayoutParams(params);
    }
}