package com.fykj.yzy.beanmovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.fykj.yzy.beanmovie.Adapter.InTheatersAdapter;
import com.fykj.yzy.beanmovie.bean.SubjectsBean;
import com.fykj.yzy.beanmovie.net.DataNet;
import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InTheatersFragment extends Fragment {
    private static final String TAG = "InTheatersFragment";

    public static final String INTHEATERS="INTHEATERS";
    public static final String COMESOON="COMESOON";
    public static final String TOP250="TOP250";

    private String type;

    View view;
    ArrayList<SubjectsBean> data;
    InTheatersAdapter adapter;

    @BindView(R.id.intheather_recycleView)
    RecyclerView recyclerView;


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String url=((SubjectsBean)msg.obj).getId();
            startNextActivity(url);
            return false;
        }
    });

    private void startNextActivity(String url) {
        Intent intent=new Intent(getContext(),MovieInfoActivity.class);
        intent.putExtra("id",url);
        Log.d(TAG, "startNextActivity: "+url);
        startActivity(intent);
    }


    public InTheatersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }

    private void init() {
        DataNet net=DataNet.getDataNew();
        data=new ArrayList<SubjectsBean>(net.getInTheatersBean().getSubjects());

        switch (type){
            case INTHEATERS:
                data=new ArrayList<SubjectsBean>(net.getInTheatersBean().getSubjects());
                break;
            case COMESOON:
                data=new ArrayList<SubjectsBean>(net.getComeSoonBean().getSubjects());
                break;
            case TOP250:
                data=new ArrayList<SubjectsBean>(net.getTop250Bean().getSubjects());
                break;
        }
        adapter=new InTheatersAdapter(getActivity(),data,handler);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_in_theaters,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        Log.d(TAG, "onStart: ");
        super.onStart();
    }
}
