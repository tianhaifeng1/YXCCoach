package com.example.module_common.mvp;

import androidx.annotation.NonNull;

import com.example.module_common.base.TView;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

/**
 * MVP设计模式：Presenter层（基类）
 * <p>
 * 中介（MVP设计目的：为了将UI层和数据层进行解耦和），通过P层进行关联
 */
public abstract class TPresenter<V extends TView, M extends TModel> implements IBaseTPresenter {

    // 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
    private WeakReference<V> mViewRef;
    protected M model;
    protected Gson gson;


    public TPresenter(@NonNull V view) {
        attachView(view);
        gson = new Gson();
    }

    private void attachView(V view) {
        mViewRef = new WeakReference(view);
    }

    /**
     * 设置TModel
     *
     * @param model
     */
    public void setModel(M model) {
        this.model = model;
    }

    /**
     * 获取View层对象
     *
     * @return
     */
    public V getView() {
        return mViewRef.get();
    }

    /**
     * 判断是否关联了View层
     *
     * @return
     */
    @Override
    public boolean isViewAttach() {
        return mViewRef != null && getView() != null;
    }

    /**
     * 解除View层关联
     */
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }


    @Override
    public void showDialog(String msg) {
        if (isViewAttach()) {
            getView().showDialog(msg);
        }
    }

    @Override
    public void hideDialog() {
        if (isViewAttach()) {
            getView().hideDialog();
        }
    }

    /**
     * 用于 Toast 提示
     * @param msg
     */
    public void onRemind(String msg) {
        if (isViewAttach()) {
            getView().tRemind(msg);
        }
    }
}
