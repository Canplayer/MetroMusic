package com.canplayer.music;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;
import com.canplayer.music.metro.ui.Activity.BasePage;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPageView(R.layout.activity_about);
        this.findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<View> a =getAllChildView(getRootContentView());
                setViewInAnimation(a,true);
            }
        });
        this.findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<View> a =getAllChildView(getRootContentView());
                setViewOutAnimation(a,true);
            }
        });
        List<View> a =getAllChildView(getRootContentView());
        for(View view : a)
        {
            view.setVisibility(View.INVISIBLE);
        }
        setViewInAnimation(a,true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
    void setViewInAnimation(final List<View> view, final Boolean reverse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!reverse) {
                    int i = view.size() - 1;
                    if (i >= 0) {
                        view.get(i).startAnimation(new DefaultAnimation().inAnimation(view.get(i), AboutActivity.this));
                        view.get(i).setVisibility(View.VISIBLE);
                        setViewInAnimation(view.subList(0, i), reverse);
                    }
                }else if(reverse){
                    if (view.size() != 0) {
                        view.get(0).startAnimation(new DefaultAnimation().inAnimation(view.get(0), AboutActivity.this));
                        view.get(0).setVisibility(View.VISIBLE);
                        if(view.size()>1)setViewInAnimation(view.subList(1, view.size()), reverse);
                    }
                }
            }
        },10);
    }
    void setViewOutAnimation(final List<View> view, final Boolean reverse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!reverse) {
                    int i = view.size() - 1;
                    if (i >= 0) {
                        Animation animation = new DefaultAnimation().outAnimation(view.get(i), AboutActivity.this);
                        animation.setFillAfter(true);
                        animation.setDuration(150);
                        view.get(i).startAnimation(animation);

                        setViewOutAnimation(view.subList(0, i), reverse);
                    }
                }else if(reverse){
                    if (view.size() != 0) {
                        Animation animation = new DefaultAnimation().outAnimation(view.get(0), AboutActivity.this);
                        animation.setFillAfter(true);
                        animation.setDuration(150);
                        view.get(0).startAnimation(animation);

                        if(view.size()>1)setViewOutAnimation(view.subList(1, view.size()), reverse);
                    }
                }
            }
        },10);
    }

    //活得所有子
    List<View> getAllChildView(View view)
    {
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
