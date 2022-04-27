package com.lovezly.coach.activity.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.module_common.official.OfficialMVPFragment;
import com.example.module_common.util.GlideUtile;
import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.LoginActivity;
import com.lovezly.coach.activity.MainNavActivity;
import com.lovezly.coach.activity.YhxyActivity;
import com.lovezly.coach.activity.personal.UserInfoActivity;
import com.lovezly.coach.bean.UserInfoBean;
import com.lovezly.coach.databinding.FragmentMainFourBinding;
import com.lovezly.coach.dialog.CommonDialog;
import com.lovezly.coach.util.DemoConstant;
import com.lovezly.coach.util.SPUtils;

public class MainFourFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainFourBinding mBinding;

    private MainNavActivity mainNavActivity;

    private MainNavActivity navActivity;

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
        navActivity = (MainNavActivity) getActivity();

        mBinding.fourHeadRel.setOnClickListener(view1 -> activity.skipActivity(UserInfoActivity.class));

        mainNavActivity = (MainNavActivity) getActivity();

        mBinding.fourLin1.setOnClickListener(view1 -> mainNavActivity.skipTab(2));
        mBinding.fourLin2.setOnClickListener(view1 -> mainNavActivity.skipTab(2));


        Glide.with(activity)
                .load(DemoConstant.avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) mBinding.fourHead);

        mBinding.fourName.setText(DemoConstant.nickname);

        mBinding.fourZx.setOnClickListener(view1 -> {
            new CommonDialog(activity) {
                @Override
                public void onOkClick() {
                    super.onOkClick();
                    DemoConstant.token = "";
                    DemoConstant.userId = 0;
                    DemoConstant.avatar = "";
                    DemoConstant.nickname = "";
                    DemoConstant.mobile = "";

                    SharedPreferencesUtils.setParam(activity, DemoConstant.user_token, "");
                    SharedPreferencesUtils.setParam(activity, DemoConstant.user_id, 0);
                    SharedPreferencesUtils.setParam(activity, DemoConstant.user_avatar, "");
                    SharedPreferencesUtils.setParam(activity, DemoConstant.user_nickname, "");
                    SharedPreferencesUtils.setParam(activity, DemoConstant.user_mobile, "");

                    SPUtils.removeDateBean(activity);

                    activity.skipActivity(LoginActivity.class);
                    navActivity.finish();
                }
            }.show();
        });

        mBinding.fourAbout.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, YhxyActivity.class);
            intent.putExtra("type", 2);
            activity.skipActivity(intent);
        });

        mBinding.fourKf.setOnClickListener(view1 -> {
            ToastUtils.showShort("开发中，请期待新版本");
        });
    }

    @Override
    public void initData() {
        super.initData();
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
        }
    }
}
