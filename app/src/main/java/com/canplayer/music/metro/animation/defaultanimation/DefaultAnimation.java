package com.canplayer.music.metro.animation.defaultanimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.canplayer.music.metro.animation.Baseanimation.BaseRotate3dAnimation;

public class DefaultAnimation{
    public enum AnimationType {
        IN, OUT, NEXT, BACK;
    }



    //默认载入动画
    public Animation inAnimation(View view) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,90,0},new float[]{0,0,0},pointX,pointY,0,false,view.getContext());
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        rotate3dAnimation.setFillAfter(true);
        return rotate3dAnimation;
    }
    public Animation outAnimation(final View view) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,0,0},new float[]{0,90,0},pointX,pointY,0,false,view.getContext());
        rotate3dAnimation.setDuration(150);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        rotate3dAnimation.setFillAfter(true);
        return rotate3dAnimation;
    }
    public Animation nextAnimation(final View view) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,0,0},new float[]{0,-90,0},pointX,pointY,0,false,view.getContext());
        rotate3dAnimation.setDuration(150);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        rotate3dAnimation.setFillAfter(true);
        return rotate3dAnimation;
    }
    public Animation backAnimation(View view) {
        int screenX = 0;
        int screenY = (view.getRootView().getHeight())/2;

        int[] viewXY = new int[2];
        view.getLocationInWindow(viewXY);

        int viewX = viewXY[0];
        int viewY = viewXY[1];

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,-90,0},new float[]{0,0,0},pointX,pointY,0,false,view.getContext());
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new  LinearOutSlowInInterpolator());
        rotate3dAnimation.setFillAfter(true);
        return rotate3dAnimation;
    }

    public Animation inAnimation_Page(Context context) {
        int screenX = 0;
        int screenY = context.getApplicationContext().getResources().getDisplayMetrics().heightPixels/2;

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
    public Animation backAnimation_Page(Context context) {
        int screenX = 0;
        int screenY = context.getApplicationContext().getResources().getDisplayMetrics().heightPixels/2;

        int viewX = 0;
        int viewY = 0;

        int pointX= screenX-viewX;
        int pointY = screenY-viewY;
        Log.d("获取控件位置信息", viewX+" "+viewY+"/"+screenX+" "+screenY+"/"+pointX+" "+pointY);
        BaseRotate3dAnimation rotate3dAnimation = new BaseRotate3dAnimation(new float[]{0,-90,0},new float[]{0,0,0},pointX,pointY,0,false,context);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setInterpolator(new  LinearOutSlowInInterpolator());
        rotate3dAnimation.setFillAfter(true);
        return rotate3dAnimation;
    }



}
