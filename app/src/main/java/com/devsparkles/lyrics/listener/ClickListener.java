package com.devsparkles.lyrics.listener;

import android.view.View;

/**
 * Created by Friends on 08-May-2016.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
