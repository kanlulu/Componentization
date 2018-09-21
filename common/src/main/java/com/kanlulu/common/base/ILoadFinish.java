package com.kanlulu.common.base;

/**
 * http请求返回结果
 */
public interface ILoadFinish<T> {
    void success(T object);
    void fail(String errorMsg);
}
