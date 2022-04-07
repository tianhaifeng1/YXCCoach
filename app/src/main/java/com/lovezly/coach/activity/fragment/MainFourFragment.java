package com.lovezly.coach.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.module_common.official.OfficialMVPFragment;
import com.example.module_common.util.GlideUtile;
import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.MainNavActivity;
import com.lovezly.coach.activity.personal.UserInfoActivity;
import com.lovezly.coach.bean.UserInfoBean;
import com.lovezly.coach.databinding.FragmentMainFourBinding;
import com.lovezly.coach.util.DemoConstant;

public class MainFourFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainFourBinding mBinding;

    private MainNavActivity mainNavActivity;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainFourBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {
        mBinding.fourHeadRel.setOnClickListener(view1 -> activity.skipActivity(UserInfoActivity.class));

        mainNavActivity = (MainNavActivity) getActivity();

        mBinding.fourLin1.setOnClickListener(view1 -> mainNavActivity.skipTab(2));
        mBinding.fourLin2.setOnClickListener(view1 -> mainNavActivity.skipTab(2));


        Glide.with(activity)
                .load(DemoConstant.avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) mBinding.fourHead);

        mBinding.fourName.setText(DemoConstant.nickname);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().getOrdersAnalysis();
    }

    @Override
    public void getOrdersAnalysisSuccess(UserInfoBean bean) {
        if (bean != null) {
            DemoConstant.userId = bean.getUser_id();
            DemoConstant.avatar = bean.getAvatar();
            DemoConstant.nickname = bean.getNickname();
            DemoConstant.mobile = bean.getMobile();
            DemoConstant.status = bean.getStatus();

            SharedPreferencesUtils.setParam(activity, DemoConstant.user_token, bean.getToken());
            SharedPreferencesUtils.setParam(activity, DemoConstant.user_id, bean.getUser_id());
            SharedPreferencesUtils.setParam(activity, DemoConstant.user_avatar, bean.getAvatar());
            SharedPreferencesUtils.setParam(activity, DemoConstant.user_nickname, bean.getNickname());
            SharedPreferencesUtils.setParam(activity, DemoConstant.user_mobile, bean.getMobile());

            Glide.with(activity)
                    .load(DemoConstant.avatar)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into((ImageView) mBinding.fourHead);

            if (DemoConstant.status.equals("normal")) {
                GlideUtile.bindImageView(getContext(), getResources().getDrawable(R.drawable.four_authentication), mBinding.fourStatus);
            }else{
                GlideUtile.bindImageView(getContext(), getResources().getDrawable(R.drawable.wrz), mBinding.fourStatus);
            }

            mBinding.fourName.setText(DemoConstant.nickname);

            mBinding.fourToday.setText(bean.getOrder_info().getToday());
            mBinding.fourMonth.setText(bean.getOrder_info().getMonth());
            mBinding.fourTotal.setText(bean.getOrder_info().getTotal());
        } else {

        }
    }
}
