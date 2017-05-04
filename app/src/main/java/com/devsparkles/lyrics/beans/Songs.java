package com.devsparkles.lyrics.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Songs {

    @SerializedName("songs")
    @Expose
    private List<Song> songs = new ArrayList<Song>();

    /**
     * @return The songs
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * @param songs The songs
     */
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}