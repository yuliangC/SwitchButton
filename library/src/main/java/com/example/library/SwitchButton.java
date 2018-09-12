package com.example.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.example.library.UIUtils.dip2Px;
import static com.example.library.UIUtils.sp2px;

public class SwitchButton extends View {


    private int normalColor,selectedColor,selectedTab;
    private float textSize,strokeRadius,strokeWidth;
    private String[] tabTexts;
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.SwitchButton);
        selectedColor=array.getColor(R.styleable.SwitchButton_selectedColor, Color.YELLOW);
        selectedTab=array.getInt(R.styleable.SwitchButton_selectedTab,0);
        textSize=array.getDimension(R.styleable.SwitchButton_textSize, sp2px(context,14));
        strokeRadius=array.getDimension(R.styleable.SwitchButton_strokeRadius, dip2Px(context,15));
        strokeWidth=array.getDimension(R.styleable.SwitchButton_strokeWidth, dip2Px(context,2));
        tabTexts= (String[]) array.getTextArray(R.styleable.SwitchButton_tabTexts);
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        RectF rect=new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
        paint.setColor(selectedColor);
        canvas.drawRoundRect(rect,strokeRadius,strokeRadius,paint);




    }






}
