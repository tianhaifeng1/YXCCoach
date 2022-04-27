package com.lovezly.coach.activity.detail;

import com.example.module_common.base.TView;
import com.lovezly.coach.bean.ExamDetailBean;
import com.lovezly.coach.bean.OrderDetailBean;
import com.lovezly.coach.bean.PayBean;
import com.lovezly.coach.bean.PayResultBean;
import com.lovezly.coach.bean.UploadBean;

public interface DetailView extends TView {
    default void getExamDetailSuccess(ExamDetailBean bean) {

    }

    default void getAddStudentSuccess() {

    }

    default void getStudentsSuccess(PayBean bean) {

    }

    default void getDelStudentSuccess() {

    }

    default void getOrderSuccess(PayResultBean bean) {

    }

    default void getOrderDetailSuccess(OrderDetailBean bean) {

    }

    default void getOrderCancelSuccess() {

    }

    default void getUploadlSuccess(UploadBean bean) {

    }

    default void getProfileSuccess() {

    }

    default void getPaymentWeChatSuccess(String s) {

    }

    default void getPaymentWeChatError() {

    }
}
