package com.example.oldandnewthing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.oldandnewthing.R;

//数字指示器
public class DigitalIndicator extends View {
    //选中第几个默认从1开始
    private int selectedIndex = 1;
    //总共有几个
    private int count = 15;


    public DigitalIndicator(Context context) {
        super(context);
    }

    public DigitalIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    //当选中的索引改变，即重绘
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        invalidate();
    }

    public int getCount() {
        return count;
    }

    //当数量改变，即重绘
    public void setCount(int count) {
        this.count = count;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //宽度
        int width = getWidth();
        //高度
        int height = getHeight();
        Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //设置红色
        paint.setColor(getResources().getColor(android.R.color.holo_red_light));
        paint.setTextSize(100);
        canvas.drawText(String.valueOf(selectedIndex), getX(), getPivotY() + height / 4, paint);

        Paint paint1 = new Paint();
        paint1.setTextSize(50);
        paint1.setAntiAlias(true);
        paint1.setColor(getResources().getColor(android.R.color.black));
        if (selectedIndex>=10){
            //画不变的部分
            canvas.drawText("/" + count, (float) (getX() + width / 1.6), getPivotY() + height / 4, paint1);
        }else {
            //画不变的部分
            canvas.drawText("/" + count, (float) (getX() + width / 2.5), getPivotY() + height / 4, paint1);
        }
    }
}
