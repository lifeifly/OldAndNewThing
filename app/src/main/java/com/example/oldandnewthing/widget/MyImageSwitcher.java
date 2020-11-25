package com.example.oldandnewthing.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.squareup.picasso.Picasso;

//因为原生ImageSwitcher无法加载网络图片
public class MyImageSwitcher extends ImageSwitcher {

    public MyImageSwitcher(Context context) {
        super(context);
    }

    public MyImageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageURI(Uri uri) {
        ImageView imageView = (ImageView) this.getNextView();
        Picasso.with(getContext()).load(uri).into(imageView);
        showNext();
    }
}
