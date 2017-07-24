package com.fykj.yzy.beanmovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fykj.yzy.beanmovie.Adapter.CollectionAdapter;
import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.CollectionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity {

    private List<CollectionBean> data=new ArrayList<>();
    private DBManage dbManage;
    private CollectionAdapter adapter;

    @BindView(R.id.collection_recycleList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        initData();
        initListView();
    }

    private void initListView() {

    }

    private void initData() {

        dbManage=DBManage.getDBManage(this);
        data=dbManage.findAllCollection();
        adapter=new CollectionAdapter(this,data);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
}
