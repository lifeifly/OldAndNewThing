package com.example.oldandnewthing.presenter;

import android.widget.Toast;

import com.example.oldandnewthing.model.MvpModel;
import com.example.oldandnewthing.model.base.DataCallBack;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.CategoryBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.presenter.base.MvpBasePresenter;
import com.example.oldandnewthing.view.base.DefineView;

import java.util.List;
import java.util.Map;


public class MvpPresenter extends MvpBasePresenter<DefineView> implements DataCallBack {

    private MvpModel model;

    public MvpPresenter(DefineView view, CategoryBean categoryBean) {
        super(view);
        //将自身接口传给Model
        model = new MvpModel(this, categoryBean);
    }


    @Override
    public void requestBannerDataToModel() {
        //请求广告数据
        model.requestBannerData();
    }

    @Override
    public void requestListDataToModel() {
        //请求列表数据
        model.requestListData();
    }

    @Override
    public void requestArticleData(String uri) {
        //请求文章数据
        model.requestArticle(uri);
    }

    @Override
    public void requestPhotoData(String uri) {
        model.requestPhoto(uri);
    }

    @Override
    public void requestVideoData(String uri) {
        model.requestVideo(uri);
    }

    @Override
    public void requestSearchData(String keyword) {
        model.search(keyword);
    }


    //数据回调方法
    @Override
    public void onBannerSuccess(Object data) {
        if (data != null) {
            view.refreshBanner(data);
        }
    }

    @Override
    public void onBannerError(String result) {

    }

    @Override
    public void onListSuccess(Object data) {
        if (data != null) {
            view.refreshList(data);
        }
    }

    @Override
    public void onListError(String error) {
        Toast.makeText(view.getContext(), "数据加载错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAricleSuccess(ArticleBean articleBean) {
        if (articleBean!=null){
            view.refreshArticle(articleBean);
        }
    }

    @Override
    public void onArticleError(String error) {
        Toast.makeText(view.getContext(), "数据加载错误", Toast.LENGTH_SHORT).show();
        view.getContext().finish();
    }

    @Override
    public void onPhotoSuccess(PhotoBean photoBean) {
        if (photoBean!=null){
            view.refreshPhoto(photoBean);
        }
    }

    @Override
    public void onPhotoError(String error) {
        Toast.makeText(view.getContext(), "数据加载错误", Toast.LENGTH_SHORT).show();
        view.getContext().finish();
    }

    @Override
    public void onVideoSuccess(VideoBean videoBean) {
        if (videoBean != null) {
            view.refreshVideo(videoBean);
        }
    }

    @Override
    public void onVideoError(String error) {
        Toast.makeText(view.getContext(), "数据加载错误", Toast.LENGTH_SHORT).show();
        view.getContext().finish();
    }

    @Override
    public void searchSuccess(List<Map<String, Object>> data) {
        if (data != null) {
            view.refreshSearchSuccess(data);
        }
    }


    @Override
    public void searchError() {
        view.refreshSearchError();
    }

}
