package com.devsparkles.lyrics.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devsparkles.lyrics.R;

import java.util.ArrayList;

/**
 * Created by Friends on 28-Oct-2016.
 */
public class FullScreenLyricsAdapter extends PagerAdapter {
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public FullScreenLyricsAdapter(Activity activity,
                                   ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView lyric;
        TextView slideNo;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen, container,
                false);

        lyric = (TextView) viewLayout.findViewById(R.id.lyric);
        slideNo = (TextView) viewLayout.findViewById(R.id.slide_no);
        lyric.setText(_imagePaths.get(position));
        int slide = position + 1;
        slideNo.setText(slide +"/" + _imagePaths.size());
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
