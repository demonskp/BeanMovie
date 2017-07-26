package com.fykj.yzy.beanmovie.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.CollectionBean;
import com.fykj.yzy.beanmovie.bean.SubjectsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pg3 on 2017/7/7.
 */

public class InTheatersAdapter extends RecyclerView.Adapter<InTheatersAdapter.MyHolder> {
    private static final String TAG = "InTheatersAdapter";

    private static final String[] ratingList={"好评如潮","多半好评","中等水平","褒贬不一","差评较多"};
    private DBManage dbManage;

    private Context context;
    private ArrayList<SubjectsBean> data;
    private Handler handler;

    public InTheatersAdapter(Context context, ArrayList<SubjectsBean> data, Handler handler) {
        this.context=context;
        this.data=data;
        this.handler=handler;
        dbManage=DBManage.getDBManage(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.intheaters_item,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final SubjectsBean subjectsBean=data.get(position);
        String directorName=getDirectorName(subjectsBean.getDirectors());
        final String castsName=getCastsName(subjectsBean.getCasts());
        String rating=getRating(subjectsBean.getRating());



        Picasso.with(context).load(subjectsBean.getImages().getLarge()).into(holder.imageView);
        holder.titleText.setText(subjectsBean.getTitle());
        holder.directorsText.setText("导演："+directorName);
        holder.castsText.setText("演员："+castsName);
        holder.ratingText.setText(rating+"  "+subjectsBean.getRating().getAverage());




        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+subjectsBean.getTitle());
                startNextActivity(subjectsBean);
            }
        });

        Long id=Long.valueOf(subjectsBean.getId());
        List<CollectionBean> list=dbManage.findCollection(id);
        if (list.size()!=0){
            holder.ischecked=true;
            holder.collectionButton.setBackgroundResource(R.drawable.ic_collection_yes);
        }else {
            holder.ischecked=false;
            holder.collectionButton.setBackgroundResource(R.drawable.ic_collection_no);
        }

        holder.collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.ischecked){
                    holder.ischecked=true;
                    Log.d(TAG, "onClick: "+holder.ischecked);
                    holder.collectionButton.setBackgroundResource(R.drawable.ic_collection_yes);
                    saveSubjects(subjectsBean);
                }else {
                    holder.ischecked=false;
                    Log.d(TAG, "onClick: "+holder.ischecked);
                    holder.collectionButton.setBackgroundResource(R.drawable.ic_collection_no);
                    deleteSubjects(subjectsBean);
                }
            }
        });


    }

    private void startNextActivity(SubjectsBean subjectsBean) {
        Message message=new Message();
        message.what=1;
        message.obj=subjectsBean;
        handler.sendMessage(message);

    }

    private void deleteSubjects(SubjectsBean subjectsBean) {
       dbManage.deleteCollection(Long.valueOf(subjectsBean.getId()));
    }

    private void saveSubjects(SubjectsBean subjectsBean) {

        long id=Long.valueOf(subjectsBean.getId());
        CollectionBean c=new CollectionBean(id,subjectsBean.getImages().getLarge(),subjectsBean.getTitle(),getDayAndMouth());
        dbManage.insertCollection(c);
    }

    private String getDayAndMouth() {
        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.DAY_OF_MONTH);
        int m = c.get(Calendar.MONTH);
        String result=new String(m+"月"+d+"日");
        return result;
    }

    private String getRating(SubjectsBean.RatingBean rating) {
        Double rat=rating.getAverage();
        if (rat<0.1) return "暂无评分";
        if (rat<4) return ratingList[4];
        if (rat<6) return ratingList[3];
        if (rat<8) return ratingList[2];
        if (rat<9) return ratingList[1];
        if (rat<10.1) return ratingList[0];
        
        return "暂无评分";
        
    }

    private String getCastsName(List<SubjectsBean.CastsBean> casts) {
        int size=casts.size();
        if (size==0){
            return "没有演员";
        }

        StringBuffer result=new StringBuffer();
        result.append(casts.get(0).getName());

        if (size==1){
            return result.toString();
        }

        for (int i=1;i<size;i++){
            result.append("/"+casts.get(i).getName());
        }
        return result.toString();
    }

    private String getDirectorName(List<SubjectsBean.DirectorsBean> directors) {
        int size=directors.size();

        StringBuffer result=new StringBuffer();
        result.append(directors.get(0).getName());

        if (size==1){
            return result.toString();
        }

        for (int i=1;i<size;i++){
            result.append(directors.get(i).getName());
        }
        return result.toString();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        Boolean ischecked=false;

        @BindView(R.id.item_list)
        LinearLayout linearLayout;

        @BindView(R.id.item_imag)
        ImageView imageView;
        @BindView(R.id.item_title)
        TextView titleText;
        @BindView(R.id.item_rating)
        TextView ratingText;
        @BindView(R.id.item_directors)
        TextView directorsText;
        @BindView(R.id.item_casts)
        TextView castsText;
        @BindView(R.id.item_collection)
        Button collectionButton;

        public MyHolder(View itemView) {
            super(itemView);
//            imageView= (ImageView) itemView.findViewById(R.id.item_imag);
//            titleText= (TextView) itemView.findViewById(R.id.item_title);
//            ratingText= (TextView) itemView.findViewById(R.id.item_rating);
//            directorsText= (TextView) itemView.findViewById(R.id.item_directors);
//            castsText= (TextView) itemView.findViewById(R.id.item_casts);
//            collectionButton= (RadioButton) itemView.findViewById(R.id.item_collection);

            ButterKnife.bind(this,itemView);
        }
    }

}
