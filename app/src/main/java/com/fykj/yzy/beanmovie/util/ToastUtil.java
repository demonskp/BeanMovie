package com.fykj.yzy.beanmovie.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 易镇艺 on 2017/7/26.
 */

public class ToastUtil {
    public static void showLongToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
