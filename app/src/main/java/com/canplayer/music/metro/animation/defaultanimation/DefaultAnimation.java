package com.canplayer.music.metro.animation.defaultanimation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.canplayer.music.metro.animation.Baseanimation.Rotate3dAnimation;

public class DefaultAnimation{

    //默认载入动画
    public Animation inAnimation(View view,Context context) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight());

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1]+ view.getHeight()/2;

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", ""+viewX+" "+viewY+"/"+screenX+" "+screenY);
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(context,90,0,pointX,pointY,0,false);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        return rotate3dAnimation;
    }

    public Animation outAnimation(View view,Context context) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight());

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1]+ view.getHeight()/2;

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", ""+viewX+" "+viewY+"/"+screenX+" "+screenY);
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(context,0,-90,pointX,pointY,0,false);
        rotate3dAnimation.setDuration(150);
        rotate3dAnimation.setFillAfter(false);
        rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        return rotate3dAnimation;
    }
}
