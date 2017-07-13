package com.fykj.yzy.beanmovie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pg3 on 2017/7/4.
 */
@Entity
public class HistoryBean {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String searchString;
    @Property
    private String searchTime;


    public HistoryBean(String searchString,String searchTime){
        this.searchString=searchString;
        this.searchTime=searchTime;
    }


    @Generated(hash = 621152352)
    public HistoryBean(Long id, String searchString, String searchTime) {
        this.id = id;
        this.searchString = searchString;
        this.searchTime = searchTime;
    }


    @Generated(hash = 48590348)
    public HistoryBean() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getSearchString() {
        return this.searchString;
    }


    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }


    public String getSearchTime() {
        return this.searchTime;
    }


    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }


}
