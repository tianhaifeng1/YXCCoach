package com.example.module_common.mvp;

public interface IBaseTPresenter {

    void showDialog(String msg);

    void hideDialog();

    boolean isViewAttach();

    void detachView();

}
