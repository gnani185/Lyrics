package com.devsparkles.lyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.devsparkles.lyrics.beans.Song;
import com.devsparkles.lyrics.utils.FavUtil;
import com.devsparkles.lyrics.utils.SongUtil;
import com.devsparkles.lyrics.utils.ThemeUtil;

public class SongActivity extends AppCompatActivity {
    private Song song = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getTheme(getApplicationContext()));
        setContentView(R.layout.activity_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SongActivity.this, FullscreenLyricActivity.class);
                i.putExtra("position", song.getId());
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        song = (Song) getIntent().getSerializableExtra("song");
        song = SongUtil.getSong(getApplicationContext(),song.getId());
        setTitle(song.getName());
        TextView lyric = (TextView) findViewById(R.id.song);
        lyric.setText(TextUtils.join("\n\n", song.getLyrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song, menu);
        MenuItem item = menu.findItem(R.id.action_favorite);
        if (song != null) {
            if (FavUtil.isFavorite(getApplicationContext(),song.getId())) {
                item.setChecked(true);
                item.setIcon(R.drawable.ic_favorite);
            } else {
                item.setChecked(false);
                item.setIcon(R.drawable.ic_favorite_border);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_favorite) {
            if (item.isCheckable()) {
                if (item.isChecked()) {
                    item.setIcon(R.drawable.ic_favorite_border);
                    FavUtil.removeFavorite(getApplicationContext(),song.getId());
                } else {
                    item.setIcon(R.drawable.ic_favorite);
                    FavUtil.addFavorite(getApplicationContext(),song.getId());
                }
                item.setChecked(!item.isChecked());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
