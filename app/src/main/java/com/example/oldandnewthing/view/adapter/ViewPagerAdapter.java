package com.example.oldandnewthing.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.oldandnewthing.model.bean.CategoryBean;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //数据源
    List<CategoryBean> categoryBeans;
    List<Fragment> fragments;

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<CategoryBean> categoryBeans, List<Fragment> fragments) {
        super(fm);
        this.categoryBeans = categoryBeans;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //固定写法
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    //返回对应的tablayout标签
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryBeans.get(position%categoryBeans.size()).getTitle();
    }
}
