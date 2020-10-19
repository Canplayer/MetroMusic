package com.canplayer.music;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;

import java.util.ArrayList;

public class HUBActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_hubtest);

        ((TextView)findViewById(R.id.editText)).setText("10000");
        ((TextView)findViewById(R.id.editText2)).setText("2500");
        ((TextView)findViewById(R.id.editText3)).setText("10000");
        ((TextView)findViewById(R.id.editText4)).setText("600");
        ((TextView)findViewById(R.id.editText5)).setText("10000");

        ViewPager2 viewPager2 = findViewById(R.id.hubbody);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(new HubPagerAdapter());
        RecyclerView recyclerView  = (RecyclerView)viewPager2.getChildAt(0);
        recyclerView.setPadding(0,0,100,0);
        recyclerView.setClipToPadding(false);
        viewPager2.setCurrentItem(20,false);





        ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();

        viewAnimationGroup.add(findViewById(R.id.hubpage), DefaultAnimation.AnimationType.IN);

        Animation animation2 = new TranslateAnimation(2500, 0, 0, 0);
        animation2.setInterpolator(new LinearOutSlowInInterpolator());
        animation2.setDuration(1000);
        viewAnimationGroup.add(findViewById(R.id.hubtitle), animation2);

        Animation animation3 = new TranslateAnimation(1000, 0, 0, 0);
        animation3.setInterpolator(new LinearOutSlowInInterpolator());
        animation3.setDuration(1000);
        viewAnimationGroup.add(findViewById(R.id.hubbody), animation3);
        viewAnimationGroup.setHideInStart(true);
        PageInAnimGroup.add(viewAnimationGroup);
        this.findViewById(R.id.buttonshowhub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageInAnimGroup = new ArrayList<>();
                ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();

        Animation animation0 = new DefaultAnimation().inAnimation(findViewById(R.id.hubpage));
        animation0.setInterpolator(new LinearOutSlowInInterpolator());
        animation0.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubpage), animation0);

                Animation animation2 = new TranslateAnimation(Integer.parseInt(((TextView)findViewById(R.id.editText2)).getText().toString()), 0, 0, 0);
                animation2.setInterpolator(new LinearOutSlowInInterpolator());
                animation2.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText3)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubtitle), animation2);

                Animation animation3 = new TranslateAnimation(Integer.parseInt(((TextView)findViewById(R.id.editText4)).getText().toString()),0, 0, 0);
                animation3.setInterpolator(new LinearOutSlowInInterpolator());
                animation3.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText5)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubbody), animation3);
                viewAnimationGroup.setHideInStart(true);
                PageInAnimGroup.add(viewAnimationGroup);




                for(ViewAnimationGroup a:PageInAnimGroup){
                    a.start();
                }
            }
        });

    }
}
