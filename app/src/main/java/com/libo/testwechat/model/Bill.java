package com.libo.testwechat.model;

/**
 * Created by Administrator on 2017/5/15.
 */

public class Bill {


    /**
     * id : 34
     * uid : 1
     * message : 小单20 小双20 大双20
     * result : 15
     * period : null
     * balance : 00300
     * time : 2017-05-15 18:07:49
     */

    private String id;
    private String uid;
    private String message;
    private String result;
    private String period;
    private String balance;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
