package com.lovezly.coach.activity.personal;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ImageUtils;
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
import com.lovezly.coach.activity.detail.DetailPresenter;
import com.lovezly.coach.activity.detail.DetailView;
import com.lovezly.coach.bean.UploadBean;
import com.lovezly.coach.databinding.ActivityUserInfoBinding;
import com.lovezly.coach.util.DemoConstant;
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

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UserInfoActivity extends OfficialMVPActivity<DetailView, DetailPresenter, ActivityUserInfoBinding> implements DetailView {


    @Override
    protected ActivityUserInfoBinding getViewBinding() {
        return ActivityUserInfoBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailPresenter initPersenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        titleModule.initTitle("????????????", true);
        titleModule.getTitleView().setVisibility(View.VISIBLE);
        titleModule.initTitleMenu(0, "??????");

        mBinding.userInfoHeadLin.setOnClickListener(view -> {
            getPermission();
        });

        Glide.with(context)
                .load(DemoConstant.avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) mBinding.userInfoHead);

        mBinding.userInfoName.setText(DemoConstant.nickname);
        mBinding.userInfoPhone.setText(DemoConstant.mobile);
        if (DemoConstant.status.equals("normal")) {
            mBinding.userInfoStatus.setText("?????????");
        } else {
            mBinding.userInfoStatus.setText("?????????");
        }
        uploadPath = DemoConstant.avatar;
    }

    @Override
    public void onClickRightText(View view) {
        super.onClickRightText(view);
        if (mBinding.userInfoName.getText().toString().trim().equals("")) {
            ToastUtils.showShort("??????????????????");
        } else if (uploadPath.equals("")) {
            ToastUtils.showShort("???????????????");
        } else {
            getPresenter().getProfile(mBinding.userInfoName.getText().toString().trim(), uploadPath);
        }
    }

    @Override
    public void getProfileSuccess() {
        ToastUtils.showShort("????????????");
        finish();
    }

    private String[] requestAll = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    /**
     * ????????????
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
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                Date curDate = new Date(System.currentTimeMillis());

                                if (Build.VERSION.SDK_INT >= 29) {
                                    //Android10??????
                                    path = context.getExternalFilesDir(null) + "/Graph/Image/" + formatter.format(curDate) + ".jpg";
                                } else {
                                    path = Environment.getExternalStorageDirectory() + "/Graph/Image/" + formatter.format(curDate) + ".jpg";
                                }

                                //?????????????????????
                                Album.initialize(AlbumConfig.newBuilder(context)
                                        .setAlbumLoader(new AlbumLoader() {
                                            @Override
                                            public void load(ImageView imageView, AlbumFile albumFile) {
                                                load(imageView, albumFile.getPath());
                                            }

                                            @Override
                                            public void load(ImageView imageView, String url) {
                                                //???????????????????????????????????????glide?????????
//                                                Bitmap bitmap = BitmapFactory.decodeFile(url);
//                                                imageView.setImageBitmap(bitmap);
                                                GlideUtile.bindImageView(imageView.getContext(), url, imageView);
                                            }
                                        })
                                        .setLocale(Locale.CHINA)
                                        .build());

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
                                                    Glide.with(context)
                                                            .load(path)
                                                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                                            .into((ImageView) mBinding.userInfoHead);
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
                                ToastUtils.showShort("????????????SD??????");
                            }
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtils.showShort("?????????'" + getResources().getString(R.string.app_name) + "'???????????????????????????????????????");
                            //??????????????????????????????????????????????????????????????????
                            XXPermissions.gotoPermissionSettings(context);
                        } else {
                            ToastUtils.showShort("???????????????????????????APP????????????");
                        }
                    }
                });
    }

    private String uploadPath = "";

    @Override
    public void getUploadlSuccess(UploadBean bean) {
        if (bean != null) {
            uploadPath = bean.getUrl();
        }
    }

    private String path;
    private File headFile;

    private void upload() {
        headFile = new File(path);
        Luban.with(context)
                .load(headFile)// ??????????????????????????????
                .ignoreBy(50)// ??????????????????????????????
                .setTargetDir(DemoUtils.getPath(context))// ?????????????????????????????????
                .setCompressListener(new OnCompressListener()//????????????
                {
                    // TODO ???????????????????????????????????????????????? loading UI
                    @Override
                    public void onStart() {
                    }

                    // TODO ??????????????????????????????????????????????????????
                    @Override
                    public void onSuccess(File file) {
                        headFile = file;
                        getPresenter().getUpload(headFile);
                    }

                    // TODO ????????????????????????????????????
                    @Override
                    public void onError(Throwable e) {
                        getPresenter().getUpload(headFile);
                    }
                }).launch();//????????????
    }

    /**
     * ????????????????????????SDCard???????????????
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}