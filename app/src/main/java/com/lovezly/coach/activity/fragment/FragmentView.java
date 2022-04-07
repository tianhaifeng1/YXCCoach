package com.lovezly.coach.activity.fragment;

import com.example.module_common.base.TView;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.OrderListBean;
import com.lovezly.coach.bean.UserInfoBean;

public interface FragmentView extends TView {

    default void getExamIndexSuccess(ExamIndexBean bean) {

    }

    default void getOrderListSuccess(OrderListBean bean) {

    }

    default void getOrdersAnalysisSuccess(UserInfoBean bean) {

    }
}
