package com.example.oldandnewthing.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.BannerBean;
import com.example.oldandnewthing.model.bean.MiPerBannerBean;
import com.example.oldandnewthing.model.bean.PersonKuaiKanBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    //3种不同的数据源
    private List<BannerBean> bannerBeanList;
    private List<MiPerBannerBean> miPerKuaiBannerBeans;
    private List<PersonKuaiKanBean.DataBean.ListBean> listBeans;
    //上下文
    private Context context;
    //用数字区别需要的布局类型，0为除了普通新闻布局，1为photo新闻，2为人物,3为军事，4为快看
    private int category;

    public GalleryAdapter(List<BannerBean> bannerBeanList, List<MiPerBannerBean> miPerKuaiBannerBeans, List<PersonKuaiKanBean.DataBean.ListBean> listBeans, Context context, int category) {
        this.miPerKuaiBannerBeans = miPerKuaiBannerBeans;
        this.listBeans = listBeans;
        this.bannerBeanList = bannerBeanList;
        this.context = context;
        this.category = category;
    }

    //数据源长度大于1，返回最大值，可以实现从最后滑到第一个效果
    @Override
    public int getCount() {
        int banck = 1;
        switch (category) {
            case 0:
                if (bannerBeanList.size() > 1) {
                    banck = Integer.MAX_VALUE;
                    return banck;
                }

                break;
            case 1:
                if (bannerBeanList.size() > 1) {
                    banck = Integer.MAX_VALUE;
                    return banck;
                }

                break;
            case 2:
                if (miPerKuaiBannerBeans != null) {
                    if (miPerKuaiBannerBeans.size() > 1) {
                        banck = Integer.MAX_VALUE;
                        return banck;
                    }
                }
                break;
            case 3:
                if (miPerKuaiBannerBeans != null) {
                    if (miPerKuaiBannerBeans.size() > 1) {
                        banck = Integer.MAX_VALUE;
                        return banck;
                    }
                }
                break;
            case 4:
                if (listBeans != null) {
                    if (listBeans.size() > 1) {
                        banck = Integer.MAX_VALUE;
                        return banck;
                    }
                }
                break;
        }
        return banck;
    }

    @Override
    public Object getItem(int position) {
        if (bannerBeanList != null) {
            return bannerBeanList.get(position);
        } else if (miPerKuaiBannerBeans != null) {
            return miPerKuaiBannerBeans.get(position);
        }
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //需要实现从最后到第一，用%
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (context != null) {
            //根据不同类型，创建不同的视图View
            switch (category) {
                case 0:
                    NewsViewHolder newsViewHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_news_gallery_layout, null);
                        newsViewHolder = new NewsViewHolder(convertView);
                        convertView.setTag(newsViewHolder);
                    } else {
                        newsViewHolder = (NewsViewHolder) convertView.getTag();
                    }
                    //用对应的数据源填充
                    BannerBean bannerBean = bannerBeanList.get(position % bannerBeanList.size());
                    newsViewHolder.tvText.setText(bannerBean.getText());
                    newsViewHolder.tvTitle.setText(bannerBean.getTitle());
                    newsViewHolder.url = bannerBean.getHref();
                    Picasso.with(context).load("http:" + bannerBean.getSrcHref()).into(newsViewHolder.iv);
                    break;
                case 1:
                    PhotoViewHolder photoViewHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_photo_gallery_layout, parent, false);
                        photoViewHolder = new PhotoViewHolder(convertView);
                        convertView.setTag(photoViewHolder);
                    } else {
                        photoViewHolder = (PhotoViewHolder) convertView.getTag();
                    }
                    BannerBean bannerBean1 = bannerBeanList.get(position % bannerBeanList.size());
                    photoViewHolder.tvPhotoGalleryTitle.setText(bannerBean1.getTitle());
                    photoViewHolder.tvPhotoGalleryText.setText(bannerBean1.getText());
                    photoViewHolder.url = bannerBean1.getHref();
                    Picasso.with(context).load("http:" + bannerBean1.getSrcHref()).into(photoViewHolder.ivPhotoGallery);
                    break;
                case 2:
                    MiPerKuaiViewHolder miPerKuaiViewHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_permikuai_gallery_layout, parent, false);
                        miPerKuaiViewHolder = new MiPerKuaiViewHolder(convertView);
                        convertView.setTag(miPerKuaiViewHolder);
                    } else {
                        miPerKuaiViewHolder = (MiPerKuaiViewHolder) convertView.getTag();
                    }
                    MiPerBannerBean miPerBannerBean = miPerKuaiBannerBeans.get(position % miPerKuaiBannerBeans.size());
                    miPerKuaiViewHolder.tvMPK.setText(miPerBannerBean.getDes());
                    miPerKuaiViewHolder.url = miPerBannerBean.getHref();
                    Picasso.with(context).load("http:" + miPerBannerBean.getSrc()).into(miPerKuaiViewHolder.ivMPK);
                    break;
                case 3:
                    MiPerKuaiViewHolder miPerKuaiViewHolder1 = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_permikuai_gallery_layout, parent, false);
                        miPerKuaiViewHolder1 = new MiPerKuaiViewHolder(convertView);
                        convertView.setTag(miPerKuaiViewHolder1);
                    } else {
                        miPerKuaiViewHolder1 = (MiPerKuaiViewHolder) convertView.getTag();
                    }
                    MiPerBannerBean miPerBannerBean1 = miPerKuaiBannerBeans.get(position % miPerKuaiBannerBeans.size());
                    miPerKuaiViewHolder1.tvMPK.setText(miPerBannerBean1.getDes());
                    miPerKuaiViewHolder1.url = miPerBannerBean1.getHref();
                    Picasso.with(context).load("http:" + miPerBannerBean1.getSrc()).into(miPerKuaiViewHolder1.ivMPK);
                    break;
                case 4:
                    MiPerKuaiViewHolder miPerKuaiViewHolder2 = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_permikuai_gallery_layout, parent, false);
                        miPerKuaiViewHolder2 = new MiPerKuaiViewHolder(convertView);
                        convertView.setTag(miPerKuaiViewHolder2);
                    } else {
                        miPerKuaiViewHolder2 = (MiPerKuaiViewHolder) convertView.getTag();
                    }
                    PersonKuaiKanBean.DataBean.ListBean listBean = listBeans.get(position % listBeans.size());
                    miPerKuaiViewHolder2.tvMPK.setText(listBean.getTitle());
                    miPerKuaiViewHolder2.url = listBean.getUrl();
                    Picasso.with(context).load(listBean.getImage()).into(miPerKuaiViewHolder2.ivMPK);
                    break;
            }
        }

        return convertView;
    }

    //普通新闻广告Viewholder
    class NewsViewHolder {
        private ImageView iv;
        private TextView tvTitle;
        private TextView tvText;
        private String url;

        public NewsViewHolder(View itemView) {
            iv = (ImageView) itemView.findViewById(R.id.iv_item);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvText = (TextView) itemView.findViewById(R.id.tv_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(url);
                    }
                }
            });
        }
    }

    //photo新闻广告Viewholder
    class PhotoViewHolder {
        private String url;
        private ImageView ivPhotoGallery;
        private TextView tvPhotoGalleryTitle;
        private TextView tvPhotoGalleryText;

        public PhotoViewHolder(View itemView) {
            ivPhotoGallery = (ImageView) itemView.findViewById(R.id.iv_photobanner);
            tvPhotoGalleryTitle = (TextView) itemView.findViewById(R.id.tv_photobanner_title);
            tvPhotoGalleryText = (TextView) itemView.findViewById(R.id.tv_photobanner_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(url);
                    }
                }
            });
        }
    }

    //军事、人物、快看新闻广告Viewholder
    class MiPerKuaiViewHolder {
        private ImageView ivMPK;
        private TextView tvMPK;
        private String url;

        public MiPerKuaiViewHolder(View itemView) {
            ivMPK = (ImageView) itemView.findViewById(R.id.iv_pmk);
            tvMPK = (TextView) itemView.findViewById(R.id.tv_pmk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(url);
                    }
                }
            });
        }
    }

    //回调接口
    private GalleryAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(GalleryAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener {

        void onItemClicked(String url);
    }
}
