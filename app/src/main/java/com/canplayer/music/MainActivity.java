package com.canplayer.music;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.canplayer.music.ui.metro.Activity.BasePage;


public class MainActivity extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =(Button) this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(MainActivity.class);
            }
        });
    }



}
