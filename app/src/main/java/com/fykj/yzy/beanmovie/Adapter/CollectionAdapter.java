package com.fykj.yzy.beanmovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.activity.MovieInfoActivity;
import com.fykj.yzy.beanmovie.bean.CollectionBean;
import com.fykj.yzy.beanmovie.util.DialogCreaterUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 易镇艺 on 2017/7/24.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyHolder> {
    private static final String TAG = "CollectionAdapter";

    private List<CollectionBean> data;
    private Context context;
    private DBManage dbmanage;
    private Handler handler;


    public CollectionAdapter(Context context,List<CollectionBean> data,Handler handler){
        this.context=context;
        this.data=data;
        this.handler=handler;
        dbmanage=DBManage.getDBManage(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.collection_item,null,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final CollectionBean bean=data.get(position);
        holder.movieTitle.setText(bean.getTitle());
        holder.collectionTime.setText(bean.getData());
        Picasso.with(context).load(bean.getURL()).into(holder.movieImage);

        holder.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MovieInfoActivity.class);
                intent.putExtra("id",bean.getId()+"");
                Log.d(TAG, "onClick: "+bean.getId()+"");
                context.startActivity(intent);
            }
        });

        holder.movieLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Message msg=new Message();
                Bundle data=new Bundle();
                data.putLong("id",bean.getId());
                data.putInt("position",position);
                msg.setData(data);
                handler.sendMessage(msg);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.collection_item_all)
        LinearLayout movieLayout;
        @BindView(R.id.collection_item_img)
        ImageView movieImage;
        @BindView(R.id.collection_item_title)
        TextView movieTitle;
        @BindView(R.id.collection_item_time)
        TextView collectionTime;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
