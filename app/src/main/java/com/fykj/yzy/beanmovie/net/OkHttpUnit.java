package com.fykj.yzy.beanmovie.net;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pg3 on 2017/7/2.
 */

public class OkHttpUnit {
    private static final String TAG = "OkHttpUnit";

    public static final int WANT_IN_THEATERS=1;  //正在热映
    public static final int WANT_COMING_SOON=2;   //即将上映
    public static final int WANT_TOP_250=3;     //TOP250
    public static final int WANT_US_BOX=4;     //北美榜单

    public static final int WANTS_SUBJECT_INFO=10;    //影片信息
//    public static final int WANTS_SUBJECT_PHOTOS=11;    //影片剧照
//    public static final int WANTS_SUBJECT_REVIEWS=12;    //影片长评
//    public static final int WANTS_SUBJECT_COMMENTS=13;   //影片短评
    public static final int WANTS_CELEBRITY_INFO=14;   //影人信息


    public static final int WANTS_SEARCH_STRING=20;   //搜索字符串
    public static final int WANTS_SEARCH_TAG=21;       //搜索类别


    private static final String DOUBAN_FIRST="https://api.douban.com/v2/movie";
    private static final String DOUBAN_SUBJECT="https://api.douban.com/v2/movie/subject";
    private static final String DOUBAN_CELEBRITY="https://api.douban.com/v2/movie/celebrity";
    private static final String DOUBAN_SEARCH="https://api.douban.com/v2/movie/search?";

    private static final String GET_IN_THEATERS="/in_theaters";
    private static final String GET_COMING_SOON="/coming_soon";
    private static final String GET_TOP_250="/top250";
    private static final String GET_US_BOX="/us_box";


    private static final String GET_SUBJECT_INFO="";
//    private static final String GET_SUBJECT_PHOTOS="/photos";
//    private static final String GET_SUBJECT_REVIEWS="/reviews";
//    private static final String GET_SUBJECT_COMMENTS="/comments";
    private static final String GET_CELEBRITY_INFO="";

    private static final String GET_SEARCH_STRING="q=";
    private static final String GET_SEARCH_TAG="tag=";

    static OkHttpUnit mUnit;


    private OkHttpUnit(){

    }


    public static OkHttpUnit getOkHttpUnit(){
        if (mUnit==null){
            synchronized (OkHttpUnit.class){
                if (mUnit==null){
                    mUnit=new OkHttpUnit();
                }
            }
        }
        return mUnit;
    }




    private String getString(int request,String id){
        StringBuffer reply=new StringBuffer();
        switch (request){
            case WANT_IN_THEATERS:
                reply.append(DOUBAN_FIRST).append(GET_IN_THEATERS);
                break;
            case WANT_COMING_SOON:
                reply.append(DOUBAN_FIRST).append(GET_COMING_SOON);
                break;
            case WANT_TOP_250:
                reply.append(DOUBAN_FIRST).append(GET_TOP_250);
                break;
            case WANT_US_BOX:
                reply.append(DOUBAN_FIRST).append(GET_US_BOX);
                break;
            case WANTS_SUBJECT_INFO:
                reply.append(DOUBAN_SUBJECT).append("/"+id).append(GET_SUBJECT_INFO);
                break;
//            case WANTS_SUBJECT_PHOTOS:
//                reply.append(DOUBAN_SUBJECT).append("/"+id).append(GET_SUBJECT_PHOTOS);
//                break;
//            case WANTS_SUBJECT_REVIEWS:
//                reply.append(DOUBAN_SUBJECT).append("/"+id).append(GET_SUBJECT_REVIEWS);
//                break;
//            case WANTS_SUBJECT_COMMENTS:
//                reply.append(DOUBAN_SUBJECT).append("/"+id).append(GET_SUBJECT_COMMENTS);
//                break;

            case WANTS_CELEBRITY_INFO:
                reply.append(DOUBAN_CELEBRITY).append(GET_CELEBRITY_INFO).append("/"+id);
                break;
            case WANTS_SEARCH_STRING:
                reply.append(DOUBAN_SEARCH).append(GET_SEARCH_STRING).append(id);
                break;
            case WANTS_SEARCH_TAG:
                reply.append(DOUBAN_SEARCH).append(GET_SEARCH_TAG).append(id);
                break;

        }
        return reply.toString();
    }



    public String getResponseString(int request,String id) throws IOException {

        String url=getString(request,id);

        OkHttpClient client=new OkHttpClient();
        Request ask=new Request.Builder().url(url).get().build();
        Response response=client.newCall(ask).execute();
        if (response!=null){
            return response.body().string();
        }else {
            throw new IOException();
        }
    }

}
