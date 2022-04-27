package com.lovezly.coach.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.official.OfficialMVPActivity;
import com.example.module_common.util.SharedPreferencesUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.DateBean;
import com.lovezly.coach.bean.UserBean;
import com.lovezly.coach.databinding.ActivityLoginBinding;
import com.lovezly.coach.util.DemoConstant;
import com.lovezly.coach.util.SPUtils;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityLoginBinding> implements LoginView {

    private int yxType = 1;

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        getPermission();//获取权限

        mBinding.loginXy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yxType == 1) {
                    yxType = 2;
                    mBinding.loginXy.setImageDrawable(getResources().getDrawable(R.drawable.login_no));
                } else {
                    yxType = 1;
                    mBinding.loginXy.setImageDrawable(getResources().getDrawable(R.drawable.login_yes));
                }
            }
        });
        mBinding.loginYhxy.setOnClickListener(view -> {
            Intent intent = new Intent(context, YhxyActivity.class);
            intent.putExtra("type", 1);
            skipActivity(intent);
        });
        mBinding.loginYzm.setOnClickListener(view -> {
            if (mBinding.loginEditPhone.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入手机号");
            }else if (!RegexUtils.isMobileExact(mBinding.loginEditPhone.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的手机号");
            } else {
                getPresenter().getRegCode(mBinding.loginEditPhone.getText().toString().trim(),"mobilelogin");
            }
        });
        mBinding.loginSubmit.setOnClickListener(view -> {
            if (mBinding.loginEditPhone.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入手机号");
            } else if (mBinding.loginEditYzm.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入验证码");
            } else if (!RegexUtils.isMobileExact(mBinding.loginEditPhone.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的手机号");
            } else if (yxType != 1) {
                ToastUtils.showShort("请先阅读并同意用户协议");
            } else {
                getPresenter().getLogin(mBinding.loginEditPhone.getText().toString().trim(), mBinding.loginEditYzm.getText().toString().trim());
//                skipActivity(MainNavActivity.class);
            }
        });
        mBinding.loginRegister.setOnClickListener(view -> {
            skipActivity(RegisterActivity.class);
        });
    }

    //要申请的权限
    private String[] requestAll = {
            Manifest.permission.INTERNET,//网络
            Manifest.permission.READ_PHONE_STATE,//获取手机状体
            Manifest.permission.ACCESS_WIFI_STATE,//获取WIFI状态
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
    };

    //申请权限
    private void getPermission() {
        XXPermissions.with(this)
                .constantRequest()// 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(requestAll)// 支持请求6.0悬浮窗权限8.0请求安装权限
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                //getPermission();
                                if (!XXPermissions.isHasPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                                    finish();
                                    return;
                                }
                            }

                            //String DEVICE_ID = tm.getSubscriberId();
                            //ToastView.initToast().textToast(LoginActivity.this, DEVICE_ID);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtils.showShort("请选择'" + getResources().getString(R.string.app_name) + "'程序，点击进行手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(LoginActivity.this);
                        } else {
                            ToastUtils.showShort("禁止权限有可能影响APP正常运行");
                        }
                    }
                });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (djs >= 0) {
                        mBinding.loginYzm.setText(djs + "秒");
                        djs--;
                    } else {
                        timer.cancel();
                        timer = null;
                        mBinding.loginYzm.setEnabled(true);
                        mBinding.loginYzm.setText("重新获取");
                    }
                    break;
            }
        }
    };

    private Timer timer;
    private int djs = 60;

    @Override
    public void eventDjs() {
        mBinding.loginYzm.setEnabled(false);
        djs = 60;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }

    @Override
    public void getLoginSuccess(UserBean bean) {
        DemoConstant.token = bean.getUserinfo().getToken();
        DemoConstant.userId = bean.getUserinfo().getUser_id();
        DemoConstant.avatar = bean.getUserinfo().getAvatar();
        DemoConstant.nickname = bean.getUserinfo().getNickname();
        DemoConstant.mobile = bean.getUserinfo().getMobile();

        SharedPreferencesUtils.setParam(context, DemoConstant.user_token, bean.getUserinfo().getToken());
        SharedPreferencesUtils.setParam(context, DemoConstant.user_id, bean.getUserinfo().getUser_id());
        SharedPreferencesUtils.setParam(context, DemoConstant.user_avatar, bean.getUserinfo().getAvatar());
        SharedPreferencesUtils.setParam(context, DemoConstant.user_nickname, bean.getUserinfo().getNickname());
        SharedPreferencesUtils.setParam(context, DemoConstant.user_mobile, bean.getUserinfo().getMobile());

        DateBean dateBean = new DateBean();
        dateBean.setLastLoginTime();
        SPUtils.setDateBean(context, dateBean);

        skipActivity(MainNavActivity.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
        if (isFinishing()) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    @Override
    protected boolean backBefore() {
        ToastUtils.showShort("再次点击退出系统");
        activityManager.exitApp(true);
        return false;
    }
}