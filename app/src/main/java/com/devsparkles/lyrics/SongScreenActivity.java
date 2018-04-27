package com.devsparkles.lyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devsparkles.lyrics.adapter.SongsScreenAdapter;
import com.devsparkles.lyrics.beans.Song;
import com.devsparkles.lyrics.comparator.SongComparator;
import com.devsparkles.lyrics.utils.FavUtil;
import com.devsparkles.lyrics.utils.SongUtil;
import com.devsparkles.lyrics.utils.ThemeUtil;

import java.util.Collections;
import java.util.List;

public class SongScreenActivity extends AppCompatActivity {
    MenuItem item;
    private ViewPager viewPager;
    private SongsScreenAdapter adapter;
    private Song song;
    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getTheme(getApplicationContext()));
        setContentView(R.layout.activity_song_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SongScreenActivity.this, FullscreenLyricActivity.class);
                i.putExtra("song", song);
                startActivity(i);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);
        songs = SongUtil.getSongs(getApplicationContext()).getSongs();
        //Sorting it since HomeFragment shows sorted order
        Collections.sort(songs, new SongComparator());
        adapter = new SongsScreenAdapter(SongScreenActivity.this,
                songs);

        song = (Song) getIntent().getSerializableExtra("song");
        int position = getIntent().getIntExtra("position", 1);
        //song = SongUtil.getSong(getApplicationContext(),song.getId());
        setTitle(song.getName());
        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                song = songs.get(position);//SongUtil.getSong(getApplicationContext(),songs.get(position).getId());
            }

            @Override
            public void onPageSelected(int position) {
                song = songs.get(position);//SongUtil.getSong(getApplicationContext(),songs.get(position).getId());
                setTitle(song.getName());
                if (item != null) {
                    if (song != null) {
                        if (FavUtil.isFavorite(getApplicationContext(), song.getId())) {
                            item.setChecked(true);
                            item.setIcon(R.drawable.ic_favorite);
                        } else {
                            item.setChecked(false);
                            item.setIcon(R.drawable.ic_favorite_border);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song, menu);
        item = menu.findItem(R.id.action_favorite);
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
