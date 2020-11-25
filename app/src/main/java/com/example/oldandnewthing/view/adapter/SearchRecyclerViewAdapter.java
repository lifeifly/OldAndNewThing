package com.example.oldandnewthing.view.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //数据源
    private List<Map<String, Object>> data;
    private Context context;



    private String url;

    public SearchRecyclerViewAdapter(List<Map<String, Object>> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 1:
                View view = LayoutInflater.from(context).inflate(R.layout.item_newslist_rv_layout, parent, false);
                viewHolder = new NewsListViewHolder(view);
                break;
            case 2:
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_photolist_rv_layout, parent, false);
                viewHolder = new PhotoListViewHolder(view1);
                break;
            case 3:
                View view2 = LayoutInflater.from(context).inflate(R.layout.item_personlist_rv_layout, parent, false);
                viewHolder = new PersonListViewHolder(view2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsListViewHolder) {
            NewsListViewHolder newsListViewHolder = (NewsListViewHolder) holder;
            NewsListBean newsListBean = (NewsListBean) data.get(position).get("news");
            newsListViewHolder.tvNewsBrief.setText(newsListBean.getBrief());
            newsListViewHolder.tvNewsDate.setText(newsListBean.getDate());
            newsListViewHolder.tvNewsKeyword.setText(newsListBean.getKeywords());
            newsListViewHolder.tvNewsTitle.setText(newsListBean.getTitle());
            newsListViewHolder.url = newsListBean.getUrl();
            Picasso.with(context).load(newsListBean.getImage()).into(newsListViewHolder.ivNews);
        } else if (holder instanceof PhotoListViewHolder) {

            PhotoListViewHolder photoListViewHolder = (PhotoListViewHolder) holder;
            PhotoMilitaryBean.RollDataBean rollDataBean = (PhotoMilitaryBean.RollDataBean) data.get(position).get("roll");
            photoListViewHolder.tvPhotoContent.setText(rollDataBean.getContent());
            photoListViewHolder.tvPhotoTitle.setText(rollDataBean.getTitle());
            photoListViewHolder.tvPhotoDate.setText(rollDataBean.getDateTime());
            Picasso.with(context).load(rollDataBean.getImage()).into(photoListViewHolder.ivPhoto);
            photoListViewHolder.url = rollDataBean.getUrl();
        } else if (holder instanceof PersonListViewHolder) {
            PersonListViewHolder personListViewHolder = (PersonListViewHolder) holder;
            PersonKuaiKanBean.DataBean.ListBean listBean = (PersonKuaiKanBean.DataBean.ListBean) data.get(position).get("list");
            personListViewHolder.tvPersonBrief.setText(listBean.getDesc());
            personListViewHolder.tvPersonDate.setText(listBean.getFocus_date());
            personListViewHolder.tvPersonTitle.setText(listBean.getTitle());
            Picasso.with(context).load(listBean.getImage()).into(personListViewHolder.ivPerson);
            personListViewHolder.url = listBean.getUrl();
        }


    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        Map<String, Object> map = data.get(position);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getKey().equals("news")) {//列表
                type = 1;
            } else if (entry.getKey().equals("roll")) {//图片
                type = 2;
            } else if (entry.getKey().equals("list")) {//人物
                type = 3;
            }
        }

        return type;
    }

    //因为分3种布局，所以写3个ViewHolder
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

    //供外部实现的Item项点击接口
//回调接口
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener {

        void onItemClicked(String url);
    }
}
