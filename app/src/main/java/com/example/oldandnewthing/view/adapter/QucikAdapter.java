package com.example.oldandnewthing.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.NewsListBean;
import com.example.oldandnewthing.model.bean.PersonKuaiKanBean;
import com.example.oldandnewthing.model.bean.PhotoMilitaryBean;
import com.example.oldandnewthing.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

//
public class QucikAdapter extends RecyclerView.Adapter {
    //3种数据源
    //除了人物、军事、图片、快看的数据源
    private List<NewsListBean> newsListBeans;
    //人物快看的数据源
    private List<PersonKuaiKanBean.DataBean.ListBean> listBeans;
    //图片军事的数据源
    private List<PhotoMilitaryBean.RollDataBean> rollDataBeans;
    private Context context;
    //布局
    private int layoutIdRes;
    //指定分类的常量,0代表除了图片军事人物快看，1代表图片，2代表人物，3代表军事，4代表快看
    private int category;
    private View view;


    public QucikAdapter(List<NewsListBean> newsListBeans, List<PersonKuaiKanBean.DataBean.ListBean> listBeans, List<PhotoMilitaryBean.RollDataBean> rollDataBeans, Context context, int layoutIdRes, int category) {
        this.newsListBeans = newsListBeans;
        this.rollDataBeans = rollDataBeans;
        this.listBeans = listBeans;
        this.context = context;
        this.layoutIdRes = layoutIdRes;
        this.category = category;
    }

    //创建ViewhOlder并进行初始化
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (context != null) {
            view = LayoutInflater.from(context).inflate(layoutIdRes, parent, false);
        }

        switch (category) {
            case 0:
                viewHolder = new NewsListViewHolder(view);
                break;
            case 1:
                viewHolder = new PhotoListViewHolder(view);
                break;
            case 2:
                viewHolder = new PersonListViewHolder(view);
                break;
            case 3:
                viewHolder = new MilitaryListViewHolder(view);
                break;
            case 4:
                viewHolder = new KuaiKanListViewHolder(view);
                break;
        }
        return viewHolder;
    }

    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (category) {
            case 0:
                NewsListBean newsListBean = newsListBeans.get(position);
                NewsListViewHolder newsListViewHolder = (NewsListViewHolder) holder;
                newsListViewHolder.tvNewsTitle.setText(newsListBean.getTitle());
                newsListViewHolder.tvNewsKeyword.setText(newsListBean.getKeywords());
                newsListViewHolder.tvNewsDate.setText(newsListBean.getDate());
                newsListViewHolder.tvNewsBrief.setText(newsListBean.getBrief());
                Picasso.with(context).load(newsListBean.getImage()).into(newsListViewHolder.ivNews);
                newsListViewHolder.url = newsListBean.getUrl();
                break;
            case 1:
                PhotoMilitaryBean.RollDataBean rollDataBean = rollDataBeans.get(position);
                PhotoListViewHolder photoListViewHolder = (PhotoListViewHolder) holder;
                photoListViewHolder.tvPhotoContent.setText(rollDataBean.getContent());
                photoListViewHolder.tvPhotoTitle.setText(rollDataBean.getTitle());
                photoListViewHolder.tvPhotoDate.setText(rollDataBean.getDateTime());
                Picasso.with(context).load(rollDataBean.getImage()).into(photoListViewHolder.ivPhoto);
                photoListViewHolder.url = rollDataBean.getUrl();
                break;
            case 2:
                PersonKuaiKanBean.DataBean.ListBean listBean = listBeans.get(position);
                PersonListViewHolder personListViewHolder = (PersonListViewHolder) holder;
                personListViewHolder.tvPersonTitle.setText(listBean.getTitle());
                //返回的时间是毫秒数，需要转换成年月日
                personListViewHolder.tvPersonDate.setText(CommonUtils.miToDate(listBean.getFocus_date()));
                personListViewHolder.tvPersonBrief.setText(listBean.getDesc());
                Picasso.with(context).load(listBean.getImage()).into(personListViewHolder.ivPerson);
                personListViewHolder.url = listBean.getUrl();
                break;
            case 3:
                PhotoMilitaryBean.RollDataBean rollDataBean1 = rollDataBeans.get(position);
                MilitaryListViewHolder militaryListViewHolder = (MilitaryListViewHolder) holder;
                militaryListViewHolder.tvMilitaryTitle.setText(rollDataBean1.getTitle());
                militaryListViewHolder.tvMilitaryDate.setText(rollDataBean1.getDateTime());
                militaryListViewHolder.tvMilitaryKeyword.setText(rollDataBean1.getContent());
                militaryListViewHolder.tvMilitaryBrief.setText(rollDataBean1.getDescription());
                Log.d("TAG", rollDataBean1.getImage());
                if (!rollDataBean1.getImage().equals("")) {
                    Picasso.with(context).load(rollDataBean1.getImage()).into(militaryListViewHolder.ivMilitary);
                }
                militaryListViewHolder.url = rollDataBean1.getUrl();
                break;
            case 4:
                PersonKuaiKanBean.DataBean.ListBean listBean1 = listBeans.get(position);
                KuaiKanListViewHolder kuaiKanListViewHolder = (KuaiKanListViewHolder) holder;
                kuaiKanListViewHolder.tvKuaiKanDate.setText(CommonUtils.miToDate(listBean1.getFocus_date()));
                kuaiKanListViewHolder.tvKuaiKanContent.setText(listBean1.getDesc());
                Picasso.with(context).load(listBean1.getImage()).into(kuaiKanListViewHolder.ivKuaiKan);
                kuaiKanListViewHolder.url = listBean1.getUrl();
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (listBeans != null) {
            return listBeans.size();
        } else if (newsListBeans != null) {
            return newsListBeans.size();
        }
        return rollDataBeans.size();
    }


    //因为分5种布局，所以写5个ViewHolder
    public class NewsListViewHolder extends RecyclerView.ViewHolder {
        private String url;
        private ImageView ivNews;
        private TextView tvNewsTitle;
        private TextView tvNewsBrief;
        private TextView tvNewsKeyword;
        private TextView tvNewsDate;

        public NewsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = (ImageView) itemView.findViewById(R.id.iv_news);
            tvNewsBrief = (TextView) itemView.findViewById(R.id.tv_news_brief);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            tvNewsKeyword = (TextView) itemView.findViewById(R.id.tv_news_keywords);
            tvNewsDate = (TextView) itemView.findViewById(R.id.tv_news_date);
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

    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        private String url;
        private ImageView ivPhoto;
        private TextView tvPhotoTitle;
        private TextView tvPhotoContent;
        private TextView tvPhotoDate;

        public PhotoListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvPhotoTitle = (TextView) itemView.findViewById(R.id.tv_photo_title);
            tvPhotoContent = (TextView) itemView.findViewById(R.id.tv_photo_keyword);
            tvPhotoDate = (TextView) itemView.findViewById(R.id.tv_photo_date);
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

    public class PersonListViewHolder extends RecyclerView.ViewHolder {
        private String url;
        private ImageView ivPerson;
        private TextView tvPersonTitle;
        private TextView tvPersonBrief;
        private TextView tvPersonDate;

        public PersonListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPerson = (ImageView) itemView.findViewById(R.id.iv_person);
            tvPersonBrief = (TextView) itemView.findViewById(R.id.tv_person_text);
            tvPersonTitle = (TextView) itemView.findViewById(R.id.tv_person_title);
            tvPersonDate = (TextView) itemView.findViewById(R.id.tv_person_date);
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

    public class MilitaryListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMilitary;
        private TextView tvMilitaryTitle;
        private TextView tvMilitaryBrief;
        private TextView tvMilitaryDate;
        private TextView tvMilitaryKeyword;
        private String url;

        public MilitaryListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMilitary = (ImageView) itemView.findViewById(R.id.iv_military);
            tvMilitaryBrief = (TextView) itemView.findViewById(R.id.tv_military_text);
            tvMilitaryTitle = (TextView) itemView.findViewById(R.id.tv_military_title);
            tvMilitaryDate = (TextView) itemView.findViewById(R.id.tv_military_date);
            tvMilitaryKeyword = (TextView) itemView.findViewById(R.id.tv_military_keyword);
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

    public class KuaiKanListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivKuaiKan;
        private TextView tvKuaiKanContent;
        private TextView tvKuaiKanDate;
        private String url;

        public KuaiKanListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivKuaiKan = (ImageView) itemView.findViewById(R.id.iv_kuaikan);
            tvKuaiKanContent = (TextView) itemView.findViewById(R.id.tv_kuaikan_content);
            tvKuaiKanDate = (TextView) itemView.findViewById(R.id.tv_kuaikan_date);
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

    //供外部实现的Item项点击接口
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener {

        void onItemClicked(String url);
    }
}
