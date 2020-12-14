package com.canplayer.music.metro.ui.Page;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.canplayer.music.R;
import com.canplayer.music.metro.animation.ViewAnimationGroup;

import java.util.ArrayList;
import java.util.List;

public class TestPage extends Fragment {

    public List<ViewAnimationGroup> PageInAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageOutAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageNextAnimGroup = new ArrayList<>();
    public List<ViewAnimationGroup> PageBackAnimGroup = new ArrayList<>();
    
    public static TestPage newInstance() {
        return new TestPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        playPageInAnim();
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    private void playPageInAnim() {
        final ViewTreeObserver viewTreeObserver = this.getView().getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                for(ViewAnimationGroup a:PageInAnimGroup){
                    a.start();
                }
                // 移除OnPreDrawListener事件监听
                TestPage.this.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}