package com.example.oldandnewthing.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    private ImageView welcomeIv;
    //需要申请的权限数组
    private String[] requestedPermissions = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"};
    //记录哪个权限未被授权
    private List<String> unPermission = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //版本大于6.0动态申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            initPermission();
        }
        //全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        welcomeIv = (ImageView) findViewById(R.id.welcome_iv);
        //加载animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        //为ImageView设置Animation
        welcomeIv.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束后开启MainActivity
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                //销毁当前activity
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
//权限判断和申请
    private void initPermission() {
        unPermission.clear();//清空没有通过的权限
        //逐个判断是否通过
        for (int i = 0; i < requestedPermissions.length; i++) {
            String requestedPermission = requestedPermissions[i];
            if (ContextCompat.checkSelfPermission(this,requestedPermission)!= PackageManager.PERMISSION_GRANTED){
                //未授权
                unPermission.add(requestedPermission);
            }
        }
        //如果存在未授权的权限,则进行申请
        if (unPermission.size()>0){
            ActivityCompat.requestPermissions(this,requestedPermissions,mRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionsDismiss=false;//记录有权限未通过
        if (mRequestCode==requestCode){
            for (int i = 0; i < grantResults.length; i++) {
                int result = grantResults[i];
                if (result==-1){
                    hasPermissionsDismiss=true;
                }
            }
            if (hasPermissionsDismiss){
                Toast.makeText(this,"权限未通过，会影响正常使用",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
