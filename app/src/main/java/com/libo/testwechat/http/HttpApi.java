package com.libo.testwechat.http;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;

/**
 * @author 王元_Trump
 * @desc 基本的网络请求的封装
 * @time 2016/6/13 16:35
 */
public class HttpApi {

    private static volatile HttpApi instance;

    public HttpApi() {
    }

    public static HttpApi getInstance() {
        if (instance == null)
            synchronized (HttpApi.class) {
                instance = new HttpApi();
            }
        return instance;
    }

    /**
     * 无参get
     *
     * @param url
     * @param callback
     */
    public void get(String url, MyCallback callback) {

        if (callback != null) {
            new RxVolley.Builder()
                    .url(url) //接口地址
                    .httpMethod(RxVolley.Method.GET)
                    //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                    .cacheTime(0)
                    //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                    .contentType(RxVolley.ContentType.FORM)
                    .callback(callback) //响应回调
                    .encoding("UTF-8") //编码格式，默认为utf-8
                    .doTask();  //执行请求操作
        }
    }

    /**
     * 有参get
     *
     * @param url
     * @param params
     * @param callback
     */
    public void get(String url, HttpParams params, MyCallback callback) {

        if (callback != null) {
            new RxVolley.Builder()
                    .url(url) //接口地址
                    .httpMethod(RxVolley.Method.GET)
                    //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                    .cacheTime(0)
                    //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                    .contentType(RxVolley.ContentType.FORM)
                    .params(params) //上文创建的HttpParams请求参数集
                    .callback(callback) //响应回调
                    .encoding("UTF-8") //编码格式，默认为utf-8
                    .doTask();  //执行请求操作
//                  .getResult();  // 使用getResult()来返回RxJava数据类型
        }
    }

    /**
     * post
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, HttpParams params, MyCallback callback) {

        if (callback != null) {
            new RxVolley.Builder()
                    .url(url) //接口地址
                    .httpMethod(RxVolley.Method.POST)
                    //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                    .cacheTime(0)
                    //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                    .params(params) //上文创建的HttpParams请求参数集
                    .contentType(RxVolley.ContentType.FORM)
                    .callback(callback) //响应回调
                    .encoding("UTF-8") //编码格式，默认为utf-8
                    .doTask();  //执行请求操作
        }
    }

}
