package com.fykj.yzy.beanmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.bean.SubjectsBean;
import com.fykj.yzy.beanmovie.bean.TOP250Bean;
import com.fykj.yzy.beanmovie.net.DataNet;
import com.fykj.yzy.beanmovie.net.OkHttpUnit;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "MainActivity";


    //yqs
    InTheatersFragment mrecentFragment;
    InTheatersFragment mingFragment;
    InTheatersFragment mtopFragment;

    String recent="recent";
    String ing="ing";
    String top="top";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
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
                Intent intent3=new Intent(this,RegisterActivity.class);
                startActivity(intent3);

        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rb1:
                if(isChecked)
                    showrecent();
                break;
            case R.id.rb2:
                if(isChecked)
                    showing();
                break;
            case R.id.rb3:
                if(isChecked)
                    showtop();
                break;
        }
    }


    @OnClick(R.id.main_nev_btn)
    public void onButtonClick(){
        drawer.openDrawer(GravityCompat.START);
    }



    public  void initView(){
        rb1.setOnCheckedChangeListener(this);
        rb2.setOnCheckedChangeListener(this);
        rb3.setOnCheckedChangeListener(this);

        /**
         * 代码点击事件
         */
        rb2.performClick();
    }


    private void showrecent() {
        
        mrecentFragment = (InTheatersFragment) getSupportFragmentManager().findFragmentByTag(recent);
        if (mrecentFragment == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            mrecentFragment = new InTheatersFragment(InTheatersFragment.COMESOON);
            transaction.replace(R.id.control, mrecentFragment, recent);
            transaction.commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.control,mrecentFragment).commit();
        }
    }
    private void showing() {
        Log.d(TAG, "showing: ");
        mingFragment = (InTheatersFragment) getSupportFragmentManager().findFragmentByTag(ing);
        if (mingFragment == null) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            mingFragment = new InTheatersFragment(InTheatersFragment.INTHEATERS);
            transaction.replace(R.id.control, mingFragment, ing);
            transaction.commit();
        } else {

            getSupportFragmentManager().beginTransaction().show(mingFragment).commit();
        }

    }
    private void showtop() {
        Log.d(TAG, "showtop: ");
        mtopFragment = (InTheatersFragment) getSupportFragmentManager().findFragmentByTag(top);
        if (mtopFragment == null) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            mtopFragment = new InTheatersFragment(InTheatersFragment.TOP250);
            transaction.replace(R.id.control, mtopFragment, top);
            transaction.commit();
        } else {

            getSupportFragmentManager().beginTransaction().show(mtopFragment).commit();
        }
    }


}
