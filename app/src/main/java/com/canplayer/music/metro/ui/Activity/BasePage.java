package com.canplayer.music.metro.ui.Activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.canplayer.music.metro.Setting;
import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.ViewAnimationGroupListener;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;

import java.util.ArrayList;
import java.util.List;

public class BasePage extends AppCompatActivity {
    public enum PageTheme{Light,Black,Auto}

    PageTheme pageTheme = PageTheme.Auto;
    public List<ViewAnimationGroup> PageInAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageOutAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageNextAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageBackAnimGroup = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { 
        restore(savedInstanceState);
        initTheme();
        super.onCreate(savedInstanceState);
        initSystemBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for(ViewAnimationGroup a:PageInAnimGroup){
            a.start();
        }
    }

    //初始化主题颜色
    private void initTheme() {
        switch (pageTheme) {
            case Light: AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);break;
            case Black:AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);break;
            default: switch (Setting.getGlobalPageTheme()){
                case Light: AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);break;
                case Black:AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);break;
                default:AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            };

        }
    }

    //恢复数据
    private void restore(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            //恢复数据
            switch (savedInstanceState.getInt("pageThemeSetting")){
                case 1:pageTheme=PageTheme.Light;break;
                case 2:pageTheme=PageTheme.Black;break;
                default:pageTheme=PageTheme.Auto;
            }
        }
    }

    //初始化沉浸/控件颜色
    private void initSystemBar() {
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int mSysThemeConfig = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (mSysThemeConfig) {
                case Configuration.UI_MODE_NIGHT_NO:
                    getWindow().getDecorView().setSystemUiVisibility(option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                    break;
                default:
                    getWindow().getDecorView().setSystemUiVisibility(option);
            }
        }

    }

    //设置主题
    public void setTheme(PageTheme theme) {
        pageTheme = theme;
        switch (theme){
            case Light:pageTheme=PageTheme.Light;break;
            case Auto:pageTheme=PageTheme.Auto;break;
            default:pageTheme=PageTheme.Black;
        }
        recreate();
    }
    //保存状态
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int pageThemeNub =0;
        switch (pageTheme){
            case Light:pageThemeNub=1;break;
            case Black:pageThemeNub=2;break;
            default:pageThemeNub=0;
        }
        outState.putBoolean("isNewPage?",true);
        outState.putInt("pageThemeSetting",pageThemeNub);
        super.onSaveInstanceState(outState);
    }

    //监听主题变化
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(pageTheme == PageTheme.Auto){
            if(Setting.getGlobalPageTheme()== Setting.GlobalPageTheme.WithAndroid){
                recreate();
            }
        }
    }

    void startPageAnimation(List<ViewAnimationGroup> animation, final ViewAnimationGroupListener l){
        List<ViewAnimationGroup>  global = new ArrayList<>();
        ViewAnimationGroup pageAnimation = new ViewAnimationGroup();
        if(pageAnimStyle == OldBasePage.PageAnimStyle.Rotate)switch (animationType) {
            case IN: pageAnimation.add(getRootContentView(),new DefaultAnimation().inAnimation_Page(getBaseContext()));break;
            case OUT: pageAnimation.add(getRootContentView(),new DefaultAnimation().outAnimation(getRootContentView()));break;
            case NEXT: pageAnimation.add(getRootContentView(),new DefaultAnimation().nextAnimation(getRootContentView()));break;
            case BACK: pageAnimation.add(getRootContentView(),new DefaultAnimation().backAnimation_Page(getBaseContext()));break;
        }
        switch (animationType) {
            case IN: global.addAll(PageInAnimGroup);break;
            case OUT: global.addAll(PageOutAnimGroup);break;
            case NEXT: global.addAll(PageNextAnimGroup);break;
            case BACK: global.addAll(PageBackAnimGroup);break;
        }
        global.add(pageAnimation);
        pageAnimation.setAnimationListener(new ViewAnimationGroupListener() {
            @Override
            public void onAnimationEnd() {

                if(l != null)l.onAnimationEnd();
            }
        });
        new ViewAnimationGroup().startMultiAnimation(global);
    }
}
