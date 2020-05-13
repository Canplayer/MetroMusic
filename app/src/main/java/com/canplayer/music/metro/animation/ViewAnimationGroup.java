package com.canplayer.music.metro.animation;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

import com.canplayer.music.metro.animation.defaultanimation.DefaultAnimation;

import java.util.ArrayList;
import java.util.List;

public class ViewAnimationGroup{
    List<View> views = new ArrayList<>();
    List<Animation> animations = new ArrayList<>();
    List<Integer> viewVisibility = new ArrayList<>();
    List<DefaultAnimation.AnimationType> animationTypes = new ArrayList<>();
    ViewAnimationGroupListener viewAnimationGroupListener = null;

    int timeCell = 0;
    boolean isHideInStart = false;

    public void add(View itemView,Animation itemAnimation){
        views.add(itemView);
        animations.add(itemAnimation);
        animationTypes.add(null);
    }
    public void add(View itemView, DefaultAnimation.AnimationType animationType){
        views.add(itemView);
        animations.add(null);
        animationTypes.add(animationType);
    }

    public void start(){
        //如果开始的时候要全部隐藏，先将所有的View可见性状态保存起来
        if(isHideInStart)
            for(View v : views){
                v.clearAnimation();
            viewVisibility.add(v.getVisibility());
            if(v.getVisibility() != View.GONE)
                v.setVisibility(View.INVISIBLE);
        }

        if (timeCell == 0)startAnimation();
        else startAnimation(timeCell,true,0);
    }
//    public void viewAnimationGroupPlayer(final List<ViewAnimationGroup> viewList, final int time , final Boolean isHideInStart) {
//        if(viewList == null){
//            return;
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final List<ViewAnimationGroup> view = viewList;
//                if (view.size() != 0) {
//                    ViewAnimationGroup viewAnimation = view.get(0);
//                    viewAnimation.startAnimation();
//                    if(isHideInStart)viewAnimation.setVisibilityInStart(View.VISIBLE);
//                    if(view.size()>1)viewAnimationGroupPlayer(view.subList(1, view.size()),time,isHideInStart);
//                    else {
//                        viewAnimation.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) { }
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                animationListener.onAnimationEnd(animation);
//                            }
//                            @Override
//                            public void onAnimationRepeat(Animation animation) { }
//                        });
//                    }
//                }
//            }
//        },time);
//    }

    public void setAnimationListener(ViewAnimationGroupListener SetViewAnimationGroupListener) {
        viewAnimationGroupListener = SetViewAnimationGroupListener;
    }

    public void setHideInStart(boolean hideInStart) {
        isHideInStart = hideInStart;
    }

    public void setTimeCell(int timeCell) {
        this.timeCell = timeCell;
    }

    private void startAnimation() {

        for(int i = 0;i<views.size();i++){

            Animation animation = null;
            if(animationTypes.get(i) == null) animation = animations.get(i);
            else switch (animationTypes.get(i)) {
                case IN:animation = new DefaultAnimation().inAnimation(views.get(i));break;
                case OUT:animation = new DefaultAnimation().outAnimation(views.get(i));break;
                case BACK:animation = new DefaultAnimation().backAnimation(views.get(i));break;
                case NEXT:animation = new DefaultAnimation().nextAnimation(views.get(i));break;
            }
            if(i+1 == animations.size()) animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(viewAnimationGroupListener != null)viewAnimationGroupListener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            views.get(i).startAnimation(animation);
            if(isHideInStart)views.get(i).setVisibility(viewVisibility.get(i));
        }
    }

    private void startAnimation(final int timeCell, final Boolean isHideInStart, final int index) {
        if(index==animations.size()) return;
        Animation animation = null;
        if(animationTypes.get(index) == null) animation = animations.get(index);
        else switch (animationTypes.get(index)) {
            case IN:animation = new DefaultAnimation().inAnimation(views.get(index));break;
            case OUT:animation = new DefaultAnimation().outAnimation(views.get(index));break;
            case BACK:animation = new DefaultAnimation().backAnimation(views.get(index));break;
            case NEXT:animation = new DefaultAnimation().nextAnimation(views.get(index));break;
        }
        if(index+1 == animations.size()) animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(viewAnimationGroupListener != null)viewAnimationGroupListener.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        views.get(index).startAnimation(animation);
        views.get(index).setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation(timeCell,isHideInStart,index+1);
            }
        },timeCell);
    }

    public void cleanAllFlag(){
        for(View v:views){
            v.clearAnimation();
        }
    }

    public void startMultiAnimation(List<ViewAnimationGroup> MultiAnimation) {
        for(ViewAnimationGroup v : MultiAnimation){
            v.start();
        }
    }

    private Animation getFirstAnimation() {
        return animations.get(0);
    }

    private Animation getEndAnimation(){
        Animation animation = getFirstAnimation();
        for(Animation a:animations){
            if(a.getDuration() > +animation.getDuration()){
                animation = a;
            }
        }
        return animation;
    }


}
