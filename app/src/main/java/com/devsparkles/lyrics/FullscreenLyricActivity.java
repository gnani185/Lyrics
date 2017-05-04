package com.devsparkles.lyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.devsparkles.lyrics.adapter.FullScreenLyricsAdapter;
import com.devsparkles.lyrics.utils.SongUtil;
import com.devsparkles.lyrics.utils.ThemeUtil;

import java.util.ArrayList;

public class FullscreenLyricActivity extends AppCompatActivity {

    private FullScreenLyricsAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getTheme(getApplicationContext()));
        setContentView(R.layout.activity_fullscreen_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        String position = i.getExtras().getString("position");

        adapter = new FullScreenLyricsAdapter(FullscreenLyricActivity.this,
                (ArrayList<String>) SongUtil.getSong(getApplicationContext(),position).getLyrics());

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(0);
    }

}
