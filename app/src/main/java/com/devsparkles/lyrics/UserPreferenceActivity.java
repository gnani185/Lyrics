package com.devsparkles.lyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.devsparkles.lyrics.fragment.UserPreferenceFragment;
import com.devsparkles.lyrics.utils.ThemeUtil;

/**
 * Created by Friends on 11-Nov-2016.
 */
public class UserPreferenceActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getTheme(getApplicationContext()));
        setContentView(R.layout.layout_preference);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new UserPreferenceFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(UserPreferenceActivity.this,MainActivity.class));
    }
}