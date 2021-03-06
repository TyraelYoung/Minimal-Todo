package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

import wang.tyrael.todo.R;
import wang.tyrael.todo.biz.theme.ThemeBiz;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_layout);

   }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceKeys preferenceKeys = new PreferenceKeys(getResources());
        if(key.equals(preferenceKeys.night_mode_pref_key)){
            SharedPreferences themePreferences = getActivity().getSharedPreferences(ThemeBiz.THEME_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor themeEditor = themePreferences.edit();
            //We tell our MainLayout to recreate itself because mode has changed
            themeEditor.putBoolean(ThemeBiz.RECREATE_ACTIVITY, true);

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference(preferenceKeys.night_mode_pref_key);
            if(checkBoxPreference.isChecked()){
                //Comment out this line if not using Google Analytics

                themeEditor.putString(ThemeBiz.THEME_SAVED, ThemeBiz.DARKTHEME);
            }
            else{
                themeEditor.putString(ThemeBiz.THEME_SAVED, ThemeBiz.LIGHTTHEME);
            }
            themeEditor.apply();

            getActivity().recreate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
