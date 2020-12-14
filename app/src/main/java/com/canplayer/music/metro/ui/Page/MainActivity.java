package com.canplayer.music.metro.ui.Page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.canplayer.music.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TestPage.newInstance())
                    .commitNow();
        }
    }
}