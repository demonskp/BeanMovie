package com.fykj.yzy.beanmovie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.fykj.yzy.beanmovie.bean.ComeSoonBean;

import java.util.List;

/**
 * Created by 易镇艺 on 2017/7/26.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private List<ComeSoonBean> dataList;

    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private int alignBottom;
    private Paint.FontMetrics fontMetrics;


    public SectionDecoration(List<ComeSoonBean> dataList, Context context, DecorationCallback decorationCallback){
        Resources res = context.getResources();
//        this.dataList = dataList;
//        this.callback = decorationCallback;
//        //设置悬浮栏的画笔---paint
//        paint = new Paint();
//        paint.setColor(res.getColor(R.color.colorGray));
//
//        //设置悬浮栏中文本的画笔
//        textPaint = new TextPaint();
//        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(DensityUtil.dip2px(context, 14));
//        textPaint.setColor(Color.DKGRAY);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//        fontMetrics = new Paint.FontMetrics();
//        //决定悬浮栏的高度等
//        topGap = res.getDimensionPixelSize(R.dimen.sectioned_top);
//        //决定文本的显示位置等
//        alignBottom = res.getDimensionPixelSize(R.dimen.sectioned_alignBottom);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }



    /**
     * 判断是不是组中的第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同意组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String prevGroupId = callback.getGroupId(pos - 1);
            String groupId = callback.getGroupId(pos);
            //判断前一个字符串 与 当前字符串 是否相同
            if (prevGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }
    }

    //定义一个借口方便外界的调用
    interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }

}
