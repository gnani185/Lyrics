package com.devsparkles.lyrics.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.devsparkles.lyrics.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Friends on 27-Oct-2016.
 */
public class PrefUtil {

    public static void putFavorites(Context context, Set<String> favorites) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.key_favorites), TextUtils.join(",",favorites));
        editor.commit();
    }
    public static Set<String> getFavorites(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String fav = sharedPreferences.getString(context.getString(R.string.key_favorites),"");
        Set<String> favorites = new HashSet<String>(Arrays.asList(fav.split(",")));
        return favorites;
    }

    public static String getTheme(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPreferences.getString(context.getString(R.string.key_app_theme),"");
        return theme;
    }
}
