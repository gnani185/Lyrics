package com.devsparkles.lyrics.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.devsparkles.lyrics.R;

/**
 * Created by Friends on 11-Nov-2016.
 */
public class UserPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference themePref = findPreference(getString(R.string.key_app_theme));
        if(((ListPreference) themePref).getEntry()!=null) {
            themePref.setSummary(((ListPreference) themePref).getEntry());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* get preference */
        Preference preference = findPreference(key);

        /* update summary */
        if (key.equals(getString(R.string.key_app_theme))) {
            preference.setSummary(((ListPreference) preference).getEntry());
        }
        getActivity().recreate();
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
