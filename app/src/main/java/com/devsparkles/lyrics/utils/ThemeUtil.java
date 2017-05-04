package com.devsparkles.lyrics.utils;

import android.content.Context;

import com.devsparkles.lyrics.R;

/**
 * Created by Friends on 22-Nov-2016.
 */
public class ThemeUtil {
    public static int getTheme(Context context) {
        String theme = PrefUtil.getTheme(context);
        if (theme.equalsIgnoreCase("blue")) {
            return R.style.AppTheme_Blue;
        } else if (theme.equalsIgnoreCase("red")) {
            return R.style.AppTheme_Red;
        } else {
            return R.style.AppTheme;
        }
    }
}
