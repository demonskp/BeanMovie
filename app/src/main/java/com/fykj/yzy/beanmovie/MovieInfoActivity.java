package com.fykj.yzy.beanmovie;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fykj.yzy.beanmovie.bean.SubjectInfoBean;
import com.fykj.yzy.beanmovie.net.DataNet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfoActivity extends AppCompatActivity {

    private static final String TAG = "MovieInfoActivity";
    @BindView(R.id.movie_info_webview)
    WebView webView;
    @BindView(R.id.movie_info_pro)
    ProgressBar pg1;


    private String id;
    private String url;
    private DataNet dataNet;
    private SubjectInfoBean data;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            data= (SubjectInfoBean) msg.obj;
            url=data.getMobile_url();
            Log.d(TAG, "handleMessage: "+url);
            webView.loadUrl(url);
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        dataNet=DataNet.getDataNew();
        id=getIntent().getStringExtra("id");
        ButterKnife.bind(this);
        dataNet.searchMovieInfo(handler,id);
        webView.getSettings().setJavaScriptEnabled(true);//开启JS
        webView.getSettings().setBlockNetworkImage(false);//开启图片加载
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("www.douban.com")){
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });

    }


}
