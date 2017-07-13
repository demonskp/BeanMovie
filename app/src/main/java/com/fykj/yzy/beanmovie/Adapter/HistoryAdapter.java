package com.fykj.yzy.beanmovie.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.SearchActivity;
import com.fykj.yzy.beanmovie.bean.HistoryBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pg3 on 2017/7/4.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHold> {
//    private static final String TAG = "HistoryAdapter";

    ArrayList<HistoryBean> mData;
    Context mContext;
    Handler mHandler;


    public HistoryAdapter(Context context,ArrayList<HistoryBean> data,Handler handler){
        super();
        mContext=context;
        mData=data;
        mHandler=handler;
    }


    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.history_item,parent,false);
        MyViewHold myViewHold=new MyViewHold(view);

        return myViewHold;
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {

        final HistoryBean historyBean=mData.get(position);

        holder.searchString.setText(historyBean.getSearchString());
        holder.searchTime.setText(historyBean.getSearchTime()); 
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data=new Bundle();
                data.putString("searchString",historyBean.getSearchString());
                Message msg=new Message();
                msg.what=1;
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


//    public void setData(ArrayList<HistoryBean> data){
//        mData=data;
//    }


    public class MyViewHold extends RecyclerView.ViewHolder{

        @BindView(R.id.history_item_searchString)
        TextView searchString;
        @BindView(R.id.history_item_searchTime)
        TextView searchTime;
        @BindView(R.id.history_item_all)
        LinearLayout linearLayout;

        public MyViewHold(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
    
    
}
