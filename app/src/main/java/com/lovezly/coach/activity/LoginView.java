package com.lovezly.coach.activity;

import com.example.module_common.base.TView;
import com.lovezly.coach.bean.UserBean;

public interface LoginView extends TView {
    default void eventDjs(){

    }

    default void getLoginSuccess(UserBean bean){

    }
}
