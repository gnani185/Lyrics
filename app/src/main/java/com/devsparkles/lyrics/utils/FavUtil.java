package com.devsparkles.lyrics.utils;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Friends on 11-Nov-2016.
 */
public class FavUtil {
    public static boolean isFavorite(Context context, String id) {
        boolean isFavorite = false;
        Set<String> favorites = PrefUtil.getFavorites(context);
        if (favorites != null) {
            isFavorite = favorites.contains(id);
        }
        return isFavorite;
    }
    public static boolean addFavorite(Context context, String id) {
        boolean isAdded = false;
        Set<String> favorites = PrefUtil.getFavorites(context);
        if (favorites != null) {
            if (!favorites.contains(id)) {
                isAdded = favorites.add(id);
            }
        } else {
            favorites = new HashSet<String>();
            isAdded = favorites.add(id);
        }
        if (isAdded) {
            PrefUtil.putFavorites(context, favorites);
        }
        return isAdded;
    }
    public static boolean removeFavorite(Context context, String id) {
        boolean isRemoved = false;
        Set<String> favorites = PrefUtil.getFavorites(context);
        if (favorites != null) {
            if (favorites.contains(id)) {
                isRemoved = favorites.remove(id);
            }
        }
        if (isRemoved) {
            PrefUtil.putFavorites(context, favorites);
        }
        return isRemoved;
    }
}
