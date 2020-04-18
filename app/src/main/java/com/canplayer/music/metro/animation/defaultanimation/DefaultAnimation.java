package com.canplayer.music.metro.animation.defaultanimation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.canplayer.music.metro.animation.Baseanimation.BaseRotate3dAnimation;

public class DefaultAnimation{

    //默认载入动画
    public Animation inAnimation(View view,Context context) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,90,0},new float[]{0,0,0},pointX,pointY,0,false,context);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        return rotate3dAnimation;
    }
    public Animation outAnimation(View view,Context context) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,0,0},new float[]{0,-90,0},pointX,pointY,0,false,context);
        rotate3dAnimation.setDuration(150);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        return rotate3dAnimation;
    }
    public Animation inAnimation_Page(int WindowHeight,Context context) {
        int screenX = 0;
        int screenY = WindowHeight/2;

        int viewX = 0;
        int viewY = 0;

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,90,0},new float[]{0,0,0},pointX,pointY,0,false,context);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        return rotate3dAnimation;
    }


    public Animation rollingAnimation(View view,Context context) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight());

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1]+ view.getHeight()/2;

        int pointX= view.getWidth()/2;
        int pointY = view.getHeight()/2;
        Log.d("获取控件位置信息", ""+viewX+" "+viewY+"/"+screenX+" "+screenY);
        //BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(context,180,0,pointX,pointY,0,false);
        //rotate3dAnimation.setDuration(500);
        //rotate3dAnimation.setFillAfter(false);
        //rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        return null;
    }
}
