package com.example.oldandnewthing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    /**
     *                 * Runnable延迟执行的时间
     * <p>
     *            
     */


    private long delayMillis = 100;

    /**
     *             * 上次滑动的时间
     * <p>
     *        
     */


    private long lastScrollUpdate = -1;

    private Runnable scrollerTask = new Runnable() {

        @Override

        public void run() {

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastScrollUpdate) > 100) {

                lastScrollUpdate = -1;

                onScrollEnd();

            } else {
                postDelayed(this, delayMillis);
            }

        }

    };


    public MyScrollView(Context context) {

        this(context, null);

    }


    public MyScrollView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }


    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (lastScrollUpdate == -1) {

            onScrollStart();

            postDelayed(scrollerTask, delayMillis);

        }
        // 更新ScrollView的滑动时间
        lastScrollUpdate = System.currentTimeMillis();

    }

    /**
     *             * 滑动开始
     * <p>
     *            
     */


    private void onScrollStart() {

        if (mListener!=null){
            mListener.onScrollStart();
        }

    }

    /**
     *          * 滑动结束
     *        
     */


    private void onScrollEnd() {

        if (mListener!=null){
            mListener.onScrollEnd();
        }

    }

    private OnScrollListener mListener;


    public void setOnScrollListener(OnScrollListener onRefreshListener) {

        mListener = onRefreshListener;

    }

    public interface OnScrollListener {

        void onScrollStart();

        void onScrollEnd();

    }


}
