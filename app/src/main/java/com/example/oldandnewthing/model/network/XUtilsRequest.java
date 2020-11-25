package com.example.oldandnewthing.model.network;

import android.app.DownloadManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class XUtilsRequest {

    private  RequestParams params;

    public XUtilsRequest(String uri) {
        params = new RequestParams(uri);
    }
    //执行get
    public void  getRequest(Callback.CommonCallback<String> commonCallback){
        x.http().get(params,commonCallback);
    }
    //执行post
    public void postRequest(Callback.CommonCallback<String> commonCallback){
        x.http().post(params, commonCallback);
    }
}
