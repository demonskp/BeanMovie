package com.fykj.yzy.beanmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.bean.User;
import com.fykj.yzy.beanmovie.bean.UserHolder;
import com.fykj.yzy.beanmovie.fragment.InTheatersFragment;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.fragment.TOP250Fragment;
import com.fykj.yzy.beanmovie.util.NullUtil;
import com.fykj.yzy.beanmovie.util.PreferencesUtils;
import com.fykj.yzy.beanmovie.util.ToastUtil;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "MainActivity";


    private User nowUser;
    private DBManage dbManage;
    //友盟分享的监听器
    private UMShareListener shareListener;


    //yqs
    InTheatersFragment inTheatersFragment;
    InTheatersFragment comingSoonFragment;
    TOP250Fragment top250Fragment;

    private static final String INTHEATERS="recent";
    private static final String COMINGSOON="ing";
    private static final String TOP250="top";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @OnClick(R.id.search)
    void search(){
        Intent intent=new Intent(this,SearchActivity.class);
        startActivity(intent);
    };

    @BindView(R.id.rb1)
    RadioButton rb1;

    @BindView(R.id.rb2)
    RadioButton rb2;

    @BindView(R.id.rb3)
    RadioButton rb3;

    TextView nameText;
    TextView mottoText;
    ImageView headerImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nowUser= UserHolder.getUserHolder().getUser();
        ButterKnife.bind(this);

        dbManage=DBManage.getDBManage(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nowUser= UserHolder.getUserHolder().getUser();
        initDrawUserInfo();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_manage:
                Intent intent1=new Intent(this,ModifyUserInfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_about:
                Intent intent2=new Intent(this,AboutusActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_collection:
                if (dbManage.findAllCollection()==null){
                    Toast.makeText(this,"暂时没有收藏",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent3=new Intent(this,CollectionActivity.class);
                    startActivity(intent3);
                }
                break;
            case R.id.nav_switch:
                changUser();
                break;
            case R.id.nav_share:
                ShareWeb("R.mipmap.img_test.webp");
                break;

        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changUser() {
        UserHolder.getUserHolder().setUser(new User());
        PreferencesUtils.clear(this);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rb1:
                if(isChecked)
                    showInTheater();
                break;
            case R.id.rb2:
                if(isChecked)
                    showComingSoon();
                break;
            case R.id.rb3:
                if(isChecked)
                    showTOP250();
                break;
        }
    }
    private void ShareImage(String image,String thumb){
        UMImage pic = new UMImage(this,image);
        pic.setThumb(new UMImage(this,thumb));
        new ShareAction(this)
                .withMedia(pic)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .withText("分享链接").share();}




    private void ShareText(){
        new ShareAction(this).withText("欢迎下载友盟")
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    private void ShareWeb(String thumb_img){
        UMImage thumb = new UMImage(this,thumb_img);
        UMWeb web = new UMWeb("https://github.com/demonskp/BeanMovie");
        web.setThumb(thumb);
        web.setDescription("豆影-带你打开电影世界");
        web.setTitle("分享应用-豆影");
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .share();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }


    @OnClick(R.id.main_nev_btn)
    public void onButtonClick(){
        drawer.openDrawer(GravityCompat.START);
    }



    public  void initView(){

        initDrawUserInfo();

        navigationView.setNavigationItemSelectedListener(this);

        rb1.setOnCheckedChangeListener(this);
        rb2.setOnCheckedChangeListener(this);
        rb3.setOnCheckedChangeListener(this);
        rb2.performClick(); //预点击


        //初始化友盟分享的监听
        shareListener=new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtil.showLongToast(MainActivity.this,throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        };

    }

    private void initDrawUserInfo() {
        View headerLayout=navigationView.getHeaderView(0);
        nameText= (TextView) headerLayout.findViewById(R.id.user_name);
        mottoText= (TextView) headerLayout.findViewById(R.id.user_motto);
        headerImage= (ImageView) headerLayout.findViewById(R.id.user_header);
        nowUser= UserHolder.getUserHolder().getUser();
        if (nowUser.getName()!=null){
            nameText.setText(nowUser.getName());
        }
        if (nowUser.getMotto()!=null){
            mottoText.setText(nowUser.getMotto());
        }
        Picasso.with(this).load(R.mipmap.ic_movie_logo).into(headerImage);
    }


    private void showInTheater() {
        
        inTheatersFragment = (InTheatersFragment) getSupportFragmentManager().findFragmentByTag(INTHEATERS);
        if (inTheatersFragment == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            inTheatersFragment = new InTheatersFragment(InTheatersFragment.COMESOON);
            transaction.replace(R.id.control, inTheatersFragment, INTHEATERS);
            transaction.commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.control,inTheatersFragment).commit();
        }
    }
    private void showComingSoon() {
        Log.d(TAG, "showComingSoon: ");
        comingSoonFragment = (InTheatersFragment) getSupportFragmentManager().findFragmentByTag(COMINGSOON);
        if (comingSoonFragment == null) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            comingSoonFragment = new InTheatersFragment(InTheatersFragment.INTHEATERS);
            transaction.replace(R.id.control, comingSoonFragment, COMINGSOON);
            transaction.commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(comingSoonFragment).commit();
        }

    }
    private void showTOP250() {
        Log.d(TAG, "showTOP250: ");
        top250Fragment = (TOP250Fragment) getSupportFragmentManager().findFragmentByTag(TOP250);
        if (top250Fragment == null) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            top250Fragment = new TOP250Fragment();
            transaction.replace(R.id.control, top250Fragment, TOP250);
            transaction.commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(top250Fragment).commit();
        }
    }


}
