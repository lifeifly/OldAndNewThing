package com.example.oldandnewthing.model.url;

//有些数据很多可以通过href的n、s控制第n页数据和每页显示s个json数据
public class MainURL {
    //主页数据url
    public static final String NEWSURL = "https://news.cctv.com/";
    //新闻界面列表数据url
    public static final String NEWSLISTURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/news_1.jsonp?cb=news&n=100";
    //国内界面列表数据url
    public static final String INTERCOUNTRYURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/china_1.jsonp?cb=china&n=100";
    //国际界面列表数据url
    public static final String WORLDURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/world_1.jsonp?cb=world&n=100";
    //社会界面列表数据url
    public static final String SOCIETYURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/society_1.jsonp?cb=society&n=100";
    //法制界面列表数据url
    public static final String LAWURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/law_1.jsonp?cb=law&n=100";
    //图片界面广告数据url
    public static final String PHOTOBANNERURL = "https://photo.cctv.com/";
    //图片界面列表数据url
    public static final String PHOTOURL = "https://photo.cctv.com/data/index.json";
    //文娱界面列表数据url
    public static final String ENTURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/ent_1.jsonp?cb=ent&n=100";
    //科技界面列表数据url
    public static final String TECHURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/tech_1.jsonp?cb=tech&n=100";
    //生活界面列表数据url
    public static final String LIFEURL = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/life_1.jsonp?cb=life&n=100";
    //人物界面广告数据url
    public static final String PERSONBANNERURL = "https://people.cctv.com/?spm=C94212.P4YnMod9m2uD.0.0";
    //人物界面列表数据url
    public static final String PERSONURL = "https://api.cntv.cn/NewArticle/getArticleListByPageId?serviceId=pcnews&id=PAGEEbwoCRG76wMgJefnNvVh170118&n=100";
    //快看界面和广告列表数据url
    public static final String KUAIKANURL = "https://api.cntv.cn/NewArticle/getArticleListByPageId?serviceId=pcnews&id=PAGEXiHyb00IG6aaBlJyh6do190619,PAGEwscuo8BgMtTD0zJpO6QO190701,PAGECApcu40XdBIEv4yVmkYN190701,PAGEagUjmHOlxrXaYH6JYVWt190701,PAGEKo8KjjL3lmW2Uli5PXxL190701,PAGE9jH9hw4VQUELFNCIgl0m190701,PAGENaS69mJtgzFQScWIanwy190701,PAGEdzyvGKRKuCz4xaV1oH4w190701,PAGEohOq6oyQO1JseD8vO4oc190701,PAGEdK3IefrnlxuHBrH0rwXC191126,PAGEwrFaGLGduWmBSlMO0TSB190703,PAGE34p6UU7QRV2Ekh6UvWhZ190709&n=100";
    //军事界面广告数据url
    public static final String MILITARYBANNERURL = "https://military.cctv.com/?spm=C35449.P80754075394.0.0";
    //军事界面列表数据url
    public static final String MILITARYURL = "https://military.cctv.com/data/index.json";

    //图片文章链接组成举例，https://photo.cctv.com/ 是固定前缀，后面跟日期、id在加上后缀 .xml
    public static final String PHOTOARTICLEEXAMPLE = "https://photo.cctv.com/2020/10/24/PHOA6WM8Exq74ysFu04JbnIW201024.xml";
    public static final String PHOTOARTICLE = "https://photo.cctv.com/";
    //视频资源链接格式
    public static final String VIDEOURLEXAMPLE = "https://newcntv.qcloudcdn.com/asp/hls/1200/0303000a/3/default/1ee7edcb60de4e8b94b56cb4504e5fd4/20.ts";
    //https://newcntv.qcloudcdn.com/asp/hls/1200/0303000a/3/default/固定格式，1ee7edcb60de4e8b94b56cb4504e5fd4是VideoId，20.ts是第几个片段
    public static final String VIDEOURL = "https://newcntv.qcloudcdn.com/asp/hls/1200/0303000a/3/default/";
}
