package com.canplayer.music.metro.animation.interpolator;

import android.view.animation.Interpolator;

import static java.lang.Math.sqrt;

public class MetroInterpolator implements Interpolator {
    boolean fangxiang = false;
    int power = 2;
    public MetroInterpolator(boolean fangxiang2,int power2){
        fangxiang =fangxiang2;
        power= power2;
    }
    public MetroInterpolator(int power2){
        power= power2;
    }
    public MetroInterpolator(boolean fangxiang2){
        fangxiang =fangxiang2;
    }
    public MetroInterpolator(){
    }
    @Override
    public float getInterpolation(float input) {
        if(fangxiang == false)
            return (float)(sqrt((1-Math.pow(1-input,power))));
        else
            return (float)(sqrt((Math.pow(input,power))));
//        return (float)(1-Math.pow(1-input,power));
//        else
        //return (float)(sqrt((1-Math.pow(1-input,2))));
//        if(fangxiang == false)
//        return (float)(1-Math.pow(1-input,power));
//        else
//            return (float)(Math.pow(input,power));
    }
}
