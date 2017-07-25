package com.fykj.yzy.beanmovie.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.fykj.yzy.beanmovie.Adapter.CollectionAdapter;
import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.CollectionBean;
import com.fykj.yzy.beanmovie.util.DialogCreaterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity {
    private static final String TAG = "CollectionActivity";

    private List<CollectionBean> data=new ArrayList<>();
    private DBManage dbManage;
    private CollectionAdapter adapter;

    @BindView(R.id.collection_recycleList)
    RecyclerView recyclerView;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data=msg.getData();
            showDialog(data.getLong("id"),data.getInt("position"));
            return false;
        }
    });

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
        adapter=new CollectionAdapter(this,data,handler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }

    private void showDialog(final long id, final int position) {
        DialogCreaterUtil.createConfirmDialog(this, "确定删除该收藏？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManage.deleteCollection(id);
                data.remove(position);
                adapter.notifyDataSetChanged();
                if (data.size()==0){
                    finish();
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
