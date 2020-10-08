package com.canplayer.music.metro.ui.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.canplayer.music.metro.Setting;
import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.ViewAnimationGroupListener;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;

import java.util.ArrayList;
import java.util.List;



@SuppressLint("Registered")
public class BasePage extends AppCompatActivity {

    //定义系统主题的三个状态
    private enum PageThemeSettings {
        Light,
        Black,
        Auto
    }
    //定义主题的状态
    private enum PageTheme {
        Light,
        Black
    }
    //默认动画主题
    private enum PageAnimStyle {
        Rotate,
        Null
    }

    //保存onCreate时获取到的系统主题
    private PageThemeSettings onCreateTheme;
    //当前页面的主题
    private PageTheme pageTheme;

    //
    static boolean isThemeFallowAndroid;
    //判断自己是否是新view，用来决定动画走向
    private boolean isNewPage = true;

    PageAnimStyle pageAnimStyle = PageAnimStyle.Rotate;

    //以下数值需要重置的时候保存
    //是否正在播放动画
    private static boolean isPlayAnim = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("Activity","捕获到onCreate");
        if (savedInstanceState != null) {
            isNewPage = savedInstanceState.getBoolean("isNewPage",isNewPage);
        }
        //创建的时候保存启动时系统的主题，以便从其他页面跳转回来的时候更新判断主题
        initTheme(onCreateTheme = getPageThemeFromSettings());
        super.onCreate(savedInstanceState);
        initBar();
    }
    //判断被创建的时候
    @Override
    protected void onStart() {
        Log.d("Activity","捕获到onStart");
        super.onStart();
    }
    @Override
    protected void onResume() {
        Log.d("Activity","捕获到onResume"+isNewPage);
        if(onCreateTheme != getPageThemeFromSettings()) recreate();
        super.onResume();
        if(isNewPage) {
            startPageAnimation(DefaultAnimation.AnimationType.IN,null);
        } else {
            startPageAnimation(DefaultAnimation.AnimationType.BACK,null);
        }
        isNewPage = false;
    }
    //这个方法有时候不会被执行到，很奇怪，已经抛弃
    @Override
    protected void onRestart() {
        Log.d("Activity","捕获到onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d("Activity","捕获到onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("Activity","捕获到onPause");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isNewPage",isNewPage);
        super.onSaveInstanceState(outState);
    }


    //以下是本程序提供给用户的方法
    //-在setContentView后设置动画
    public void loadPageView(int layoutResID) {
        super.setContentView(layoutResID);
    }
    //？设置主题颜色 注意，此设置不能再OnCreate中设置，否则可能会无限刷新
    public void setTheme(PageTheme theme) {
        pageTheme = theme;
        switch (theme){
            case Light:AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            case Black:AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }






    //以下是未公开给基类的方法，通常用于初始化
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
    //-读取系统系统当前主题设置
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
    //-从SettingClass取全局主题设置 此项和上面的区别是，此项用于获取，PageTheme负责执行
    private PageThemeSettings getPageThemeFromSettings() {
        switch (Setting.getGlobalPageTheme())
        {
            case Light:return PageThemeSettings.Light;
            case Black:return PageThemeSettings.Black;
            case WithAndroid:return PageThemeSettings.Auto;
        }
        return PageThemeSettings.Black;
    }
    //初始化主题，传入pageThemeSettings,按照传入的设置系统主题 注意，此设置仅能在onCreate函数执行之前调用
    private void initTheme(PageThemeSettings pageThemeSettings){
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




    //获取XML根控件,在本类中被用于设置动画
    private View getRootContentView() {
        //ViewGroup view = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        return findViewById(android.R.id.content);//view.getChildAt(0);
    }



    //四个用户自定义动画组
    public List<ViewAnimationGroup> PageInAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageOutAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageNextAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageBackAnimGroup = new ArrayList<>();
    //设置页面进入动画
    void startPageAnimation(DefaultAnimation.AnimationType animationType, final ViewAnimationGroupListener l){
        List<ViewAnimationGroup>  global = new ArrayList<>();
        ViewAnimationGroup pageAnimation = new ViewAnimationGroup();
        if(pageAnimStyle == PageAnimStyle.Rotate)switch (animationType) {
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









    //以下是对Android默认方法进行的重写

    //页面结束，此处将Android默认的推出动画关闭了。这里不推荐永华使用
    @Override @Deprecated
    public void finish() {
        //TODO 在此处实现页面跳转动画;此处有个问题，如果切换主题模式后按下返回键会发生闪烁
        super.finish();
        overridePendingTransition(0, 0);
    }

    //跳转指定页面跳转
    public void openPage(final Class newPageClass) {
        //TODO 在此处实现页面跳转动画
        //将本activity传入下一个页面，以便设置主题
        final Intent intent = new Intent(this,newPageClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startPageAnimation(DefaultAnimation.AnimationType.NEXT, new ViewAnimationGroupListener() {
            @Override
            public void onAnimationEnd() {
                startActivity(intent);
            }
        });
    }
    //-监听系统主题变化 其中实现了当主题变化的时候重新设定主题的操作
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (readSystemTheme(newConfig)){
            case Black:setTheme(PageTheme.Light);break;
            case Light:setTheme(PageTheme.Black);break;
        }
    }
    //在恢复的时候检查主题是否一致->已经转移至OnRestart

    //单纯的不推荐用户继续使用这个函数加载View
    @Override @Deprecated
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
    //改写返回键设定，添加动画
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("提示","获取到返回键事件"+ isPlayAnim);
            //TODO
            if(!isPlayAnim) {
                startPageAnimation(DefaultAnimation.AnimationType.OUT, new ViewAnimationGroupListener() {
                    @Override
                    public void onAnimationEnd() {
                        finish();
                    }
                });
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


//开发过程中抛弃的方法

//活得所有子View
//public List<View> getAllChildView(View view) {
//    List<View> subView=new ArrayList<>();
//    if(view instanceof ViewGroup){
//        for(int i = ((ViewGroup) view).getChildCount();i>0;i--){
//            View tempView = ((ViewGroup) view).getChildAt(i-1);
//            subView.addAll(getAllChildView(tempView));
//        }
//        return subView;
//    }
//    subView.add(view);
//    return subView;
//}
//
//    public void animationGroupTimer(final List<View> viewlist, final Boolean reverse, final DefaultAnimation.AnimationType animationType, final PageAnimationListener l) {
//        isPlayAnmin = true;
//        if(viewlist == null){
//            Log.d("View动画","View动画已经结束");
//            if (l != null) l.onAnimationEnd();
//            isPlayAnmin = false;
//            return;
//        }
//        //开始前先将所有的View设置为隐藏
//        if(animationType == DefaultAnimation.AnimationType.IN||animationType == DefaultAnimation.AnimationType.BACK) for(View view1 : viewlist) {
//            view1.setVisibility(View.INVISIBLE);
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final List<View> view = viewlist;
//                if(!reverse)Collections.reverse(view);
//                if(reverse) {
//                    if (view.size() != 0) {
//                        Animation animation = null;
//                        switch (animationType){
//                            case IN:animation = new DefaultAnimation().inAnimation(view.get(0), getApplicationContext());break;
//                            case OUT:animation = new DefaultAnimation().outAnimation(view.get(0), getApplicationContext());break;
//                            case BACK:animation = new DefaultAnimation().backAnimation(view.get(0), getApplicationContext());break;
//                            case NEXT:animation = new DefaultAnimation().nextAnimation(view.get(0), getApplicationContext());break;
//                        }
//                        view.get(0).startAnimation(animation);
//                        view.get(0).setVisibility(View.VISIBLE);
//                        if(view.size()>1)startViewAnimation(view.subList(1, view.size()), true,animationType,l);
//                        else {
//                            animation.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    if(l != null) l.onAnimationEnd();
//                                    isPlayAnmin = false;
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        },10);
//    }