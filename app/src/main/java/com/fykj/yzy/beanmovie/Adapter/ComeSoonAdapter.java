package com.fykj.yzy.beanmovie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.ComeSoonBean;

import java.util.List;

/**
 * Created by 易镇艺 on 2017/7/26.
 */

public class ComeSoonAdapter extends RecyclerView.Adapter<ComeSoonAdapter.ComeHolder>{
    private static final String TAG = "ComeSoonAdapter";

    private Context context;
    private List<ComeSoonBean> data;


    public ComeSoonAdapter(Context context, List<ComeSoonBean> data){
        this.context=context;
        this.data=data;
    }


    @Override
    public ComeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comesoon_item,parent,false);
        ComeHolder holder=new ComeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class ComeHolder extends RecyclerView.ViewHolder{

    public ComeHolder(View itemView) {
        super(itemView);
    }
}
}
