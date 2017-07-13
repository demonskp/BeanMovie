package com.fykj.yzy.beanmovie.net;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.fykj.yzy.beanmovie.bean.CelebrityInfoBean;
import com.fykj.yzy.beanmovie.bean.ComeSoonBean;
import com.fykj.yzy.beanmovie.bean.InTheatersBean;
import com.fykj.yzy.beanmovie.bean.SearchBean;
import com.fykj.yzy.beanmovie.bean.SubjectInfoBean;
import com.fykj.yzy.beanmovie.bean.TOP250Bean;
import com.fykj.yzy.beanmovie.bean.USBoxBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pg3 on 2017/7/3.
 */

public class DataNet {
    private static final String TAG = "DataNet";
    private static DataNet dataNet;

    private ExecutorService threadPool=Executors.newFixedThreadPool(5);
    private OkHttpUnit httpUnit;

    private InTheatersBean inTheatersBean;
    private ComeSoonBean comeSoonBean;
    private TOP250Bean top250Bean;
    private USBoxBean usBoxBean;

    Runnable runInTheater=new Runnable() {
        @Override
        public void run() {
            try {
                String result=httpUnit.getResponseString(OkHttpUnit.WANT_IN_THEATERS,"");
                Gson gson=new Gson();
                inTheatersBean=gson.fromJson(result,InTheatersBean.class);
                Log.d(TAG, "runInTheater: "+inTheatersBean.getSubjects().get(0).getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runComeSoon=new Runnable() {
        @Override
        public void run() {
            try {
                String result=httpUnit.getResponseString(OkHttpUnit.WANT_COMING_SOON,"");
                Gson gson=new Gson();
                comeSoonBean=gson.fromJson(result,ComeSoonBean.class);
                Log.d(TAG, "runComeSoon: "+comeSoonBean.getSubjects().get(0).getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runTOP250=new Runnable() {
        @Override
        public void run() {
            try {
                String result=httpUnit.getResponseString(OkHttpUnit.WANT_TOP_250,"");
                Gson gson=new Gson();
                top250Bean=gson.fromJson(result,TOP250Bean.class);
                Log.d(TAG, "runTOP250: "+top250Bean.getSubjects().get(0).getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runUSBox=new Runnable() {
        @Override
        public void run() {
            try {
                String result=httpUnit.getResponseString(OkHttpUnit.WANT_US_BOX,"");
                Gson gson=new Gson();
                usBoxBean=gson.fromJson(result,USBoxBean.class);
                Log.d(TAG, "runUSBox: "+usBoxBean.getSubjects().get(0).getSubject().getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private  DataNet(){};

    public static DataNet getDataNew(){
        if(dataNet==null){
            synchronized (DataNet.class){
                if (dataNet==null){
                    dataNet=new DataNet();
                }
            }
        }
        return dataNet;
    }




    public void init(){
        httpUnit=OkHttpUnit.getOkHttpUnit();
        threadPool.execute(runComeSoon);
        threadPool.execute(runInTheater);
        threadPool.execute(runTOP250);
        threadPool.execute(runUSBox);
    }


    public void searchString(final Handler handler, final String data){


        Runnable runSearchString=new Runnable() {
            @Override
            public void run() {
                try {
                    String result=httpUnit.getResponseString(OkHttpUnit.WANTS_SEARCH_STRING,data);
                    Gson gson=new Gson();
                    SearchBean searBean=gson.fromJson(result,SearchBean.class);
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=searBean;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        threadPool.execute(runSearchString);

    }

    public void searchTag(final Handler handler,final String tag){

        Runnable runSearchString=new Runnable() {
            @Override
            public void run() {
                try {
                    String result=httpUnit.getResponseString(OkHttpUnit.WANTS_SEARCH_TAG,tag);
                    Gson gson=new Gson();
                    SearchBean searBean=gson.fromJson(result,SearchBean.class);
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=searBean;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(runSearchString);
    }

    public void searchMovieInfo(final Handler handler,final String id){
        Runnable runSearchMovie=new Runnable() {
            @Override
            public void run() {
                try {
                    String result=httpUnit.getResponseString(OkHttpUnit.WANTS_SUBJECT_INFO,id);
                    Gson gson=new Gson();
                    SubjectInfoBean movieInfo=gson.fromJson(result,SubjectInfoBean.class);
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=movieInfo;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(runSearchMovie);
    }


    public void searchCelebrityInfo(final Handler handler,final String id){
        Runnable runSearchCelebrity=new Runnable() {
            @Override
            public void run() {
                try {
                    String result=httpUnit.getResponseString(OkHttpUnit.WANTS_CELEBRITY_INFO,id);
                    Gson gson=new Gson();
                    CelebrityInfoBean celebrityInfo=gson.fromJson(result,CelebrityInfoBean.class);
                    Message msg=new Message();
                    msg.what=1;
                    msg.obj=celebrityInfo;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(runSearchCelebrity);
    }


    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public InTheatersBean getInTheatersBean() {
        return inTheatersBean;
    }

    public void setInTheatersBean(InTheatersBean inTheatersBean) {
        this.inTheatersBean = inTheatersBean;
    }

    public ComeSoonBean getComeSoonBean() {
        return comeSoonBean;
    }

    public void setComeSoonBean(ComeSoonBean comeSoonBean) {
        this.comeSoonBean = comeSoonBean;
    }

    public TOP250Bean getTop250Bean() {
        return top250Bean;
    }

    public void setTop250Bean(TOP250Bean top250Bean) {
        this.top250Bean = top250Bean;
    }

    public USBoxBean getUsBoxBean() {
        return usBoxBean;
    }

    public void setUsBoxBean(USBoxBean usBoxBean) {
        this.usBoxBean = usBoxBean;
    }
}
