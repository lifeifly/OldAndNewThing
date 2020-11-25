package com.example.oldandnewthing.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.widget.NestedScrollView;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.model.beanmanager.ArticleBeanManager;
import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.view.base.BaseActivity;
import com.example.oldandnewthing.utils.CommonUtils;
import com.example.oldandnewthing.view.base.DefineView;
import com.example.oldandnewthing.widget.MyScrollView;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArticleActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private TextView tvTitle;
    private TextView tvSource;
    private TextView tvEditor;
    private TextView tvKeyword;
    private VideoView videoView;
    private LinearLayout ll;
    private TextView tvArticleBar;
    private Button backBtn;
    private Button shareQQ;
    private Button shareWeiXin;
    private MyScrollView scrollView;
    //视频资源播放进度，第几个片段
    private int progressIndex = 0;
    //后续视图添加在ll中的索引
    private int childIndex = 3;
    private MvpPresenter presenter;
    private String resUrl;
    private ArticleBean articleBean1;
    private String url;
    private MediaController mediaController;
    private TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
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
        //获取传来的链接
        url = getIntent().getStringExtra("url");

        //拼接成资源链接
        resUrl = CommonUtils.changeHref(url, "xml");

        presenter = new MvpPresenter(this, null);
//请求数据
        requestRefreshArticle(resUrl);
    }

    //视图初始化
    @Override
    public void initView() {
        scrollView = (MyScrollView) findViewById(R.id.scrollview_article);
        shareQQ = (Button) findViewById(R.id.ib_share_qq);
        shareWeiXin = (Button) findViewById(R.id.ib_share_weixin);
        tvArticleBar = (TextView) findViewById(R.id.tv_article_bar);
        backBtn = (Button) findViewById(R.id.btn_back);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        mediaController = new MediaController(ArticleActivity.this);
        tvTitle = (TextView) findViewById(R.id.tv_newsarticle_title);
        tvSource = (TextView) findViewById(R.id.tv_newsarticle_source);
        tvEditor = (TextView) findViewById(R.id.tv_newsarticle_editor);
        tvKeyword = (TextView) findViewById(R.id.tv_newsarticle_keyword);
        videoView = (VideoView) findViewById(R.id.video_view);
        tvLoad=(TextView)findViewById(R.id.tv_load_article);
        //设置VedioView与Controller关联
        videoView.setMediaController(mediaController);
        ll = (LinearLayout) findViewById(R.id.ll);
        //先获取之前的Padding

        //设置监听器

        shareWeiXin.setOnClickListener(this);
        shareQQ.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ib_share_qq:
                //指定分享到QQ好友
                ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                Intent shareIntent = new Intent();
                shareIntent.setComponent(comp);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                if (articleBean1 != null) {
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, articleBean1.getTitle());
                }
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "分享资源链接"));
                break;
            case R.id.ib_share_weixin:
                //指定分享到微信朋友
                ComponentName comp1 = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                Intent shareIntent1 = new Intent();
                shareIntent1.setComponent(comp1);
                shareIntent1.setAction(Intent.ACTION_SEND);
                shareIntent1.putExtra(Intent.EXTRA_TEXT, url);
                if (articleBean1 != null) {
                    shareIntent1.putExtra(Intent.EXTRA_SUBJECT, articleBean1.getTitle());
                }
                shareIntent1.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent1, "分享资源链接"));
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void requestRefreshArticle(String uri) {
        presenter.requestArticleData(uri);
    }


    @Override
    public void refreshArticle(final ArticleBean articleBean) {
        tvLoad.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        articleBean1 = articleBean;
        List<Map<String, String>> data = articleBean.getData();
        //更新UI
        tvArticleBar.setText(articleBean.getTitle());
        tvTitle.setText(articleBean.getTitle());
        tvSource.setText(articleBean.getSource() + " | " + articleBean.getTime());
        tvEditor.setText("编辑: " + articleBean.getEditor());
        tvKeyword.setText("关键词: " + articleBean.getKeyword());
        //将文本和图片数据显示出来
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> stringMap = data.get(i);
            Set<Map.Entry<String, String>> entries = stringMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                switch (entry.getKey()) {
                    case "img":
                        //属于图片数据
                        ImageView imageView = new ImageView(ArticleActivity.this);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        param.bottomMargin = 10;
                        //设置居中显示
                        param.gravity = Gravity.CENTER;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面
                        imageView.setLayoutParams(param);
                        //利用Picasso框架加载网络图片
                        Picasso.with(ArticleActivity.this).load(entry.getValue()).into(imageView);
                        ll.addView(imageView, childIndex++);
                        break;
                    case "bigtitle": {
                        TextView textView = new TextView(ArticleActivity.this);
                        textView.setText(entry.getValue() + "\n");
                        textView.setTextSize(15);
                        //设置加粗
                        textView.setTypeface(Typeface.DEFAULT_BOLD);
                        //设置黑色
                        textView.setTextColor(getResources().getColor(R.color.color_black));
                        //添加到ll中
                        ll.addView(textView, childIndex++);
                        break;
                    }
                    case "text": {
                        TextView textView = new TextView(ArticleActivity.this);
                        if (articleBean.getTitle().contains("联播+")) {
                            //返回的数据不含空格，需要添加
                            textView.setText("\t\t\t\t" + entry.getValue() + "\n");
                        } else {
                            textView.setText(entry.getValue() + "\n");
                        }
                        //设置黑色
                        textView.setTextColor(getResources().getColor(R.color.color_black));
                        textView.setTextSize(15);
                        //添加到ll中
                        ll.addView(textView, childIndex++);
                        break;
                    }
                    case "imgdes":
                        TextView tvImageDes = new TextView(ArticleActivity.this);
                        tvImageDes.setText(entry.getValue() + "\n");
                        tvImageDes.setGravity(Gravity.CENTER_HORIZONTAL);
                        tvImageDes.setTextSize(15);
                        ll.addView(tvImageDes, childIndex++);
                        break;
                }
            }
        }
        if (!TextUtils.isEmpty(articleBean.getVideoUrl())) {
            //视频资源不为空
            //显示VideoView
            relativeLayout.setVisibility(View.VISIBLE);
            //为vieoview设置资源链接
            videoView.setVideoPath(articleBean.getVideoUrl() + "/" + progressIndex + ".ts");
            //获取焦点
            videoView.requestFocus();
            //播放视频
            videoView.start();
            //设置播放结束监听器,播放结束时调用
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //更新进度索引
                    progressIndex += 1;
                    videoView.setVideoPath(articleBean.getVideoUrl() + "/" + progressIndex + ".ts");
                    //获取焦点
                    videoView.requestFocus();
                    //重新开始
                    videoView.start();
                }
            });
            //设置MediaController不跟随scrollview滚动
            scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
                @Override
                public void onScrollStart() {
                    //滑动开始,隐藏mediacontroller
                    mediaController.hide();
                }

                @Override
                public void onScrollEnd() {
                    mediaController.isShowing();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭VideoView
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }


}