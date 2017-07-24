package com.fykj.yzy.beanmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TOP250Fragment extends Fragment {
    private static final String TAG = "TOP250Fragment";
    private View view;
    FragmentManager fm;
    InTheatersFragment fragment1;
    InTheatersFragment fragment2;

    private List<Fragment> fragmentList;
    private int currIndex=0;//当前页卡编号
    private int bmpW;//横线图片宽度

    @BindView(R.id.fragment_top250_us)
    TextView usBtn;
    @BindView(R.id.fragment_top250_douban)
    TextView doubanBtn;
    @BindView(R.id.fragment_top250_img)
    LinearLayout moveImag;
    @BindView(R.id.fragment_top250_viewpage)
    ViewPager viewPage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view==null){
            view=inflater.inflate(R.layout.fragment_top250,container,false);
            ButterKnife.bind(this,view);
            initView();
            Log.d(TAG, "onCreateView: "+(fragmentList==null));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        initView();
    }

    private void initView() {
        fragmentList=new ArrayList<>();
        fragment1=new InTheatersFragment(InTheatersFragment.COMESOON);
        fragment2=new InTheatersFragment(InTheatersFragment.TOP250);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);



        fm=getChildFragmentManager();
        viewPage.setAdapter(new MyPagerAdapter(fm));

        DisplayMetrics metrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bmpW = metrics.widthPixels/2;
        Log.d(TAG, "initView: "+bmpW);


        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = new TranslateAnimation(currIndex*bmpW,position*bmpW,0,0);//平移动画
                currIndex = position;
                animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
                animation.setDuration(200);//动画持续时间0.2秒
                moveImag.startAnimation(animation);//是用ImageView来显示动画的

                switch (position){
                    case 0:
                        usBtn.setTextColor(getResources().getColor(R.color.color_3AA94C));
                        doubanBtn.setTextColor(getResources().getColor(R.color.color_FFE1FF));
                        break;
                    case 1:
                        usBtn.setTextColor(getResources().getColor(R.color.color_FFE1FF));
                        doubanBtn.setTextColor(getResources().getColor(R.color.color_3AA94C));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick(R.id.fragment_top250_us)
    public void clickUS(){
        viewPage.setCurrentItem(0);
    }
    @OnClick(R.id.fragment_top250_douban)
    public void clickDouban(){
        viewPage.setCurrentItem(1);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter{


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
