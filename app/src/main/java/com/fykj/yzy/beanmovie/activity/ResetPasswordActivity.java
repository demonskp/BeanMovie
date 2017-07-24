package com.fykj.yzy.beanmovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fykj.yzy.beanmovie.util.Code;
import com.fykj.yzy.beanmovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    @BindView(R.id.iv_showCode)
    ImageView iv_showCode;
    //产生的验证码
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        //iv_showCode.setOnClickListener();
    }
    @OnClick(R.id.iv_showCode)
    public void refresh(View view){
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        Log.v(TAG,"realCode"+realCode);
    }
    @OnClick(R.id.nav_top_back)
    public void back(View view){
        finish();
    }
}
