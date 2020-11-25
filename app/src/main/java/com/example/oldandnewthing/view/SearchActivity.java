package com.example.oldandnewthing.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;
import com.example.oldandnewthing.presenter.MvpPresenter;
import com.example.oldandnewthing.view.adapter.QucikAdapter;
import com.example.oldandnewthing.view.adapter.SearchRecyclerViewAdapter;
import com.example.oldandnewthing.view.base.BaseActivity;
import com.example.oldandnewthing.view.base.DefineView;
import com.example.oldandnewthing.widget.OpenActivity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText;
    private Button searBtn;
    private RecyclerView rvSearch;
    private TextView tvError;
    private MvpPresenter presenter;
    private Button backBtn;
    private SearchRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        presenter = new MvpPresenter(this, null);
    }

    @Override
    public void initView() {
        backBtn = (Button) findViewById(R.id.btn_back);
        editText = (EditText) findViewById(R.id.et_search);
        tvError = (TextView) findViewById(R.id.tv_error);
        rvSearch = (RecyclerView) findViewById(R.id.rv_search);
        searBtn = (Button) findViewById(R.id.btn_search);
        backBtn.setOnClickListener(this);
        searBtn.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //文本改变时，隐藏之前的搜索结果
                if (tvError.getVisibility()==View.VISIBLE){
                    tvError.setVisibility(View.GONE);
                }
                if (rvSearch.getVisibility()==View.VISIBLE){
                    rvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void requestSearch(String keyword) {
        presenter.requestSearchData(keyword);
    }

    @Override
    public void refreshSearchError() {
        if (tvError.getVisibility()==View.GONE){
            tvError.setVisibility(View.VISIBLE);
        }
        if (rvSearch.getVisibility()==View.VISIBLE){
            rvSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshSearchSuccess(List<Map<String, Object>> data) {
        if (rvSearch.getVisibility()==View.GONE){
            rvSearch.setVisibility(View.VISIBLE);
        }
        if (tvError.getVisibility()==View.VISIBLE){
            tvError.setVisibility(View.GONE);
        }
        Log.d("155", String.valueOf(new Date().getTime()));
        adapter = new SearchRecyclerViewAdapter(data, this);
        rvSearch.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSearch.setLayoutManager(layoutManager);
        //设置recyclerview的监听器
        adapter.setOnItemClickedListener(new SearchRecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(String url) {
                try {
                    OpenActivity.openActivity(url, SearchActivity.this, new Intent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                String s = editText.getText().toString();
                requestSearch(s);
                break;
        }
    }
}
