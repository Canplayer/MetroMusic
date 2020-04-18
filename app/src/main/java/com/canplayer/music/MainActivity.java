package com.canplayer.music;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;

import com.canplayer.music.metro.animation.Baseanimation.BaseRotate3dAnimation;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;
import com.canplayer.music.metro.Setting;


public class MainActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPageView(R.layout.activity_main);
        /**TODO 已完成的方法
         - onCreate中可以设置主题（要求必须在super.onCreate之前设置）
         - 可以设置Page本体的主体动画（重载loadPageView中设定）<-可以在此中手动自定义控件动画
         ？ 可以设置Page 销毁动画
         - 可以动态设置主题
         */

        this.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(MainActivity.class);
            }
        });
        this.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.setGlobalPageTheme(Setting.GlobalPageTheme.Black);
                recreate();
            }
        });
        this.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.setGlobalPageTheme(Setting.GlobalPageTheme.Light);
                recreate();
            }
        });
        this.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.setGlobalPageTheme(Setting.GlobalPageTheme.WithAndroid);
                recreate();
            }
        });
        this.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        this.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openOptionsMenu();
                openPage(AboutActivity.class);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,Menu.FIRST + 1,5,"删除").setIcon(android.R.drawable.ic_menu_delete);
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "保存").setIcon(android.R.drawable.ic_menu_edit);
        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(android.R.drawable.ic_menu_help);
        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(android.R.drawable.ic_menu_add);
        menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(android.R.drawable.ic_menu_info_details);
        menu.add(Menu.NONE, Menu.FIRST + 6, 3, "发送").setIcon(android.R.drawable.ic_menu_send);
        return true;
    }



}
