package com.devsparkles.lyrics.utils;

import android.content.Context;
import android.util.Log;

import com.devsparkles.lyrics.R;
import com.devsparkles.lyrics.beans.Song;
import com.devsparkles.lyrics.beans.Songs;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Friends on 16-Nov-2016.
 */
public class SongUtil {
    public static Song getSong(Context context,String id) {
        Log.i("SONGGGGGGGGGGGG", id);
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

    public static Songs getSongs(Context context){
        InputStream ip = context.getResources().openRawResource(R.raw.songs);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ip, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Songs songs = gson.fromJson(reader, Songs.class);
        return songs;
    }
    public static ArrayList<Song> searchSong(Context context,String q) {
        Songs songs = getSongs(context);
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs.getSongs()) {
            if (song.getName().startsWith(q)) {
                songList.add(song);
            }
        }
        return songList;
    }
    public static ArrayList<Song> searchFavoriteSong(Context context,String q) {
        Songs songs = getSongs(context);
        Set<String> favorites = PrefUtil.getFavorites(context);
        ArrayList<Song> songList = new ArrayList<>();
        for (Song song : songs.getSongs()) {
            if (favorites.contains(song.getId()) && song.getName().startsWith(q)) {
                songList.add(song);
            }
        }
        return songList;
    }
}
