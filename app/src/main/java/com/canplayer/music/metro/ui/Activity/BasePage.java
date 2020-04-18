package com.canplayer.music.metro.ui.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.canplayer.music.metro.Setting;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;


@SuppressLint("Registered")
public class BasePage extends AppCompatActivity {

    private enum PageThemeSettings {
        Light,
        Black,
        Auto
    }
    private enum PageTheme {
        Light,
        Black
    }
    static boolean isThemeFallowAndroid;
    private PageTheme pageTheme;
    private PageThemeSettings onCreateTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme(onCreateTheme = pageThemeSettings());
        super.onCreate(savedInstanceState);
        initBar();
    }

    //-在setContentView后设置动画
    public void loadPageView(int layoutResID) {
        super.setContentView(layoutResID);
        InPageAnim();
    }
    public void loadPageView(int layoutResID, animationSetter l) {
        super.setContentView(layoutResID);
        getRootContentView().startAnimation(l.animation());
    }
    public void loadPageView(int layoutResID,boolean isAnimationOFF) {
        super.setContentView(layoutResID);
        if (!isAnimationOFF){
            InPageAnim();
        }
    }

    //-设置沉浸,由于设置状态栏沉浸需要API23以上，未来如需要兼容旧版本可以去掉沉浸
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initBar(){
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if(isThemeFallowAndroid) {
            switch (readSystemTheme(getResources().getConfiguration())) {
                case Black:
                    pageTheme = PageTheme.Black;
                    break;
                case Light:
                    pageTheme = PageTheme.Light;
                    break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (pageTheme == PageTheme.Light)
                getWindow().getDecorView().setSystemUiVisibility(option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            if (pageTheme == PageTheme.Black)
                getWindow().getDecorView().setSystemUiVisibility(option);
        }

    }

    //-设置主题为浅色
    public void setLightTheme() {
        pageTheme = PageTheme.Light;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
    }
    //-设置主题为深色
    public void setBlackTheme() {
        pageTheme = PageTheme.Black;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        recreate();
    }

    //-获取系统当前主题
    private PageTheme readSystemTheme(Configuration newConfig) {
        if(isThemeFallowAndroid) {
            int mSysThemeConfig = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (mSysThemeConfig) {
                case Configuration.UI_MODE_NIGHT_NO:
                    return PageTheme.Light;
                case Configuration.UI_MODE_NIGHT_YES:
                    return PageTheme.Black;
            }
        }
        return PageTheme.Black;
    }

    //-监听系统主题变化
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (readSystemTheme(newConfig)){
            case Black:setBlackTheme();break;
            case Light:setLightTheme();break;
        }
    }
    //初始化时用于获取Android当前主题
    public void initTheme(PageThemeSettings pageThemeSettings){
        Log.d("初始化","");
        switch (pageThemeSettings) {
            case Auto:
                isThemeFallowAndroid = true;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case Light:
                isThemeFallowAndroid = false;
                pageTheme = PageTheme.Light;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);break;
            case Black:
                isThemeFallowAndroid = false;
                pageTheme = PageTheme.Black;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);break;
        }
    }

    //-从SettingClass取全局主题设置
    private PageThemeSettings pageThemeSettings() {
        switch (Setting.getGlobalPageTheme())
        {
            case Light:return PageThemeSettings.Light;
            case Black:return PageThemeSettings.Black;
            case WithAndroid:return PageThemeSettings.Auto;
        }
        return PageThemeSettings.Black;
    }

    //TODO 待优化回到该页面的时候检查主题设置是否一致
    @Override
    protected void onResume() {
        super.onResume();
        if(onCreateTheme != pageThemeSettings()) recreate();
    }

    //获取XML根控件,在本类中被用于设置动画
    public View getRootContentView() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        return view.getChildAt(0);
    }

    //设置页面进入动画
    private void InPageAnim() {
        final View view = getRootContentView();
        Animation animation = new DefaultAnimation().inAnimation_Page(getApplicationContext().getResources().getDisplayMetrics().heightPixels,BasePage.this);
        getRootContentView().startAnimation(animation);
        view.post(new Runnable() {
            @Override
            public void run() {
//                Animation animation = new DefaultAnimation().inAnimation(view,BasePage.this);
//                getRootContentView().startAnimation(animation);
            }
        });
    }

    //页面结束
    @Override
    public void finish() {
        //TODO 在此处实现页面跳转动画;此处有个问题，如果切换主题模式后按下返回键会发生闪烁

        super.finish();
        overridePendingTransition(0, 0);
    }

    //页面跳转
    public void openPage(final Class newPageClass) {
        //TODO 在此处实现页面跳转动画
        //将本activity传入下一个页面，以便设置主题
        final Intent intent = new Intent(this,newPageClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Animation animation = new DefaultAnimation().outAnimation(getRootContentView(),this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },100);

        //animation.setFillAfter(true);
        getRootContentView().startAnimation(animation);

    }
    


}

