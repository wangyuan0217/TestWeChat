package com.libo.testwechat.http;

import com.kymjs.rxvolley.client.HttpParams;
import com.libo.testwechat.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Apis {

    //    public static final String PRE_URL = "http://192.168.1.110/ar/app/";
    public static final String PRE_URL = "http://www.libokeji.cn/ar/app/";
    public static final String PARAM = "params";


    private static volatile Apis instance;


    private Apis() {
    }

    public static Apis getInstance() {
        if (instance == null)
            synchronized (Apis.class) {
                instance = new Apis();
            }
        return instance;
    }

    public void login(String type, String name, String pass, MyCallback callback) {
        String url = PRE_URL + "Index/Login";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("user_name", name);
            jsonObject.put("user_pwd", MD5.encryption(pass));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void start(String uid, MyCallback callback) {
        String url = PRE_URL + "UserCenter/request_start";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void end(String uid, String result, MyCallback callback) {
        String url = PRE_URL + "UserCenter/request_end";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("result", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void setOn(String uid, MyCallback callback) {
        String url = PRE_URL + "UserCenter/start";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void setOff(String uid, MyCallback callback) {
        String url = PRE_URL + "UserCenter/end";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void postBill(String uid, String bill, MyCallback callback) {
        String url = PRE_URL + "UserCenter/request_bill";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("balance", bill);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().post(url, params, callback);
    }

    public void getUserInfo(String uid, MyCallback callback) {
        String url = PRE_URL + "UserCenter/User_info";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpParams params = new HttpParams();
        params.put(PARAM, jsonObject.toString());

        new HttpApi().get(url, params, callback);
    }
}
