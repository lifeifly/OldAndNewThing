package com.example.oldandnewthing.model;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.base.DataCallBack;
import com.example.oldandnewthing.model.base.MvpBaseModel;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.BannerBean;
import com.example.oldandnewthing.model.bean.CategoryBean;
import com.example.oldandnewthing.model.bean.MiPerBannerBean;
import com.example.oldandnewthing.model.bean.NewsListBean;
import com.example.oldandnewthing.model.bean.PersonKuaiKanBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.PhotoMilitaryBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.model.beanmanager.ArticleBeanManager;
import com.example.oldandnewthing.model.beanmanager.BannerBeanManager;
import com.example.oldandnewthing.model.beanmanager.MilitaryBannerBeanManager;
import com.example.oldandnewthing.model.beanmanager.NewsListBeanManager;
import com.example.oldandnewthing.model.beanmanager.PersonBannerManager;
import com.example.oldandnewthing.model.beanmanager.PersonKuaiKanBeanManager;

import com.example.oldandnewthing.model.beanmanager.PhotoBannerBeanManager;
import com.example.oldandnewthing.model.beanmanager.PhotoBeanManager;
import com.example.oldandnewthing.model.beanmanager.PhotoMilitaryBeanManager;
import com.example.oldandnewthing.model.beanmanager.VideoBeanManager;
import com.example.oldandnewthing.model.network.XUtilsRequest;
import com.example.oldandnewthing.model.url.MainURL;
import com.example.oldandnewthing.utils.CommonUtils;
import com.example.oldandnewthing.view.SearchActivity;


import org.xutils.common.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MvpModel extends MvpBaseModel {
    private DataCallBack callBack;

    //记录对应的广告和列表链接
    private String bannerUrl;
    private String listUrl;
    //数据类型
    private CategoryBean categoryBean;
    //广告数据源
    private List<BannerBean> bannerData1;
    private List<PersonKuaiKanBean.DataBean.ListBean> bannerList;
    private List<MiPerBannerBean> miPerKuaiBannerBeanList;
    private List<MiPerBannerBean> data2;
    private List<BannerBean> data1;


    //列表数据源总数据
    private static List<PersonKuaiKanBean.DataBean.ListBean> list;
    private static List<PhotoMilitaryBean.RollDataBean> rollData;
    private static List<NewsListBean> newsListData;
    //列表数据源每次提供的数据源
    private List<PersonKuaiKanBean.DataBean.ListBean> newList;
    private List<PhotoMilitaryBean.RollDataBean> newRollData;
    private List<NewsListBean> newNewsListData;
    //记录数据请求的次数
    private int requestTimes = 0;

    public MvpModel(DataCallBack callBack, CategoryBean categoryBean) {
        this.callBack = callBack;
        this.categoryBean = categoryBean;
        if (categoryBean != null) {
            //获取对应的数据uri
            distinguishUrl();
        }
    }

    @Override
    public void requestBannerData() {

        //开启子线程请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                XUtilsRequest request = new XUtilsRequest(bannerUrl);
                request.getRequest(new Callback.CommonCallback<String>() {
                    //请求成功调用
                    @Override
                    public void onSuccess(String result) {
                        //转换为实体bean对象,并回调不同的数据类型
                        transferToBannerBean(result, callBack);
                    }

                    //失败调用
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        callBack.onBannerError(ex.toString());
                    }

                    //取消调用
                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    //结束调用
                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }


    @Override
    public void requestListData() {
        //如果是第一次请求,就一次性请求完数据
        requestTimes++;
        if (requestTimes == 1) {
            //开启子线程请求列表数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    XUtilsRequest request = new XUtilsRequest(listUrl);
                    request.getRequest(new Callback.CommonCallback<String>() {
                        //请求成功调用
                        @Override
                        public void onSuccess(String result) {
                            //转换为实体bean对象,并回调不同的数据类型
                            transferToListBean(result);
                            //根据每次填充20条数据回调给接口
                            callBackTo(requestTimes);
                        }

                        //失败调用
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            callBack.onBannerError(ex.toString());
                        }

                        //取消调用
                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        //结束调用
                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }).start();
        } else if (requestTimes > 1) {
            //说明数据已请求完毕，只需根据请求次数回调数据
            callBackTo(requestTimes);
        }

    }

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void requestArticle(final String uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    final ArticleBean articleBean = ArticleBeanManager.getData(uri);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                if (articleBean != null) {
                                    callBack.onAricleSuccess(articleBean);
                                } else {
                                    callBack.onArticleError("数据加载失败");
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //请求图片内容数据
    @Override
    public void requestPhoto(final String uri) {
        //拼成链接
        final String dataUrl = CommonUtils.changeHref(uri, "xml");
        PhotoBean photoBean = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求内容
                XUtilsRequest request = new XUtilsRequest(dataUrl);
                request.getRequest(new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        PhotoBean data = PhotoBeanManager.getData(result);
                        if (callBack != null) {
                            callBack.onPhotoSuccess(data);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        callBack.onPhotoError(ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    @Override
    public void requestVideo(final String uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                XUtilsRequest request = new XUtilsRequest(uri);
                request.getRequest(new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("VIDEO", result);


                        if (callBack != null) {
                            callBack.onVideoSuccess(VideoBeanManager.getData(result));
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    //根据请求次数回调数据
    private void callBackTo(int requestTimes) {
        //如果上次请求的数据个数小于总个数，说明还有数据

        if (categoryBean.getTitle().equals("人物") || categoryBean.getTitle().equals("快看")) {
            //返回人物快看相关数据
            //返回军事图片相关数据
            if (requestTimes * 20 <= list.size()) {
                for (int i = ((requestTimes - 1) * 20); i < (requestTimes * 20); i++) {
                    newList.add(list.get(i));
                }
                callBack.onListSuccess(newList);
            } else {
                if ((requestTimes - 1) * 20 < list.size()) {
                    for (int i = ((requestTimes - 1) * 20); i < list.size(); i++) {
                        newList.add(list.get(i));
                    }
                    callBack.onListSuccess(newList);
                } else {
                    callBack.onListError("已经没有更多新闻啦");
                }
            }

        } else if (categoryBean.getTitle().equals("图片") || categoryBean.getTitle().equals("军事")) {
            //返回军事图片相关数据
            if (requestTimes * 20 <= rollData.size()) {
                for (int i = ((requestTimes - 1) * 20); i < (requestTimes * 20); i++) {
                    newRollData.add(rollData.get(i));
                }
                callBack.onListSuccess(newRollData);
            } else {
                if ((requestTimes - 1) * 20 < rollData.size()) {
                    for (int i = ((requestTimes - 1) * 20); i < rollData.size(); i++) {
                        newRollData.add(rollData.get(i));
                    }
                    callBack.onListSuccess(newRollData);
                } else {
                    callBack.onListError("已经没有更多新闻啦");
                }
            }

        } else {
            //返回其他相关数据
            //返回军事图片相关数据
            if (requestTimes * 20 < newsListData.size()) {
                for (int i = ((requestTimes - 1) * 20); i < (requestTimes * 20); i++) {
                    newNewsListData.add(newsListData.get(i));
                }
                callBack.onListSuccess(newNewsListData);
            } else {
                if ((requestTimes - 1) * 20 < newsListData.size()) {
                    for (int i = ((requestTimes - 1) * 20); i < newsListData.size(); i++) {
                        newNewsListData.add(newsListData.get(i));
                    }
                    callBack.onListSuccess(newNewsListData);
                } else {
                    callBack.onListError("已经没有更多新闻啦");
                }

            }

        }


    }


    //根据不同分类转换为不同的列表bean对象，并会调数据
    private void transferToListBean(String listHtml) {
        //根据不同分类，分别请求对应新闻列表数据、和广告数据
        switch (categoryBean.getTitle()) {
            case "新闻":
                requestNewsListData(listHtml, 5);
                break;
            case "国内":
                requestNewsListData(listHtml, 6);
                break;
            case "国际":
                requestNewsListData(listHtml, 6);
                break;
            case "图片":
                requestPhotoMilitary(listHtml);
                break;
            case "人物":
                requestPersonKauikan(listHtml, 1);
                break;
            case "军事":
                requestPhotoMilitary(listHtml);
                break;
            case "快看":
                requestPersonKauikan(listHtml, 1);
                break;
            case "法治":
                requestNewsListData(listHtml, 4);
                break;
            case "文娱":
                requestNewsListData(listHtml, 4);
                break;
            case "科技":
                requestNewsListData(listHtml, 5);
                break;
            case "生活":
                requestNewsListData(listHtml, 5);
                break;
            case "社会":
                requestNewsListData(listHtml, 8);
                break;
        }
    }

    //根据不同分类转换为不同的实体广告bean对象,并回调数据
    private void transferToBannerBean(String bannerHtml, DataCallBack callBack) {
        switch (categoryBean.getTitle()) {
            case "图片":
                //转换为图片广告实体类
                BannerBean data = PhotoBannerBeanManager.getData(bannerHtml);
                //因为就一个广告数据，根据需要加入List
                data1 = new ArrayList<>();
                data1.add(data);
                if (callBack != null) {
                    callBack.onBannerSuccess(data1);
                }
                break;
            case "军事":
                //转换为军事广告实体类
                MiPerBannerBean miPerKuaiBannerBean = MilitaryBannerBeanManager.getData(bannerHtml);
                //就一个数据需要加入List
                data2 = new ArrayList<>();
                data2.add(miPerKuaiBannerBean);
                if (callBack != null) {
                    callBack.onBannerSuccess(data2);
                }
                break;
            case "人物":
                //转换为人物广告实体类
                miPerKuaiBannerBeanList = PersonBannerManager.getData(bannerHtml);
                Log.d("TAG1", miPerKuaiBannerBeanList.get(0).getSrc());
                if (callBack != null) {
                    callBack.onBannerSuccess(miPerKuaiBannerBeanList);
                }
                break;
            case "快看":
                //转换为快看广告实体类,快看广告和列表数据均来自同一个,截取前3个为广告
                PersonKuaiKanBean data3 = PersonKuaiKanBeanManager.getData(bannerHtml, 1);
                List<PersonKuaiKanBean.DataBean.ListBean> list = data3.getData().getList();
                Log.d("KUAIKAN", String.valueOf(list.size()));
                //新建一个集合存放广告数据
                bannerList = new ArrayList<>();
                bannerList.add(list.get(0));
                bannerList.add(list.get(1));
                bannerList.add(list.get(2));
                if (callBack != null) {
                    callBack.onBannerSuccess(bannerList);
                }
                break;
            default:
                //转换为默认除了以上四种类型的广告实体类
                //默认除了以上四种类型的广告数据绑定
                bannerData1 = BannerBeanManager.getBannerData(bannerHtml);
                if (callBack != null) {
                    callBack.onBannerSuccess(bannerData1);
                }
                break;
        }


    }

    //根据分类不同获得不同的广告和列表url
    private void distinguishUrl() {
        switch (categoryBean.getTitle()) {
            case "新闻":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.NEWSLISTURL;
                break;
            case "国内":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.INTERCOUNTRYURL;
                break;
            case "国际":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.WORLDURL;
                break;
            case "图片":
                bannerUrl = MainURL.PHOTOBANNERURL;
                listUrl = MainURL.PHOTOURL;
                break;
            case "人物":
                bannerUrl = MainURL.PERSONBANNERURL;
                listUrl = MainURL.PERSONURL;
                break;
            case "军事":
                bannerUrl = MainURL.MILITARYBANNERURL;
                listUrl = MainURL.MILITARYURL;
                break;
            case "快看":
                bannerUrl = MainURL.KUAIKANURL;
                listUrl = MainURL.KUAIKANURL;
                break;
            case "法治":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.LAWURL;
                break;
            case "文娱":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.ENTURL;
                break;
            case "科技":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.TECHURL;
                break;
            case "生活":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.LIFEURL;
                break;
            case "社会":
                bannerUrl = categoryBean.getHref();
                listUrl = MainURL.SOCIETYURL;
                break;
        }
    }

    //人物快看类型列表
    private void requestPersonKauikan(String response, int startIndex) {
        //转换为实体类
        newList = new ArrayList<>();
        PersonKuaiKanBean data = PersonKuaiKanBeanManager.getData(response, startIndex);
        list = data.getData().getList();
        Log.d("13", String.valueOf(data.getData().getList().size()));

    }
    //图片军事类型列表

    private void requestPhotoMilitary(String response) {
        newRollData = new ArrayList<>();
        PhotoMilitaryBean photoMilitaryBean = PhotoMilitaryBeanManager.getData(response);
        //获取数据集合
        rollData = photoMilitaryBean.getRollData();
    }

    //除了图片、人物、军事、快看分类的其他类型列表，请求数据并填充

    public void requestNewsListData(String newsListHtml, int startIndex) {
        newNewsListData = new ArrayList<>();
        //获取除了图片列表集合
        newsListData = NewsListBeanManager.getNewsListData(newsListHtml, startIndex);
    }

    //搜索方法
    public void search(String keyword) {
        int s=1;
        int z=1;
        List<Map<String, Object>> searchData = new ArrayList<>();
        searchData.clear();
        if (newsListData != null) {
            for (int i = 0; i < newsListData.size(); i++) {
                if (newsListData.get(i).getTitle().contains(keyword)||newsListData.get(i).getKeywords().contains(keyword)) {
                    Map<String, Object> newsMap = new HashMap<>();
                    newsMap.put("news", newsListData.get(i));
                    searchData.add(newsMap);
                    Log.d("SEARCH"+String.valueOf(z++),String.valueOf(s++));
                }
            }
        }
        if (rollData != null) {
            for (int i = 0; i < rollData.size(); i++) {
                if (rollData.get(i).getTitle().contains(keyword)||rollData.get(i).getBrief().contains(keyword)) {
                    Map<String, Object> newsMap = new HashMap<>();
                    newsMap.put("roll", rollData.get(i));
                    searchData.add(newsMap);
                    Log.d("SEARCH4","2");
                }
            }
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTitle().contains(keyword)||list.get(i).getKeywords().contains(keyword)) {
                    Map<String, Object> newsMap = new HashMap<>();
                    newsMap.put("list", list.get(i));
                    searchData.add(newsMap);
                    Log.d("SEARCH5","3");
                }
            }
        }
        if (searchData.size() > 0) {
            if (callBack!=null){
                callBack.searchSuccess(searchData);

            }
        } else {
            if (callBack!=null){
                callBack.searchError();
            }
        }
    }
}
