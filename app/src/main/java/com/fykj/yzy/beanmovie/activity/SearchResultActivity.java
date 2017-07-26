package com.fykj.yzy.beanmovie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fykj.yzy.beanmovie.Adapter.InTheatersAdapter;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.SearchBean;
import com.fykj.yzy.beanmovie.bean.SubjectsBean;
import com.fykj.yzy.beanmovie.net.DataNet;
import com.fykj.yzy.beanmovie.util.DialogCreaterUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultActivity";

    DataNet dataNet;
    private ArrayList<SubjectsBean> data;
    private static String searchString;
    private Dialog dialog;

    @BindView(R.id.search_result_listView)
    RecyclerView listView;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            SearchBean searchBean= (SearchBean) msg.obj;
            data=new ArrayList<SubjectsBean>(searchBean.getSubjects());
            recycleViewSet();
            dialog.dismiss();
            return false;
        }
    });

    Handler handler1=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String url=((SubjectsBean)msg.obj).getId();
            startNextActivity(url);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        dataNet=DataNet.getDataNew();
        searchString=getIntent().getStringExtra("searchString");
        dialog= DialogCreaterUtil.showprogressdialog(this,-1,false,true);
        dataNet.searchString(handler,searchString);
    }


    private void recycleViewSet(){
        InTheatersAdapter adapter=new InTheatersAdapter(this,data,handler1);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void startNextActivity(String url) {
        Intent intent=new Intent(this,MovieInfoActivity.class);
        intent.putExtra("id",url);
        startActivity(intent);
    }


}
