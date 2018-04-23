package com.danesfeder.daynightexample;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import static android.content.res.Configuration.UI_MODE_NIGHT_NO;
import static android.content.res.Configuration.UI_MODE_NIGHT_YES;

public class DayNightActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    initNightMode();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_day_night);

    FloatingActionButton fabRefresh = findViewById(R.id.fab_refresh_theme);
    fabRefresh.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int currentNightMode = getCurrentNightMode();
        alternateNightMode(currentNightMode);
      }
    });
    showSnackbar(fabRefresh, buildNightModeMessage());
  }

  private void initNightMode() {
    int nightMode = retrieveNightModeFromPreferences();
    AppCompatDelegate.setDefaultNightMode(nightMode);
  }

  private int getCurrentNightMode() {
    return getResources().getConfiguration().uiMode
      & Configuration.UI_MODE_NIGHT_MASK;
  }

  private void alternateNightMode(int currentNightMode) {
    int newNightMode;
    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
      newNightMode = AppCompatDelegate.MODE_NIGHT_NO;
    } else {
      newNightMode = AppCompatDelegate.MODE_NIGHT_YES;
    }
    saveNightModeToPreferences(newNightMode);
    recreate();
  }

  private int retrieveNightModeFromPreferences() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    return preferences.getInt(getString(R.string.current_night_mode), Configuration.UI_MODE_NIGHT_NO);
  }

  private void saveNightModeToPreferences(int nightMode) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(getString(R.string.current_night_mode), nightMode);
    editor.apply();
  }

  private String buildNightModeMessage() {
    int currentNightMode = getCurrentNightMode();
    switch (currentNightMode) {
      case UI_MODE_NIGHT_NO:
        return "Night Mode OFF";
      case UI_MODE_NIGHT_YES:
        return "Night Mode ON";
    }
    return "Night Mode UNKNOWN";
  }

  private void showSnackbar(View view, String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
  }
}
