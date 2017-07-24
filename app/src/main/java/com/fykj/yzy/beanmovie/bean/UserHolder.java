package com.fykj.yzy.beanmovie.bean;

/**
 * Created by pg3 on 2017/7/9.
 */

public class UserHolder {
    private static UserHolder instance;
    private  User user;

    private UserHolder(){

    }

    public static UserHolder getUserHolder(){
        if (instance==null){
            instance=new UserHolder();
        }
        return instance;
    }


    public  User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
