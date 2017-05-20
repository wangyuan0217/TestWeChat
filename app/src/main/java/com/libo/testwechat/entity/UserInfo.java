package com.libo.testwechat.entity;

/**
 * Created by Administrator on 2017/5/8.
 */

public class UserInfo {


    /**
     * id : 1
     * user_name : username
     * user_pwd : 14e1b600b1fd579f47433b88e8d85291
     * billname : 张三
     * balance : 1000
     * limit_profit : 3000
     * limit_loss : 50
     * logic_template_id : 1
     * status : 1
     * time : 2017-05-05 20:40:51
     * token : 0CB5EAED-6BF2-62A9-7782-A52E18A77C02
     * key : 风云会欢迎你
     */

    private String id;
    private String user_name;
    private String user_pwd;
    private String billname;
    private String balance;
    private String limit_profit;
    private String limit_loss;
    private String logic_template_id;
    private String status;
    private String message;
    private String time;
    private String token;
    private String key;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getLimit_profit() {
        return limit_profit;
    }

    public void setLimit_profit(String limit_profit) {
        this.limit_profit = limit_profit;
    }

    public String getLimit_loss() {
        return limit_loss;
    }

    public void setLimit_loss(String limit_loss) {
        this.limit_loss = limit_loss;
    }

    public String getLogic_template_id() {
        return logic_template_id;
    }

    public void setLogic_template_id(String logic_template_id) {
        this.logic_template_id = logic_template_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
