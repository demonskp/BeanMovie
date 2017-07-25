package com.fykj.yzy.beanmovie.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fykj.yzy.beanmovie.R;
import com.squareup.picasso.Picasso;


/**
 * Created by YYB on 2016/7/30 0030.
 */
public class DialogCreaterUtil {


    public static Dialog showListDialogs(Context context, int title, int shares,int message, final View.OnClickListener one,final View.OnClickListener two) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dlg = builder.create();
        dlg.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_list_item);
        TextView titles=(TextView)window.findViewById(R.id.dialog_tip);

        TextView share= (TextView) window.findViewById(R.id.dialog_share);
        TextView formessage= (TextView) window.findViewById(R.id.dialog_formesgg);

        titles.setText(title);
        share.setText(shares);
        formessage.setText(message);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != one) {
                    one.onClick(v);
                }
                dlg.dismiss();
            }
        });
      formessage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (null!=two){
                  two.onClick(v);
              }
              dlg.dismiss();
          }
      });



        return dlg;
    }
    public static Dialog createConfirmDialog(Context context, String msg,final View.OnClickListener okListener,final View.OnClickListener cancleListener)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dlg = builder.create();
        dlg.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_confirm);
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        TextView tittleTextView = (TextView) window.findViewById(R.id.confirmdlg_tittle);
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        tittleTextView.setText(msg);

            cancel.setVisibility(View.VISIBLE);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }
                dlg.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cancleListener != null) {
                    cancleListener.onClick(v);
                }
                dlg.cancel();
            }
        });
        return dlg;
    }

    /**
     *
     * @param context
     * @param msg   信息
     * @param okListener
     * @param cancleListener
     * @param show    是否点击外部取消
     * @param text_ok
     * @param txt_no
     * @param canle_vis
     * @return
     */
    public static Dialog createConfirmDialogInt(Context context, int msg,final View.OnClickListener okListener,final View.OnClickListener cancleListener,boolean show,int text_ok,int txt_no,boolean canle_vis)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(show);
        final AlertDialog dlg = builder.create();
        dlg.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_confirm);
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        TextView tittleTextView = (TextView) window.findViewById(R.id.confirmdlg_tittle);
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        tittleTextView.setText(msg);

        if (canle_vis) {
            cancel.setVisibility(View.VISIBLE);
            if (show) {
                ok.setText(text_ok);
                cancel.setText(txt_no);
            }
        }


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }
                dlg.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cancleListener != null) {
                    cancleListener.onClick(v);
                }
                dlg.cancel();
            }
        });
        return dlg;
    }



    public  static ProgressDialog showprogressdialog(Context context,int msg,boolean is_cancle,boolean is_show){

         ProgressDialog pd = new ProgressDialog(context);
        if (-1!=msg){
            pd.setMessage(context.getResources().getString(msg));
            }
        if (!is_cancle){
            pd.setCanceledOnTouchOutside(false);
        }
        if (is_show){
            pd.show();
        }
        return pd;
    }

    /**
     * 点击头像 弹出的dialog
     * @param context
     * @param msg
     * @param camereBtnListener
     * @param pictureBtnListener
     * @return
     */
    public static Dialog createPhotoDialog(Context context, String msg, final View.OnClickListener camereBtnListener, final View.OnClickListener pictureBtnListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        final AlertDialog dlg = builder.create();
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_photo);
        TextView dialog_photo_title_name = (TextView) window.findViewById(R.id.dialog_photo_title_name);
        TextView dialog_photo_camera = (TextView) window.findViewById(R.id.dialog_photo_camera);
        TextView dialog_photo_pictrue = (TextView) window.findViewById(R.id.dialog_photo_pictrue);
        dialog_photo_title_name.setText(msg);

        /**点击拍照按钮*/
        dialog_photo_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camereBtnListener != null) {
                    camereBtnListener.onClick(v);
                }
                dlg.dismiss();
            }
        });
        /**点击从相册选择按钮*/
        dialog_photo_pictrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pictureBtnListener != null){
                    pictureBtnListener.onClick(v);
                }
                dlg.dismiss();
            }
        });
        return dlg;
    }


    /**
     * 点击图片放大
     * @param context
     * @param msg
     * @return
     */
    public static Dialog showPhotoDialog(Context context, String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        final AlertDialog dlg = builder.create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.show_dialog);

//        window.setWindowAnimations(R.style.dialogWindowAnim);//设置动画

        ImageView photo=(ImageView) window.findViewById(R.id.photo_dialog_iv);
        Picasso.with(context).load(msg).error(R.drawable.bg_collection).into(photo);

        /**点击拍照按钮*/
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        return dlg;

    }


    public static Dialog createConfirmDialogUpdata(Context context, String sersion,String msg,boolean update,final View.OnClickListener okListener,final View.OnClickListener cancleListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final AlertDialog dlg = builder.create();
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_update);
        TextView  version=(TextView) window.findViewById(R.id.updata_vesion);
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        TextView tittleTextView = (TextView) window.findViewById(R.id.confirmdlg_tittle);
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        version.setText("发现新版本	"+sersion);
        tittleTextView.setText(msg);

        if (update) {
            cancel.setText("取消");
            ok.setText("确定");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }

                dlg.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (cancleListener != null)
                {
                    cancleListener.onClick(v);
                }
                dlg.cancel();
            }
        });
        return dlg;
    }


    public static Dialog createConfirmDialogInput(Context context,String msg,final View.OnClickListener okListener,final View.OnClickListener cancleListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final AlertDialog dlg = builder.create();
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_input);
        TextView tittleTextView = (TextView) window.findViewById(R.id.confirmdlg_tittle);

        EditText  input= (EditText) window.findViewById(R.id.editText);

        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);

        tittleTextView.setText(msg);



        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }

                dlg.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (cancleListener != null)
                {
                    cancleListener.onClick(v);
                }
                dlg.cancel();
            }
        });
        return dlg;
    }








}