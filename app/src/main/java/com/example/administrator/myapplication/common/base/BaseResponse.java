package com.example.administrator.myapplication.common.base;

/**
 * Created by zhanghao on 2016/8/5.
 */
public class BaseResponse<T> extends BaseEntity {
    public int error_code;
    public String reason;
    public T result;
}
