package com.lovezly.coach.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.module_common.official.OfficialMVPFragment;
import com.lovezly.coach.R;
import com.lovezly.coach.activity.adapter.MainAdapter;
import com.lovezly.coach.activity.detail.ExaminationDetailActivity;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.SelectDateBean;
import com.lovezly.coach.databinding.FragmentMainOneBinding;
import com.lovezly.coach.databinding.FragmentMainTwoBinding;
import com.lovezly.coach.util.DemoUtils;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainTwoFragment extends OfficialMVPFragment<FragmentView, FragmentPresenter> implements FragmentView {

    private FragmentMainTwoBinding mBinding;

    private int index = 1;

    @Override
    protected FragmentPresenter initPersenter() {
        return new FragmentPresenter(this);
    }

    @Override
    public void bindingView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentMainTwoBinding.inflate(inflater);
    }

    @Override
    public View getLayoutId() {
        return mBinding.getRoot();
    }

    @Override
    protected void initFragmentView(View view) {
        mBinding.twoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                index = 1;
                initData();
            }
        });

        mBinding.refreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index++;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                mBinding.twoCc.setText("????????????");
                bookDate = "";
                initData();
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });

        initAdapter();

        mBinding.twoCcLin.setOnClickListener(view1 -> {
//            onYearMonthDayTimePicker();
            onSelectDate();
        });
    }

    private MainAdapter mainAdapter;

    private void initAdapter() {
        mBinding.twoRv.setLayoutManager(new LinearLayoutManager(activity));
        mainAdapter = new MainAdapter(null);
        mainAdapter.setOnItemClickListener((adapter, view, position) -> {
            ExamIndexBean.DataBean dataBean = (ExamIndexBean.DataBean) adapter.getData().get(position);
            Intent intent = new Intent(activity, ExaminationDetailActivity.class);
            intent.putExtra("id", dataBean.getId());
            activity.skipActivity(intent);
        });
        View view = LayoutInflater.from(activity).inflate(R.layout.default_view, null);
        mainAdapter.setEmptyView(view);
        mBinding.twoRv.setAdapter(mainAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        getPresenter().getExamIndex(bookDate,mBinding.twoSearch.getText().toString().trim(),index,2);
        getPresenter().selectdata();
    }

    private List<SelectDateBean> selectDateBeans = new ArrayList<>();

    @Override
    public void getSelectDateSuccess(List<SelectDateBean> bean) {
        selectDateBeans = bean;
    }

    @Override
    public void getExamIndexSuccess(ExamIndexBean bean) {
        if(bean!=null){
            if (index == 1) {
                mBinding.refreshLayout.finishRefresh();
                mainAdapter.setNewData(bean.getData());
            } else {
                mBinding.refreshLayout.finishLoadMore();
                mainAdapter.addData(bean.getData());
            }
        }else{
            ToastUtils.showShort("????????????");
        }
    }

    private String bookDate = "";

    public void onSelectDate() {
        if (selectDateBeans != null && selectDateBeans.size() > 0) {
            List<String> option1 = new ArrayList<>();
            for (SelectDateBean date : selectDateBeans) {
                option1.add(date.getBook_date());
            }
            OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    bookDate = option1.get(options1);
                    index = 1;
                    initData();
                    mBinding.twoCc.setText(bookDate);
                }
            }).setTitleText("?????????")
                    .setContentTextSize(16)//??????????????????
                    .setLineSpacingMultiplier((float) 2.0)
                    .setOutSideCancelable(false)
                    .build();
            pvOptions.setPicker(option1);
            pvOptions.show();
        } else {
            ToastUtils.showShort("????????????");
        }
    }

    public void onYearMonthDayTimePicker() {
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //?????????????????? ??????????????????????????????
        startDate.set(2017, 0, 1);
        endDate.set(2111, 11, 31);
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                bookDate = format.format(date);
                initData();
                mBinding.twoCc.setText(format.format(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .setItemVisibleCount(3) //?????????????????????????????????1???????????????6???????????????????????????7???
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
                dialogWindow.setDimAmount(0.3f);
            }
        }
        pvTime.show();
    }
}
