package com.lovezly.coach.activity.detail;

import com.example.module_common.base.TView;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PayResultBean;

public interface DetailView extends TView {
    default void getExamDetailSuccess(ExamDetailBean bean) {

    }

    default void getAddStudentSuccess() {

    }

    default void getStudentsSuccess(PayBean bean) {

    }

    default void getDelStudentSuccess() {

    }

    default void getOrderSuccess(PayResultBean bean){

    }

    default void getOrderDetailSuccess(OrderDetailBean bean){

    }
}
