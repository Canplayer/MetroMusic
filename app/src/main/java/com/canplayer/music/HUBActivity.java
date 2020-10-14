package com.canplayer.music;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;

import java.util.ArrayList;
import java.util.List;

public class HUBActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_hubtest);

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
