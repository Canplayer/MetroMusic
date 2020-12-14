package com.canplayer.music;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.animation.interpolator.MetroInterpolator;
import com.canplayer.music.metro.ui.Page.BasePage;

import java.util.ArrayList;

public class HUBActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_hubtest);

        ((TextView)findViewById(R.id.editText)).setText("850");
        ((TextView)findViewById(R.id.editText2)).setText("4000");
        ((TextView)findViewById(R.id.editText3)).setText("1300");
        ((TextView)findViewById(R.id.editText4)).setText("1500");
        ((TextView)findViewById(R.id.editText5)).setText("1300");
        ((TextView)findViewById(R.id.editText6)).setText("4");
        ((TextView)findViewById(R.id.editText7)).setText("2");

        ViewPager2 viewPager2 = findViewById(R.id.hubbody);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(new HubPagerAdapter());
        RecyclerView recyclerView  = (RecyclerView)viewPager2.getChildAt(0);
        recyclerView.setPadding(30,0,30,0);
        recyclerView.setClipToPadding(false);
        viewPager2.setCurrentItem(20,false);





        ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();

        viewAnimationGroup.add(findViewById(R.id.hubpage), DefaultAnimation.AnimationType.IN);

        Animation animation2 = new TranslateAnimation(4000, 0, 0, 0);
        animation2.setInterpolator(new MetroInterpolator());
        animation2.setDuration(1300);
        viewAnimationGroup.add(findViewById(R.id.hubtitle), animation2);

        Animation animation3 = new TranslateAnimation(1500, 0, 0, 0);
        animation3.setInterpolator(new MetroInterpolator());
        animation3.setDuration(1300);
        viewAnimationGroup.add(findViewById(R.id.hubbody), animation3);
        viewAnimationGroup.setHideInStart(true);
        PageInAnimGroup.add(viewAnimationGroup);
        this.findViewById(R.id.buttonshowhub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageInAnimGroup = new ArrayList<>();
                ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();
                ViewAnimationGroup viewAnimationGroup2 = new ViewAnimationGroup();

                Animation animation1 = new DefaultAnimation().nextAnimation(findViewById(R.id.hubpage));
                animation1.setInterpolator(new MetroInterpolator(true,5));
                animation1.setDuration(150);
                viewAnimationGroup2.add(findViewById(R.id.hubpage), animation1);

        Animation animation0 = new DefaultAnimation().inAnimation(findViewById(R.id.hubpage));
        animation0.setInterpolator(new MetroInterpolator(false,Integer.parseInt(((TextView)findViewById(R.id.editText7)).getText().toString())));
        animation0.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubpage), animation0);

                Animation animation2 = new TranslateAnimation(Integer.parseInt(((TextView)findViewById(R.id.editText2)).getText().toString()), 0, 0, 0);
                animation2.setInterpolator(new MetroInterpolator(false,Integer.parseInt(((TextView)findViewById(R.id.editText6)).getText().toString())));
                animation2.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText3)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubtitle), animation2);

                Animation animation3 = new TranslateAnimation(Integer.parseInt(((TextView)findViewById(R.id.editText4)).getText().toString()),0, 0, 0);
                animation3.setInterpolator(new MetroInterpolator(false,Integer.parseInt(((TextView)findViewById(R.id.editText6)).getText().toString())));
                animation3.setDuration(Integer.parseInt(((TextView)findViewById(R.id.editText5)).getText().toString()));
                viewAnimationGroup.add(findViewById(R.id.hubbody), animation3);
                viewAnimationGroup.setHideInStart(true);
                PageInAnimGroup.add(viewAnimationGroup);
                PageNextAnimGroup.add(viewAnimationGroup2);




                for(ViewAnimationGroup a:PageNextAnimGroup){
                    a.start();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(ViewAnimationGroup a:PageInAnimGroup){
                            a.start();
                        }
                    }
                },180);
            }
        });

    }
}
