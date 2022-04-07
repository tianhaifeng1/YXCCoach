package com.lovezly.coach.bean;

import java.util.Date;

public class DateBean {
    private Date lastLoginTime;//最后登录日期时间

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(){
        lastLoginTime = new Date();
    }
}
