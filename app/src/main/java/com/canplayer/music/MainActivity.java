package com.canplayer.music;


import android.os.Bundle;
import android.view.View;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;



public class MainActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();
        viewAnimationGroup.add(findViewById(R.id.button6), DefaultAnimation.AnimationType.IN);
        viewAnimationGroup.setTimeCell(100);
        viewAnimationGroup.setHideInStart(true);
        PageInAnimGroup.add(viewAnimationGroup);


        {
            this.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPage(AboutActivity.class);
                }
            });
            this.findViewById(R.id.btn99).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPage(HUBActivity.class);
                }
            });
        }

    }
}
