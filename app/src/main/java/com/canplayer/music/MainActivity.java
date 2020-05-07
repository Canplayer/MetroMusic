package com.canplayer.music;


import android.os.Bundle;
import android.view.View;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
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
        ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();
        viewAnimationGroup.add(findViewById(R.id.button2), new DefaultAnimation().inAnimation(findViewById(R.id.button2)));
        viewAnimationGroup.add(findViewById(R.id.button3), new DefaultAnimation().inAnimation(findViewById(R.id.button3)));
        viewAnimationGroup.add(findViewById(R.id.button4), new DefaultAnimation().inAnimation(findViewById(R.id.button4)));
        viewAnimationGroup.setTimeCell(500);
        viewAnimationGroup.setHideInStart(true);
        PageInAnimGroup.add(viewAnimationGroup);


        {
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

    }
}
