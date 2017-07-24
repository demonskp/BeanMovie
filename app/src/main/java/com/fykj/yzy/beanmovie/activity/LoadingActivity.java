package com.fykj.yzy.beanmovie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.User;
import com.fykj.yzy.beanmovie.bean.UserHolder;
import com.fykj.yzy.beanmovie.net.DataNet;
import com.fykj.yzy.beanmovie.util.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";
    private static int COUNT=0;
    private static long userId;
    private static String userPass;

    @BindView(R.id.loading_img)
    ImageView imageView;

    private DataNet dataNet;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            COUNT++;
            Log.d(TAG, "handleMessage: "+COUNT);
            if (dataNet.getComeSoonBean()!=null&&dataNet.getInTheatersBean()!=null&&dataNet.getTop250Bean()!=null){
                jumpTo();
            }else {
                handler.sendEmptyMessageDelayed(1,200);
                if (COUNT==40){
                    dataNet.init();
                }
                if (COUNT==80){
                    makeToast("网络连接超时");
                    dataNet.init();
                }

            }

            return false;
        }
    });

    private void makeToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        dataNet=DataNet.getDataNew();
        dataNet.init();
        handler.sendEmptyMessageDelayed(1,200);
        ButterKnife.bind(this);

        userId= PreferencesUtils.getLong(this,PreferencesUtils.USER_ID,0);
        userPass=PreferencesUtils.getString(this,PreferencesUtils.USER_PASS,"");

        loadingAnimation();
    }

    private void loadingAnimation() {
        RotateAnimation loading=new RotateAnimation(0f,359f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        loading.setDuration(1500);
        loading.setInterpolator(new LinearInterpolator());
        loading.setRepeatMode(Animation.RESTART);
        loading.setRepeatCount(-1);
        imageView.startAnimation(loading);
    }

    void jumpTo(){
        if (DBManage.getDBManage(this).login(userId,userPass)){
            User user=DBManage.getDBManage(this).findUser(userId).get(0);
            UserHolder.getUserHolder().setUser(user);
            startMain();
        }else {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        }
        finish();
    }


    void startMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
