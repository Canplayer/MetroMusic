package com.canplayer.music.metro.ui.View;

import android.content.Context;
import android.view.View;

public class MetroView extends View {



    private boolean isAnimationView = false;

    public void setIsAnimationView(boolean animationView) {
        isAnimationView = animationView;
    }
    public boolean getIsAnimationView() {
        return isAnimationView;
    }

    public MetroView(Context context) {
        super(context);
    }
}
