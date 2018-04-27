package com.devsparkles.lyrics.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.devsparkles.lyrics.R;
import com.devsparkles.lyrics.beans.Song;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Friends on 15-Nov-2016.
 */
public class SongsScreenAdapter extends PagerAdapter{
    private Activity _activity;
    private List<Song> songs;
    private LayoutInflater inflater;

    // constructor
    public SongsScreenAdapter(Activity activity,
                                  List<Song> songs) {
        this._activity = activity;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return this.songs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ScrollView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView lyric;
        TextView slideNo;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.content_song, container,
                false);

        lyric = (TextView) viewLayout.findViewById(R.id.song);
        Song song = songs.get(position);//getSong(container.getContext(),songs.get(position).getId());
        lyric.setText(TextUtils.join("\n\n", song.getLyrics()));
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ScrollView) object);

    }

    private Song getSong(Context context,String id) {
        InputStream ip = context.getResources().openRawResource(context.getResources().getIdentifier("s" + id, "raw", context.getPackageName()));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ip, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Song song = gson.fromJson(reader, Song.class);
        return song;
    }
}
