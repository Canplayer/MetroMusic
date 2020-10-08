package com.canplayer.music;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.canplayer.music.metro.animation.ViewAnimationGroup;
import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.OldBasePage;

import java.util.ArrayList;
import java.util.List;


public class AboutActivity extends OldBasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadPageView(R.layout.activity_about);
        super.onCreate(savedInstanceState);

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




        this.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startViewAnimation(getAnimationSubView(),true, DefaultAnimation.AnimationType.NEXT,null);
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
