package com.example.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.example.library.UIUtils.dip2Px;
import static com.example.library.UIUtils.sp2px;

public class SwitchButton extends View {


    private int normalColor,selectedColor,selectedTab;
    private float textSize,strokeRadius,strokeWidth,totalWidth,totalHeight;
    private String[] tabTexts=new String[]{"左边","中间","右边"};
    private Paint selectedPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint normalPaint=new Paint(Paint.ANTI_ALIAS_FLAG);


    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.SwitchButton);
        selectedColor=array.getColor(R.styleable.SwitchButton_selectedColor, Color.RED);
        selectedTab=array.getInt(R.styleable.SwitchButton_selectedTab,0);
        textSize=array.getDimension(R.styleable.SwitchButton_textSize, sp2px(context,14));
        strokeRadius=array.getDimension(R.styleable.SwitchButton_strokeRadius, dip2Px(context,15));
        strokeWidth=array.getDimension(R.styleable.SwitchButton_strokeWidth, dip2Px(context,5));
        array.recycle();
    }


    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        totalWidth=getMeasuredWidth();
        totalHeight=getMeasuredHeight();

        //逐个画矩形兵处理两边圆角。画笔宽度有一半会融入图形大小，另外一半在图形之外
        selectedPaint.setColor(selectedColor);
        selectedPaint.setStrokeWidth(strokeWidth);
        selectedPaint.setAntiAlias(true);
        float tabWidth=totalWidth/tabTexts.length;
        for (int i=0;i<tabTexts.length;i++){
            if (selectedTab==i){
                selectedPaint.setStyle(Paint.Style.FILL);
            }else {
                selectedPaint.setStyle(Paint.Style.STROKE);
            }
            if (i==0){
                RectF rectR0=new RectF(strokeWidth/2,strokeWidth/2,tabWidth-strokeWidth/2,totalHeight-strokeWidth/2);
                canvas.drawRoundRect(rectR0,strokeRadius,strokeRadius,selectedPaint);
                RectF rectF=new RectF(tabWidth-strokeRadius,tabWidth-strokeRadius,strokeRadius,strokeRadius);
                canvas.drawRect(rectF,selectedPaint);
                selectedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }else if (i==tabTexts.length-1){
                float startX=totalWidth-tabWidth+strokeWidth/2;
                RectF rectR0=new RectF(startX,strokeWidth/2,totalWidth-strokeWidth/2,totalHeight-strokeWidth/2);
                canvas.drawRoundRect(rectR0,strokeRadius,strokeRadius,selectedPaint);
                RectF rectF=new RectF(totalWidth-strokeRadius,totalWidth-strokeRadius,strokeRadius,strokeRadius);
                canvas.drawRect(rectF,selectedPaint);
                selectedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }else {
                float startX=tabWidth*i+strokeWidth/2;
                float endX=tabWidth*(i+1)-strokeWidth/2;
                RectF rect=new RectF(startX,strokeWidth/2,endX,totalHeight-strokeWidth/2);
                canvas.drawRect(rect,selectedPaint);
            }
        }
        Bitmap bitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Rect src=new Rect(0,0,(int)totalWidth,(int)totalHeight);
        Rect dst=src;
        canvas.drawBitmap(bitmap,src,dst,selectedPaint);










//        //画外框和分割线
//        RectF rect=new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
//        selectedPaint.setStrokeWidth(strokeWidth);
//        selectedPaint.setColor(selectedColor);
//        canvas.drawRoundRect(rect,strokeRadius,strokeRadius,selectedPaint);
//        if (tabTexts.length<=1){
//            return;
//        }
//        float tabWidth=(getMeasuredWidth()-((tabTexts.length+1)*strokeWidth))/tabTexts.length;
//        //获取背景颜色
//
//        for (int i=0;i<tabTexts.length-1;i++){
//            float width=(tabWidth+strokeWidth)*(i+1);
//            canvas.drawLine(width,strokeWidth,width,getMeasuredHeight()-strokeWidth,selectedPaint);
//        }
//
//
//
//
//
//
//        //画选中部分的背景色
//        if (selectedTab==0){
//            RectF rect1=new RectF(0,0,tabWidth+strokeWidth,getMeasuredHeight()-strokeWidth);
//            canvas.drawRoundRect(rect1,strokeRadius,strokeRadius,selectedPaint);
//            RectF rectR=new RectF(0,0,strokeRadius,strokeRadius);
//            canvas.drawRect(rectR,selectedPaint);
//            selectedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        }else if (selectedTab==tabTexts.length-1){
//            float startWith=getMeasuredWidth()-tabWidth-strokeWidth;
//            RectF rect1=new RectF(startWith,strokeWidth,getMeasuredWidth()-strokeWidth,getMeasuredHeight()-strokeWidth);
//            canvas.drawRoundRect(rect1,strokeRadius,strokeRadius,selectedPaint);
//            RectF rectR=new RectF(getMeasuredWidth()-strokeRadius-strokeWidth,0,strokeRadius,strokeRadius);
//            canvas.drawRect(rectR,selectedPaint);
//            selectedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        }else {
//
//        }

    }






}
