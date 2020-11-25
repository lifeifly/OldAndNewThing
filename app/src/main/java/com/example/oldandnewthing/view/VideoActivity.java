package com.example.oldandnewthing.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.model.url.MainURL;
import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.utils.CommonUtils;
import com.example.oldandnewthing.view.base.BaseActivity;
import com.example.oldandnewthing.view.base.DefineView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class VideoActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvVideoBar;
    private Button btnBack;
    private Button shareQQ;
    private Button shareWeiXin;

    private TextView tvHeader;

    private TextView tvKeyword;

    private TextView tvBreif;

    private TextView tvEditor;

    private VideoView videoView;
    private MvpPresenter presenter;
    private MediaController controller;
    //记录当前视频播放的索引
    private int progressIndex = 0;
    private String s;
    private VideoBean videoBean1;
    private String url;

    private TextView tvLoad;
    private ScrollView scVideo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
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
        url = getIntent().getStringExtra("url");
        //拼接成资源链接
        s = CommonUtils.changeHref(url, "xml");
        presenter = new MvpPresenter(this, null);
        requestRefreshVideo(s);
    }



    @Override
    public void requestRefreshVideo(String uri) {
        presenter.requestVideoData(uri);
    }

    @Override
    public void refreshVideo(final VideoBean videoBean) {
        tvLoad.setVisibility(View.GONE);
        scVideo.setVisibility(View.VISIBLE);
        videoBean1 = videoBean;
        tvVideoBar.setText(videoBean.getHeader());
        tvBreif.setText(videoBean.getBreif());
        tvEditor.setText(videoBean.getEditor());

        tvHeader.setText(videoBean.getHeader());
        tvKeyword.setText(videoBean.getKeyword());
        videoView.setVideoPath(MainURL.VIDEOURL + videoBean.getCenterId() + "/" + progressIndex + ".ts");
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                progressIndex++;
                videoView.setVideoPath(MainURL.VIDEOURL + videoBean.getCenterId() + "/" + progressIndex + ".ts");
                videoView.start();
            }
        });
    }

    @Override
    public void initView() {
        tvLoad=(TextView)findViewById(R.id.tv_load_video);
        scVideo=(ScrollView)findViewById(R.id.sc_video);
        shareQQ=(Button)findViewById(R.id.ib_share_qq);
        shareWeiXin=(Button)findViewById(R.id.ib_share_weixin);
        tvVideoBar = (TextView) findViewById(R.id.tv_video_bar);
        btnBack = (Button) findViewById(R.id.btn_back);
        tvKeyword = (TextView) findViewById(R.id.tv_video_keyword);
        tvHeader = (TextView) findViewById(R.id.tv_video_header);
        tvEditor = (TextView) findViewById(R.id.tv_video_editor);
        tvBreif = (TextView) findViewById(R.id.tv_video_brief);
        videoView = (VideoView) findViewById(R.id.video_video_view);
        controller = new MediaController(this);
        //设置videoview的关联mediacontroller
        videoView.setMediaController(controller);

        shareWeiXin.setOnClickListener(this);
        shareQQ.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }


    @Override
    public Activity getContext() {
        return this;
    }



    @Override
    protected void onStop() {
        super.onStop();
        //关闭VideoView
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ib_share_qq:
                //指定分享到QQ好友
                ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                Intent shareIntent = new Intent();
                shareIntent.setComponent(comp);
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putExtra(Intent.EXTRA_TEXT,url);
                if (videoBean1!=null){
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,videoBean1.getHeader());
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
                shareIntent1.putExtra(Intent.EXTRA_TEXT,url);
                if (videoBean1!=null){
                    shareIntent1.putExtra(Intent.EXTRA_SUBJECT,videoBean1.getHeader());
                }
                shareIntent1.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent1, "分享资源链接"));
                break;

        }
    }
}
