package com.lovezly.coach.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.module_common.official.OfficialMVPActivity;
import com.example.module_common.util.GlideUtile;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.lovezly.coach.R;
import com.lovezly.coach.bean.UploadBean;
import com.lovezly.coach.databinding.ActivityRegisterBinding;
import com.lovezly.coach.dialog.RegisterDialog;
import com.lovezly.coach.util.DemoUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class RegisterActivity extends OfficialMVPActivity<LoginView, LoginPresenter, ActivityRegisterBinding> implements LoginView {

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LoginPresenter initPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        titleModule.initTitle("注册新用户", true);
        titleModule.getTitleView().setVisibility(View.VISIBLE);

        mBinding.registerYzm.setOnClickListener(view -> {
            if (mBinding.registerEditPhone.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入手机号");
            } else if (!RegexUtils.isMobileExact(mBinding.registerEditPhone.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的手机号");
            } else {
                getPresenter().getRegCode(mBinding.registerEditPhone.getText().toString().trim(), "register");
            }
        });

        mBinding.registerSubmit.setOnClickListener(view -> {
            if (mBinding.registerEditPhone.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入手机号");
            } else if (mBinding.registerEditYzm.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入验证码");
            } else if (!RegexUtils.isMobileExact(mBinding.registerEditPhone.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的手机号");
            } else if (mBinding.registerEditName.getText().toString().trim().equals("")) {
                ToastUtils.showShort("请输入姓名");
            } else if (front.equals("")) {
                ToastUtils.showShort("请上传身份证正面");
            } else if (backend.equals("")) {
                ToastUtils.showShort("请上传身份证反面");
            } else {
                getPresenter().getRegister(mBinding.registerEditName.getText().toString().trim(), mBinding.registerEditPhone.getText().toString().trim(),
                        mBinding.registerEditYzm.getText().toString().trim(), front, backend);
            }
        });
        mBinding.idCardJust.setOnClickListener(view -> {
            uploadType = 1;
            getPermission();
        });
        mBinding.idCardBack.setOnClickListener(view -> {
            uploadType = 2;
            getPermission();
        });

        //初始化图库工具
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new AlbumLoader() {
                    @Override
                    public void load(ImageView imageView, AlbumFile albumFile) {
                        load(imageView, albumFile.getPath());
                    }

                    @Override
                    public void load(ImageView imageView, String url) {
                        //本地大图多选的加载图片，用glide会崩溃
                        GlideUtile.bindImageView(imageView.getContext(), url, imageView);
                    }
                })
                .setLocale(Locale.CHINA)
                .build());
    }

    @Override
    public void getRegisterSuccess() {
        new RegisterDialog(context) {
            @Override
            public void onOkClick() {
                super.onOkClick();
                finish();
            }
        }.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (djs >= 0) {
                        mBinding.registerYzm.setText(djs + "秒");
                        djs--;
                    } else {
                        timer.cancel();
                        timer = null;
                        mBinding.registerYzm.setEnabled(true);
                        mBinding.registerYzm.setText("重新获取");
                    }
                    break;
            }
        }
    };

    private Timer timer;
    private int djs = 60;

    @Override
    public void eventDjs() {
        mBinding.registerYzm.setEnabled(false);
        djs = 60;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }

    private int uploadType = 1;

    private String front = "";
    private String backend = "";

    @Override
    public void getUploadlSuccess(UploadBean bean) {
        if (bean != null) {
            if (uploadType == 1) {
                front = bean.getUrl();
            } else {
                backend = bean.getUrl();
            }
        }
    }

    private String[] requestAll = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    /**
     * 选择图片
     */
    private void getPermission() {
        XXPermissions.with(this)
                .constantRequest()
                .permission(requestAll)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            if (hasSdcard()) {
                                Album.image(context) // Image selection.
                                        .multipleChoice()
                                        .camera(true)
                                        .columnCount(3)
                                        .selectCount(1)
                                        .onResult(new Action<ArrayList<AlbumFile>>() {
                                            @Override
                                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                                if (result != null && result.size() > 0) {
                                                    path = result.get(0).getPath();
                                                    if (uploadType == 1) {
                                                        DemoUtils.bindImageViewC(context, path, 5, mBinding.idCardJust);
                                                    } else {
                                                        DemoUtils.bindImageViewC(context, path, 5, mBinding.idCardBack);
                                                    }
                                                    upload();
                                                }
                                            }
                                        })
                                        .onCancel(new Action<String>() {
                                            @Override
                                            public void onAction(@NonNull String result) {
                                            }
                                        })
                                        .start();
                            } else {
                                ToastUtils.showShort("设备没有SD卡！");
                            }
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtils.showShort("请选择'" + getResources().getString(R.string.app_name) + "'程序，点击进行手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(context);
                        } else {
                            ToastUtils.showShort("禁止权限有可能影响APP正常运行");
                        }
                    }
                });
    }

    private String path;

    private File file;

    private void upload() {
        file = new File(path);
        Luban.with(context)
                .load(file)// 传人要压缩的图片列表
                .ignoreBy(50)// 忽略不压缩图片的大小
                .setTargetDir(DemoUtils.getPath(context))// 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener()//设置回调
                {
                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    @Override
                    public void onStart() {
                    }

                    // TODO 压缩成功后调用，返回压缩后的图片文件
                    @Override
                    public void onSuccess(File file) {
                        file = file;
                        getPresenter().getUpload(file);
                    }

                    // TODO 当压缩过去出现问题时调用
                    @Override
                    public void onError(Throwable e) {
                        getPresenter().getUpload(file);
                    }
                }).launch();//启动压缩
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
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
}