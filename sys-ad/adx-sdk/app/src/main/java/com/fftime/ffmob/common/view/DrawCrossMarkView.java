package com.fftime.ffmob.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DrawCrossMarkView extends View {
    //绘制圆弧的进度值
    private int progress = 0;

    //线1的x轴增量
    private int line1X = 0;

    //线1的y轴增量
    private int line1Y = 0;

    //线2的x轴增量
    private int line2X = 0;

    //线2的y轴增量
    private int line2Y = 0;

    //打叉的起点
    int line1StartX;
    int line2StartX;
    int lineStartY;

    int step = 2;

    //线水平最大增量
    int maxLineIncrement;

    //线的宽度
    private int lineThick = 6;

    //获取圆心的x坐标
    int center;

    //圆弧半径
    int radius;

    //定义的圆弧的形状和大小的界限
    RectF rectF;

    Paint paint;
    Paint bgPaint;

    //控件大小
    float totalWidth;

    public DrawCrossMarkView(Context context) {
        super(context);
        totalWidth = dp2px(context, 30);

        maxLineIncrement = (int) (totalWidth * 2 / 5);
        init();
    }

    public DrawCrossMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Pattern p = Pattern.compile("\\d*");

        Matcher m = p.matcher(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width"));

        if (m.find()) {
            totalWidth = Float.valueOf(m.group());
        }

        totalWidth = dp2px(context, totalWidth);

        maxLineIncrement = (int) (totalWidth * 2 / 5);

        init();
    }

    public static float dp2px(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public DrawCrossMarkView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //init();
    }

    void init() {
        paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.WHITE);

        //设置圆弧的宽度
        paint.setStrokeWidth(lineThick);

        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);

        //消除锯齿
        paint.setAntiAlias(true);

        //获取圆心的x坐标
        center = (int) (totalWidth / 2);

        //圆弧半径
        radius = (int) (totalWidth / 2) - lineThick;

        //打叉的起点
        line1StartX = (int) (center + totalWidth / 5);
        lineStartY = (int) (center - totalWidth / 5);
        line2StartX = (int) (center - totalWidth / 5);

        rectF = new RectF(center - radius,
                center - radius,
                center + radius,
                center + radius);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧
        canvas.drawArc(rectF, 235, -360, false, paint);
        //画叉
        line1X = maxLineIncrement;
        line1Y = maxLineIncrement;
        //画第一根线
        canvas.drawLine(line1StartX, lineStartY, line1StartX - line1X, lineStartY + line1Y, paint);
        line2X = maxLineIncrement;
        line2Y = maxLineIncrement;
        //画第二根线
        canvas.drawLine(line2StartX, lineStartY, line2StartX + line2X, lineStartY + line2Y, paint);
    }

}