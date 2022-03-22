package com.example.module_common.official;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.module_common.base.TView;
import com.example.module_common.mvp.TModel;
import com.example.module_common.mvp.TPresenter;
import com.example.module_common.rxhttp.ErrorInfo;

public class OfficialPresenter<V extends TView> extends TPresenter<V, TModel> {

    public OfficialPresenter(@NonNull V view) {
        super(view);
    }

    protected void tishi(String msg) {
        if (isViewAttach()) {
            getView().tRemind(msg);
        }
    }

    public void responseState(ErrorInfo error) {
        if (error.getErrorCode() == 501) {
            getView().tPostFinish(501);
        } else {
            LogUtils.i("错误===", error.getThrowable().getMessage());
            ToastUtils.showShort(error.getErrorMsg());
        }
    }
}
