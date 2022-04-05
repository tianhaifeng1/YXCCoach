package com.lovezly.coach.activity.fragment;

import com.example.module_common.base.TView;
import com.lovezly.coach.bean.ExamIndexBean;
import com.lovezly.coach.bean.OrderListBean;

public interface FragmentView extends TView {

    default void getExamIndexSuccess(ExamIndexBean bean) {

    }

    default void getOrderListSuccess(OrderListBean bean) {

    }
}
