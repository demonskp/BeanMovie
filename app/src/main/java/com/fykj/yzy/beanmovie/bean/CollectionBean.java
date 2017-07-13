package com.fykj.yzy.beanmovie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pg3 on 2017/7/9.
 */


@Entity
public class CollectionBean {
    @Id(autoincrement = true)
    Long id;
    @Property
    String URL;//图片的URL
    @Property
    String title;
    @Property
    String data;
    @Generated(hash = 651369768)
    public CollectionBean(Long id, String URL, String title, String data) {
        this.id = id;
        this.URL = URL;
        this.title = title;
        this.data = data;
    }
    @Generated(hash = 1423617684)
    public CollectionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getURL() {
        return this.URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }


}
