package com.libo.testwechat.http;

import android.graphics.Bitmap;

import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author 王元_Trump
 * @desc 网络请求回调的封装
 * @time 2016/6/13 17:05
 */
public abstract class MyCallback extends HttpCallback {

    public MyCallback() {
        super();
    }

    @Override
    public void onPreStart() {
        super.onPreStart();
    }

    @Override
    public void onPreHttp() {
        super.onPreHttp();
    }

    @Override
    public void onSuccessInAsync(byte[] t) {
        super.onSuccessInAsync(t);
    }

    @Override
    public void onSuccess(String content) {
        super.onSuccess(content);
        try {
            JSONObject job = new JSONObject(content);
            int state = job.getInt("code");
            if (state == 200) {
                responeData(job.optString("data"), job);
            } else {
                responeDataFail(state, job.optString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            responeDataFail(-1, "");
        }
    }

    @Override
    public void onSuccess(Map<String, String> headers, byte[] t) {
        super.onSuccess(headers, t);
    }

    @Override
    public void onFailure(int errorNo, String strMsg) {
        super.onFailure(errorNo, strMsg);
        responeDataFail(errorNo, strMsg);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onSuccess(Map<String, String> headers, Bitmap bitmap) {
        super.onSuccess(headers, bitmap);
    }


    public abstract void responeData(String body, JSONObject json);

    public abstract void responeDataFail(int responseStatus, String errMsg);

}
