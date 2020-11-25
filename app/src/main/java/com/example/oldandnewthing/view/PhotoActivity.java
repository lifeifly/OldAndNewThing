package com.example.oldandnewthing.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.BannerBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.PhotoMilitaryBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.model.beanmanager.PhotoBeanManager;

import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.view.base.BaseActivity;
import com.example.oldandnewthing.utils.CommonUtils;
import com.example.oldandnewthing.view.base.DefineView;
import com.example.oldandnewthing.widget.DigitalIndicator;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvPhotoBar;
    private Button btnBack;
    private Button shareQQ;
    private Button shareWeiXin;

    private TextView title;
    private TextView des;
    //源网址
    private String sourceUrl;
    //图片切换器
    private ImageSwitcher imageSwitcher;
    private List<PhotoBean.ListBean> listBean;
    //数字指示器
    private DigitalIndicator digitalIndicator;
    private ScrollView scPhoto;
    //图片列表
    private Gallery gallery;

    //数据源网址
    private String dataUrl;
    //控制上一张和下一张的ImageButton
    private ImageButton ibNext;
    private ImageButton ibPrev;
    //当前位置，默认为0
    private int po = 0;
    private MvpPresenter presenter;
    private PhotoBean photoBean1;
    private TextView tvLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
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
        //获取传输的数据
        sourceUrl = getIntent().getStringExtra("url");
        presenter = new MvpPresenter(this, null);
        //请求网络数据
        requestRefershPhoto(sourceUrl);
    }

    //视图初始化
    @Override
    public void initView() {
        shareQQ = (Button) findViewById(R.id.ib_share_qq);
        shareWeiXin = (Button) findViewById(R.id.ib_share_weixin);
        tvPhotoBar = (TextView) findViewById(R.id.tv_photo_bar);
        btnBack = (Button) findViewById(R.id.btn_back);
        title = (TextView) findViewById(R.id.tv_photoarticle_title);
        des = (TextView) findViewById(R.id.tv_photoarticle_des);
        gallery = (Gallery) findViewById(R.id.gallery);

        tvLoad=(TextView)findViewById(R.id.tv_load_photo);
        scPhoto=(ScrollView)findViewById(R.id.sc_photo);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.is_photoarticle);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //返回ImageView
                ImageView imageView = new ImageView(PhotoActivity.this);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });

        digitalIndicator = (DigitalIndicator) findViewById(R.id.digital_indicator);
        ibNext = (ImageButton) findViewById(R.id.ib_next);
        ibPrev = (ImageButton) findViewById(R.id.ib_prev);
        //设置点击事件
        ibPrev.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        //设置监听器

        shareWeiXin.setOnClickListener(this);
        shareQQ.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void requestRefershPhoto(String uri) {
        presenter.requestPhotoData(uri);
    }

    @Override
    public void refreshPhoto(PhotoBean photoBean) {
        tvLoad.setVisibility(View.GONE);
        scPhoto.setVisibility(View.VISIBLE);
        photoBean1 = photoBean;
        listBean = photoBean.getDatabean();
        if (listBean != null) {
            //默认显示第一个
            tvPhotoBar.setText(photoBean.getTitle());
            title.setText(photoBean.getTitle());
            des.setText(listBean.get(0).getDes());
            imageSwitcher.setImageURI(Uri.parse(listBean.get(0).getPhotoUrl()));
            //创建适配器
            GalleryAdapter adapter = new GalleryAdapter();
            gallery.setAdapter(adapter);
            //设置数字指示器
            digitalIndicator.setCount(listBean.size());
            //设置gallery的监听器
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    po = position;
                    des.setText(listBean.get(position).getDes());
                    digitalIndicator.setSelectedIndex(position + 1);
                    //ImageSwitcher跟随变化
                    imageSwitcher.setImageURI(Uri.parse(listBean.get(position).getPhotoUrl()));
                }
            });
        }

    }

    @Override
    public void requestRefreshVideo(String uri) {

    }

    @Override
    public void refreshVideo(VideoBean videoBean) {

    }

    @Override
    public Activity getContext() {
        return this;
    }



    @Override
    public void onClick(View v) {
        if (listBean != null && listBean.size() > 0) {
            switch (v.getId()) {
                case R.id.ib_next:
                    if ((po + 1) < listBean.size()) {
                        //说明后面还有图片,控制gallery选择下一个
                        po = po + 1;
                        gallery.setSelection(po);
                        //数字指示器跟随变化
                        digitalIndicator.setSelectedIndex(po + 1);
                        //描述跟随变化
                        String des = listBean.get(po).getDes();
                        //将<br>换为\n
                        String s1 = CommonUtils.replaceString(des, "<br>", "\n");
                        PhotoActivity.this.des.setText(s1);
                        //ImageSwitcher跟随变化
                        imageSwitcher.setImageURI(Uri.parse(listBean.get(po).getPhotoUrl()));
                    }
                    break;
                case R.id.ib_prev:
                    if (po > 0) {
                        po = po - 1;
                        //说明不处在第一张
                        gallery.setSelection(po);
                        //数字指示器跟随变化
                        digitalIndicator.setSelectedIndex(po + 1);
                        //描述跟随变化
                        String des = listBean.get(po).getDes();
                        //将<br>换为\n
                        String s1 = CommonUtils.replaceString(des, "<br>", "\n");
                        PhotoActivity.this.des.setText(s1);
                        //ImageSwitcher跟随变化
                        imageSwitcher.setImageURI(Uri.parse(listBean.get(po).getPhotoUrl()));
                    }
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.ib_share_qq:
                    //指定分享到QQ好友
                    ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                    Intent shareIntent = new Intent();
                    shareIntent.setComponent(comp);
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sourceUrl);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, photoBean1.getTitle());
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, "分享资源链接"));
                    break;
                case R.id.ib_share_weixin:
                    //指定分享到微信朋友
                    ComponentName comp1 = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                    Intent shareIntent1 = new Intent();
                    shareIntent1.setComponent(comp1);
                    shareIntent1.setAction(Intent.ACTION_SEND);
                    shareIntent1.putExtra(Intent.EXTRA_TEXT, sourceUrl);
                    shareIntent1.putExtra(Intent.EXTRA_SUBJECT, photoBean1.getTitle());
                    shareIntent1.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent1, "分享资源链接"));
                    break;

            }
        }
    }


    //gallery适配器
    private class GalleryAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return listBean.size();
        }

        @Override
        public Object getItem(int position) {
            return listBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(PhotoActivity.this).inflate(R.layout.item_photoarticle_gallery, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(PhotoActivity.this).load(listBean.get(position).getPhotoUrl()).into(viewHolder.imageView);
            return convertView;
        }

        class ViewHolder {
            private ImageView imageView;

            public ViewHolder(View itemView) {
                imageView = (ImageView) itemView.findViewById(R.id.iv_photoarticle_gallery_iv);
            }
        }
    }
}
