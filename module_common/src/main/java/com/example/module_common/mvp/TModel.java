package com.example.module_common.mvp;

import com.google.gson.Gson;

/**
 * MVP设计模式：Model层（基类）
 *
 * 数据层（数据库、文件、网络等等...）
 *
 * 注：关联Gson库，用于数据处理
 *
 */
public class TModel {

    protected Gson gson;

    public TModel() {
        gson = new Gson();

    }
}
