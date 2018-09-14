package com.example.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

/**
 *
 */
public class SwitchButton extends View {


    private int normalColor,selectedColor,selectedTab;
    private float textSize,strokeRadius,strokeWidth,totalWidth,totalHeight;
    private String[] tabTexts=new String[]{"左边","中间","右边","右边"};
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
        strokeRadius=array.getDimension(R.styleable.SwitchButton_strokeRadius, dip2Px(context,5));
        strokeWidth=array.getDimension(R.styleable.SwitchButton_strokeWidth, dip2Px(context,2));
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
                selectedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            }else {
                selectedPaint.setStyle(Paint.Style.STROKE);
            }
            if (i==0){
                canvas.drawPath(getLeftPath(tabWidth),selectedPaint);
            }else if (i==tabTexts.length-1){
                canvas.drawPath(getRightPath(tabWidth),selectedPaint);
            }else {
                canvas.drawPath(getCenterTabPath(tabWidth,i),selectedPaint);
            }
        }


        //画文字 还有触摸反馈事件








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


    /**
     * @param tabWidth  每一个tab的宽度
     * @return
     */
    private Path getLeftPath(float tabWidth){
        Path path=new Path();
        RectF rectF=new RectF(strokeWidth/2,strokeWidth/2,strokeWidth+strokeRadius*2,strokeWidth+strokeRadius*2);
        path.addArc(rectF,180,90);
        path.lineTo(tabWidth-strokeWidth/2,strokeWidth/2);
        path.lineTo(tabWidth-strokeWidth/2,totalHeight-strokeWidth/2);
        path.lineTo(strokeWidth/2+strokeRadius,totalHeight-strokeWidth/2);
        float rectL=strokeWidth/2;
        float rectT=totalHeight-strokeWidth-strokeRadius*2;
        float rectR=strokeWidth+strokeRadius*2;
        float rectB=totalHeight-strokeWidth/2;
        RectF rectF1=new RectF(rectL,rectT,rectR,rectB);
        path.arcTo(rectF1,90,90);
        path.lineTo(strokeWidth/2,totalHeight-strokeWidth/2-strokeRadius);
        path.close();
        return path;
    }


    /**
     * @param tabWidth  每一个tab的宽度
     * @param position  tab的位置
     * @return
     */
    private Path getCenterTabPath(float tabWidth,int position){
        Path path=new Path();
        path.moveTo(tabWidth*position-strokeWidth/2,strokeWidth/2);
        path.lineTo(tabWidth*(position+1)-strokeWidth/2,strokeWidth/2);
        path.lineTo(tabWidth*(position+1)-strokeWidth/2,totalHeight-strokeWidth/2);
        path.lineTo(tabWidth*position-strokeWidth/2,totalHeight-strokeWidth/2);
        path.close();
        return path;
    }


    /**
     * @param tabWidth  每一个tab的宽度
     * @return
     */
    private Path getRightPath(float tabWidth){
        Path path=new Path();
        float startX=totalWidth-tabWidth-strokeWidth/2;
        path.moveTo(startX,strokeWidth/2);
        path.lineTo(totalWidth-strokeWidth-strokeRadius,strokeWidth/2);
        float rect1L=totalWidth-strokeWidth-strokeRadius*2;
        RectF rectF=new RectF(rect1L,strokeWidth/2,totalWidth-strokeWidth/2,strokeWidth+strokeRadius*2);
        path.arcTo(rectF,-90,90);
        path.lineTo(totalWidth-strokeWidth/2,totalHeight-strokeRadius-strokeWidth/2);

        float rectT=totalHeight-strokeWidth-strokeRadius*2;
        RectF rect2F=new RectF(rect1L,rectT,totalWidth-strokeWidth/2,totalHeight-strokeWidth/2);
        path.arcTo(rect2F,0,90);
        path.lineTo(totalWidth-strokeWidth-strokeRadius,totalHeight-strokeWidth/2);
        path.lineTo(startX,totalHeight-strokeWidth/2);
        path.close();
        return path;
    }










}
