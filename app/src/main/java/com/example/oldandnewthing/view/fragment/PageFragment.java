package com.example.oldandnewthing.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.view.adapter.GalleryAdapter;
import com.example.oldandnewthing.view.adapter.QucikAdapter;
import com.example.oldandnewthing.model.bean.BannerBean;
import com.example.oldandnewthing.model.bean.CategoryBean;
import com.example.oldandnewthing.model.bean.MiPerBannerBean;
import com.example.oldandnewthing.model.bean.NewsListBean;
import com.example.oldandnewthing.model.bean.PersonKuaiKanBean;
import com.example.oldandnewthing.model.bean.PhotoMilitaryBean;
import com.example.oldandnewthing.view.base.DefineView;
import com.example.oldandnewthing.widget.OpenActivity;
import com.example.oldandnewthing.view.fragment.base.BaseFragment;

import com.example.oldandnewthing.widget.AutoGallery;
import com.example.oldandnewthing.widget.FlowIndicator;


import java.io.IOException;

import java.util.List;
import java.util.Map;

public class PageFragment extends BaseFragment implements DefineView {
    private View mView;
    private NestedScrollView nestedScrollView;
    private static final String EXTRA = "extra";
    private CategoryBean categoryBean;
    private AutoGallery autoGallery;
    private FlowIndicator flowIndicator;
    private RecyclerView recyclerView;

    //广告数据源
    private List<BannerBean> bannerData1;
    private List<PersonKuaiKanBean.DataBean.ListBean> bannerList;
    private List<MiPerBannerBean> miPerKuaiBannerBeanList;
    private List<MiPerBannerBean> data2;
    private List<BannerBean> data1;


    //列表数据源
    private List<PersonKuaiKanBean.DataBean.ListBean> list;
    private List<PhotoMilitaryBean.RollDataBean> rollData;
    private List<NewsListBean> newsListData;
    private QucikAdapter adapter1;
    private MvpPresenter presenter;
    //广告适配器
    private GalleryAdapter adapter;
    //纪录rv的item位置和数量
    private int totalItemCount;
    private int lastItemPosition;
    //用来标记是否正在向最后一个滑动
    boolean isSlidingToLast = false;


    public static PageFragment getInstance(CategoryBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA, extra);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryBean = (CategoryBean) bundle.get(EXTRA);
            //创建Presenter对象
            presenter = new MvpPresenter(this, categoryBean);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_page_layout, container, false);
            initView();
        }
        requestRefreshBanner();
        requestRefreshList();
        return mView;
    }

    //初始化视图组件
    public void initView() {
        nestedScrollView = (NestedScrollView) mView.findViewById(R.id.nested_scrollview);
        autoGallery = mView.findViewById(R.id.headline_image_gallery);
        flowIndicator = mView.findViewById(R.id.headline_circle_indicator);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycleView);
        //优化滑动卡顿
        recyclerView.setNestedScrollingEnabled(false);
        //设置数据刷新监听
        setRvListener();
    }

    @Override
    public void requestRefreshBanner() {
        //请求广告数据
        presenter.requestBannerDataToModel();
    }

    //请求presenter向model请求数据
    @Override
    public void requestRefreshList() {
        presenter.requestListDataToModel();
    }

    @Override
    public void refreshBanner(Object data) {
        switch (categoryBean.getTitle()) {
            case "图片":
                //因为就一个广告数据，根据需要加入List
                data1 = (List<BannerBean>) data;
                adapter = new GalleryAdapter(data1, null, null, getActivity(), 1);
                //设置圆点指示的个数
                flowIndicator.setCount(data1.size());
                break;
            case "军事":
                //就一个数据需要加入List
                data2 = (List<MiPerBannerBean>) data;
                adapter = new GalleryAdapter(null, data2, null, getActivity(), 3);
                //设置圆点指示的个数
                flowIndicator.setCount(data2.size());
                break;
            case "人物":
                //转换为人物广告实体类
                miPerKuaiBannerBeanList = (List<MiPerBannerBean>) data;
                Log.d("TAG1", miPerKuaiBannerBeanList.get(0).getSrc());
                adapter = new GalleryAdapter(null, miPerKuaiBannerBeanList, null, getActivity(), 2);
                //设置圆点指示的个数
                flowIndicator.setCount(miPerKuaiBannerBeanList.size());
                break;
            case "快看":
                //新建一个集合存放广告数据
                bannerList = (List<PersonKuaiKanBean.DataBean.ListBean>) data;
                adapter = new GalleryAdapter(null, null, bannerList, getActivity(), 4);
                //设置圆点指示的个数
                flowIndicator.setCount(bannerList.size());
                break;
            default:
                //转换为默认除了以上四种类型的广告实体类
                //默认除了以上四种类型的广告数据绑定
                bannerData1 = (List<BannerBean>) data;
                adapter = new GalleryAdapter(bannerData1, null, null, getActivity(), 0);
                //设置圆点指示的个数
                flowIndicator.setCount(bannerData1.size());
                break;
        }
        autoGallery.setAdapter(adapter);
        //设置指示器跟随广告变化和点击事件
        initBannerListener();
        if (adapter != null) {
            adapter.setOnItemClickedListener(new GalleryAdapter.OnItemClickedListener() {
                @Override
                public void onItemClicked(String url) {
                    transDataOpenActivity(url);
                }
            });
        }
    }

    @Override
    public void refreshList(Object data) {
        switch (categoryBean.getTitle()) {
            case "图片":
                //获取数据集合
                rollData = (List<PhotoMilitaryBean.RollDataBean>) data;
                //创建适配器,根据图片或军事填充不同的布局
                adapter1 = new QucikAdapter(null, null, rollData, getActivity(), R.layout.item_photolist_rv_layout, 1);
                recyclerView.setAdapter(adapter1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case "军事":
                //获取数据集合
                rollData = (List<PhotoMilitaryBean.RollDataBean>) data;
                //创建适配器,根据图片或军事填充不同的布局
                adapter1 = new QucikAdapter(null, null, rollData, getActivity(), R.layout.item_militarylist_rv_layout, 3);
                recyclerView.setAdapter(adapter1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case "人物":
                //转换为实体类
                list = (List<PersonKuaiKanBean.DataBean.ListBean>) data;
                //根据不同类型填充
                adapter1 = new QucikAdapter(null, list, null, getActivity(), R.layout.item_personlist_rv_layout, 2);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter1);
                break;
            case "快看":
                //转换为实体类
                list = (List<PersonKuaiKanBean.DataBean.ListBean>) data;
                //根据不同类型填充
                adapter1 = new QucikAdapter(null, list, null, getActivity(), R.layout.item_kuaikanlist_rv_layout, 4);
                //快看需要网络布局
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(adapter1);

                break;
            default:
                //获取除了图片列表集合
                newsListData = (List<NewsListBean>) data;
                //构建adapter
                adapter1 = new QucikAdapter(newsListData, null, null, getActivity(), R.layout.item_newslist_rv_layout, 0);
                //设置适配器
                recyclerView.setAdapter(adapter1);
                //设置布局管理器
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
        }
//设置recyclerview监听器
        initListListener(adapter1);
    }


    //设置Gallery选项监听器
    private void initBannerListener() {
        //设置gallery被选中监听器
        autoGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                //设置圆点指示跟跟Gallry同步
                flowIndicator.setSeletion(position % flowIndicator.getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //设置RecyclerView的点击事件
    private void initListListener(QucikAdapter adapter) {
        if (adapter != null) {
            adapter.setOnItemClickedListener(new QucikAdapter.OnItemClickedListener() {
                @Override
                public void onItemClicked(String url) {
                    transDataOpenActivity(url);
                }
            });
        }
    }

    //传输数据，并打开相应的活动,category代表数据来自广告还是列表，1广告2列表
    private void transDataOpenActivity(String url) {
        try {
            Intent intent = new Intent();
            OpenActivity.openActivity(url, getContext(), intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置recyclerview的滑动监听器，因为人物和军事列表数据是根据链接的n、s来控制第几页和每页显示几个数据的，
    // 当滑动到最后一个通过改变s来刷新数据，NestedScrollView影响recyclerview的滑动，故需要通过NestedScrollView的滑动事件替代
    private void setRvListener() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //滑动到底，刷新数据
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    requestRefreshList();
                }
            }
        });

    }

    //用于presenter土司
    @Nullable
    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void requestRefreshArticle(String uri) {

    }

    @Override
    public void requestRefershPhoto(String uri) {

    }

    @Override
    public void refreshArticle(ArticleBean articleBean) {

    }

    @Override
    public void refreshPhoto(PhotoBean photoBean) {

    }

    @Override
    public void requestRefreshVideo(String uri) {

    }

    @Override
    public void refreshVideo(VideoBean videoBean) {

    }

    @Override
    public void requestSearch(String keyword) {

    }

    @Override
    public void refreshSearchError() {

    }

    @Override
    public void refreshSearchSuccess(List<Map<String, Object>> data) {

    }
}
