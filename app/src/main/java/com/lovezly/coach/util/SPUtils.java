package com.lovezly.coach.util;

import android.content.Context;

import com.example.module_common.util.SharedPreferencesUtils;
import com.lovezly.coach.bean.DateBean;

import rxhttp.wrapper.utils.GsonUtil;

public class SPUtils {
    public static void setDateBean(Context context, DateBean dateBean) {
        String login = DemoUtils.toJson(dateBean);
        SharedPreferencesUtils.setParam(context, DemoConstant.date_bean, login);
    }

    public static DateBean getDateBean(Context context) {
        String date = (String) SharedPreferencesUtils.getParam(context, DemoConstant.date_bean, "");
        if (!date.equals("")) {
            return GsonUtil.getObject(date, DateBean.class);
        }
        return null;
    }

    public static void removeDateBean(Context context) {
        String date = "";
        SharedPreferencesUtils.setParam(context, DemoConstant.date_bean, date);
    }
}
