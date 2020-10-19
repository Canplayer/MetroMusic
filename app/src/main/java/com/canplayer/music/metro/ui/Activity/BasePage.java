package com.canplayer.music.metro.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

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

public class BasePage extends Activity {

    public List<ViewAnimationGroup> PageInAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageOutAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageNextAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageBackAnimGroup = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        restore(savedInstanceState);
        super.onCreate(savedInstanceState);
        initSystemBar();
        playPageInAnim();
    }

    private void playPageInAnim() {
        final ViewTreeObserver viewTreeObserver = this.getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                for(ViewAnimationGroup a:PageInAnimGroup){
                    a.start();
                }
                // 移除OnPreDrawListener事件监听
                BasePage.this.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }


    //恢复数据
    private void restore(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            //恢复数据
        }
    }

    public void openPage(final Class newPageClass) {
        //TODO 在此处实现页面跳转动画
        //将本activity传入下一个页面，以便设置主题
        final Intent intent = new Intent(this,newPageClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
//        startPageAnimation(DefaultAnimation.AnimationType.NEXT, new ViewAnimationGroupListener() {
//            @Override
//            public void onAnimationEnd() {
//                startActivity(intent);
//            }
//        });
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

    //保存状态
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isNewPage?",true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        for(ViewAnimationGroup a:PageOutAnimGroup){
            a.start();
        }
        super.finish();

    }

    void startPageAnimation(List<ViewAnimationGroup> animation, final ViewAnimationGroupListener l){
        ViewAnimationGroup pageAnimation = new ViewAnimationGroup();

        animation.add(pageAnimation);
        pageAnimation.setAnimationListener(new ViewAnimationGroupListener() {
            @Override
            public void onAnimationEnd() {

                if(l != null)l.onAnimationEnd();
            }
        });
        new ViewAnimationGroup().startMultiAnimation(animation);
    }
}
