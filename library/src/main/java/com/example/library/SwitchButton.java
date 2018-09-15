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
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.example.library.UIUtils.dip2Px;
import static com.example.library.UIUtils.sp2px;

/**
 *
 */
public class SwitchButton extends View {


    private int normalColor;
    private int selectedColor;
    private int selectedTab;
    private float textSize,strokeRadius,strokeWidth,totalWidth,totalHeight;
    private String[] tabTexts=new String[]{"左边","中间","右边","右边"};
    private Paint selectedPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private OnSwitchCheckListener listener;


    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }


    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.SwitchButton);
        normalColor=array.getColor(R.styleable.SwitchButton_normalColor, Color.WHITE);
        selectedColor=array.getColor(R.styleable.SwitchButton_selectedColor, Color.RED);
        selectedTab=array.getInt(R.styleable.SwitchButton_selectedTab,0);
        textSize=array.getDimension(R.styleable.SwitchButton_textSize, sp2px(context,14));
        strokeRadius=array.getDimension(R.styleable.SwitchButton_strokeRadius, dip2Px(context,5));
        strokeWidth=array.getDimension(R.styleable.SwitchButton_strokeWidth, dip2Px(context,2));
        array.recycle();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textPaint.setTextSize(textSize);
        int defaultWidth = getDefaultWidth();
        int defaultHeight = getDefaultHeight();
        setMeasuredDimension(getExpectSize(defaultWidth, widthMeasureSpec), getExpectSize(defaultHeight,
                heightMeasureSpec));
    }

    /**
     * get default height when android:layout_height="wrap_content"
     */
    private int getDefaultHeight() {
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        return (int) (fontMetrics.bottom - fontMetrics.top) + getPaddingTop() + getPaddingBottom();
    }

    /**
     * get default width when android:layout_width="wrap_content"
     */
    private int getDefaultWidth() {
        float tabTextWidth = 0f;
        int tabs = tabTexts.length;
        for (int i = 0; i < tabs; i++) {
            tabTextWidth = Math.max(tabTextWidth, textPaint.measureText(tabTexts[i]));
        }
        float totalTextWidth = tabTextWidth * tabs;
        float totalStrokeWidth = (strokeWidth * tabs);
        int totalPadding = (getPaddingRight() + getPaddingLeft()) * tabs;
        return (int) (totalTextWidth + totalStrokeWidth + totalPadding);
    }


    /**
     * get expect size
     *
     * @param size
     * @param measureSpec
     * @return
     */
    private int getExpectSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            default:
                break;
        }
        return result;
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
        textPaint.setTextSize(textSize);
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
            drawText(canvas,i,tabWidth);
        }

    }


    /**
     * @param canvas    画布
     * @param position  tab位置
     * @param tabWidth  每一个tab宽度
     */
    private void drawText(Canvas canvas,int position,float tabWidth){
        String text=tabTexts[position];
        if (position==selectedTab){
            textPaint.setColor(normalColor);
        }else {
            textPaint.setColor(selectedColor);
        }
        Rect textRect=new Rect();
        textPaint.getTextBounds(text,0,text.length(),textRect);
        float startX=position*tabWidth+tabWidth/2-textRect.width()/2;
        float startY=getMeasuredHeight()/2+textRect.height()/2;
        canvas.drawText(text,startX,startY,textPaint);
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            float tabWith = getMeasuredWidth() / tabTexts.length;
            for (int i = 0; i < tabTexts.length; i++) {
                float startX = tabWith * i;
                float endX = tabWith * (i + 1);
                float startY = strokeWidth / 2;
                float endY = getMeasuredHeight();
                if (x > startX && x < endX && y > startY && y < endY) {
                    selectedTab = i;
                    invalidate();
                    if (listener != null) {
                        listener.checkButton(i, tabTexts[i]);
                    }
                    break;
                }
            }
        }
        return true;
    }



    //清楚选中状态
    public void clearSelectin(){
        selectedTab=-1;
        postInvalidate();
    }


    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getStrokeRadius() {
        return strokeRadius;
    }

    public void setStrokeRadius(float strokeRadius) {
        this.strokeRadius = strokeRadius;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String[] getTabTexts() {
        return tabTexts;
    }

    public void setTabTexts(String... tabTexts) {
        if (tabTexts.length<=1){
            throw new IllegalArgumentException("the size of tabTexts should greater then 1");
        }
        this.tabTexts = tabTexts;
        invalidate();
    }

    public void setListener(OnSwitchCheckListener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SwitchButton.class.getSimpleName(),super.onSaveInstanceState());
        bundle.putInt("selectedTab", selectedTab);
        bundle.putStringArray("tabTexts",tabTexts);
        bundle.putInt("normalColor",normalColor);
        bundle.putInt("selectedColor",selectedColor);
        bundle.putFloat("textSize",textSize);
        bundle.putFloat("strokeRadius",strokeRadius);
        bundle.putFloat("strokeWidth",strokeWidth);
        return bundle;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            selectedTab=bundle.getInt("selectedTab");
            tabTexts=bundle.getStringArray("tabTexts");
            normalColor=bundle.getInt("normalColor");
            selectedColor=bundle.getInt("selectedColor");
            textSize=bundle.getFloat("textSize");
            strokeRadius=bundle.getFloat("strokeRadius");
            strokeWidth=bundle.getFloat("strokeWidth");
            super.onRestoreInstanceState(bundle.getParcelable(SwitchButton.class.getSimpleName()));
        }else {
            super.onRestoreInstanceState(state);
        }

    }

    public interface OnSwitchCheckListener{
        void checkButton(int tabIndex,String selectedString);
    }


}
