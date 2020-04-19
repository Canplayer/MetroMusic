package com.canplayer.music;

import android.os.Bundle;
import android.view.View;

import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;

import java.util.List;

public class AboutActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPageView(R.layout.activity_about);
        setAnimationSubView(getAllChildView(findViewById(android.R.id.content)));


        this.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewAnimation(getAnimationSubView(),true, DefaultAnimation.AnimationType.IN);
            }
        });
        this.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewAnimation(getAnimationSubView(),true, DefaultAnimation.AnimationType.NEXT);
            }
        });
    }





}
