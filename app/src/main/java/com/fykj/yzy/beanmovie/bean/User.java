package com.fykj.yzy.beanmovie.bean;

import android.graphics.Bitmap;
import android.media.Image;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pg3 on 2017/7/5.
 */
@Entity
public class User {
    @Id
    private Long id;
    @Property
    private String passWorld;
    @Property
    private String name;
    @Property
    private String motto; //座右铭
    @Generated(hash = 1310918850)
    public User(Long id, String passWorld, String name, String motto) {
        this.id = id;
        this.passWorld = passWorld;
        this.name = name;
        this.motto = motto;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPassWorld() {
        return this.passWorld;
    }
    public void setPassWorld(String passWorld) {
        this.passWorld = passWorld;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMotto() {
        return this.motto;
    }
    public void setMotto(String motto) {
        this.motto = motto;
    }

    
}
