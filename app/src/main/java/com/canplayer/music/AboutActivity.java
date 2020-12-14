package com.canplayer.music;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Page.BasePage;

import java.util.ArrayList;
import java.util.List;


public class AboutActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_about);

        ViewAnimationGroup viewAnimationGroup = new ViewAnimationGroup();
        for(View a :getAllChildView(findViewById(android.R.id.content))){
            viewAnimationGroup.add(a, DefaultAnimation.AnimationType.IN);
        }
        viewAnimationGroup.setTimeCell(10);
        viewAnimationGroup.setHideInStart(true);
        PageInAnimGroup.add(viewAnimationGroup);

        ViewAnimationGroup viewAnimationGroup2 = new ViewAnimationGroup();
        for(View a :getAllChildView(findViewById(android.R.id.content))){
            viewAnimationGroup2.add(a, DefaultAnimation.AnimationType.OUT);
        }
        viewAnimationGroup2.setTimeCell(10);
        PageOutAnimGroup.add(viewAnimationGroup2);

        ViewAnimationGroup viewAnimationGroup3 = new ViewAnimationGroup();
        for(View a :getAllChildView(findViewById(android.R.id.content))){
            viewAnimationGroup3.add(a, DefaultAnimation.AnimationType.NEXT);
        }
        viewAnimationGroup3.setTimeCell(10);
        PageNextAnimGroup.add(viewAnimationGroup3);

        ViewAnimationGroup viewAnimationGroup4 = new ViewAnimationGroup();
        for(View a :getAllChildView(findViewById(android.R.id.content))){
            viewAnimationGroup4.add(a, DefaultAnimation.AnimationType.BACK);
        }
        viewAnimationGroup3.setTimeCell(10);
        PageBackAnimGroup.add(viewAnimationGroup4);




        this.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ViewAnimationGroup a:PageInAnimGroup){
                    a.start();
                }

            }
        });
        this.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ViewAnimationGroup a:PageOutAnimGroup){
                    a.start();
                }
            }
        });
        this.findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ViewAnimationGroup a:PageNextAnimGroup){
                    a.start();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openPage(HUBActivity.class);
                    }
                },350);
            }
        });
        this.findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ViewAnimationGroup a:PageBackAnimGroup){
                    a.start();
                }
            }
        });
    }






    public List<View> getAllChildView(View view) {
    List<View> subView=new ArrayList<>();
    if(view instanceof ViewGroup){
        for(int i = ((ViewGroup) view).getChildCount();i>0;i--){
            View tempView = ((ViewGroup) view).getChildAt(i-1);
            subView.addAll(getAllChildView(tempView));
        }
        return subView;
    }
    subView.add(view);
    return subView;
    }
}
