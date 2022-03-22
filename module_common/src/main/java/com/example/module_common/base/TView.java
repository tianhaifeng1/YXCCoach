package com.example.module_common.base;

public interface TView {

    void showDialog(String msg);

    void hideDialog();

    /**
     * 请求失败:
     */
    void tPostFail(int resultState);

    /**
     * 提示方法:
     * @param message 提示文字
     */
    void tRemind(String message);

    /**
     * 请求异常
     * @param errorMsg
     */
    void tPostError(String errorMsg);


    /**
     * 请求完成
     * @param code 用于区分那个请求
     */
    void tPostFinish(int code);
}