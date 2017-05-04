package com.devsparkles.lyrics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devsparkles.lyrics.R;
import com.devsparkles.lyrics.beans.Song;

import java.util.List;

/**
 * Created by Friends on 16-Oct-2016.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {
    Context context;
    private List<Song> songs;

    public SongsAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_song_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song question = songs.get(position);
        holder.name.setText(question.getName());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
