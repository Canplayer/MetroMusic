package com.canplayer.music.ui.metro.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


interface ThemeChanger{
    void setTheme();

}





public class BasePage extends AppCompatActivity implements ThemeChanger {

    //0-暗色主题，1-亮色主题
    static private int appTheme = 1;
    //是否跟随系统
    static boolean themeWithSystem = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBar();
    }

    //设置沉浸
    private void initBar(){
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if (appTheme==1)getWindow().getDecorView().setSystemUiVisibility(option|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (appTheme==2)getWindow().getDecorView().setSystemUiVisibility(option);

    }
    //设置主题为浅色
    private void setLightTheme()
    {
        appTheme = 1;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
}
    //设置主题为深色
    private void setNightTheme()
    {
        appTheme = 0;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        recreate();
    }

    //设置主题
    public void setAppTheme(int theme) {
        switch (theme)
        {
            case 0:{
                //TODO 获取系统主题状态，日后实现
            } break;
            case 2:setLightTheme();break;
            default:setNightTheme();
        }
    }


    //监听系统主题变化
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(themeWithSystem) {
            int mSysThemeConfig = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (mSysThemeConfig) {
                case Configuration.UI_MODE_NIGHT_NO:
                    Log.d("系统主题", "切换到亮色");
                    setLightTheme();
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    Log.d("系统主题", "切换到暗色");
                    setNightTheme();
                    break;
            }
        }
    }

    //页面跳转
    public void openPage(Class newPageClass) {
        //TODO 在此处实现页面跳转动画
        //将本activity传入下一个页面，以便设置主题
        this.getClass();
        Intent intent = new Intent(this,newPageClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        //TODO 在此处实现页面跳转动画;此处有个问题，如果切换主题模式后按下返回键会发生闪烁
        
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void setTheme() {
        recreate();
    }
}

