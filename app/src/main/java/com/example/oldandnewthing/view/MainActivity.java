package com.example.oldandnewthing.view;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.CategoryBean;
import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.utils.CategoryBeanUtils;
import com.example.oldandnewthing.view.adapter.ViewPagerAdapter;
import com.example.oldandnewthing.view.base.BaseActivity;
import com.example.oldandnewthing.view.fragment.PageFragment;
import com.example.oldandnewthing.view.fragment.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RelativeLayout relativeLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<CategoryBean> categoryDataList;
    private List<Fragment> fragments;
    //记录滑动的数据
    private float startY;
    private float dy;
    public boolean isSlidingUp = false;
    public boolean isSlidingDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//沉浸式状态栏,当系统版本大于等于4.4时可以使用沉浸式状态栏
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取status高度
            int statusBarHeight = getStatusBarHeight(this);
            ImageView imageView = findViewById(R.id.iv_status);
            //把status的高度赋值给Imageview
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = statusBarHeight;
            imageView.setLayoutParams(layoutParams);
        }
        initView();
        initValidata();

        bindData();
    }


    public void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_search);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        //设置搜索框监听器
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    public void initValidata() {
        //获取自定义的分类信息Category
        categoryDataList = CategoryBeanUtils.getData();
        //根据category创建需要的Fragment
        fragments = new ArrayList<>();
        for (int i = 0; i < categoryDataList.size(); i++) {
            BaseFragment baseFragment = null;
            String title = categoryDataList.get(i).getTitle();
            baseFragment = PageFragment.getInstance(categoryDataList.get(i));
            fragments.add(baseFragment);
        }
    }


    public void bindData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), categoryDataList, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }




}
