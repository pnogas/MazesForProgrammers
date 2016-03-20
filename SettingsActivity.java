package com.paulnogas.mazesforprogrammers;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.paulnogas.seekbarpreference.SeekBarPreference;

import static android.view.View.generateViewId;

public class SettingsActivity extends AppCompatActivity {

    public static final String MAZE_PREFS = "MazePreferences";

    static SeekBarPreference widthSeekBarPreference;
    static SeekBarPreference heightSeekBarPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = new FrameLayout(this);
        frame.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        if(Build.VERSION.SDK_INT > 16) frame.setId(generateViewId());
        else //noinspection ResourceType
            frame.setId(676442);

        setContentView(frame);

        getFragmentManager()
                .beginTransaction()
                .add(frame.getId(), new CreditsFragment())
                .commit();
    }

    public static class CreditsFragment extends PreferenceFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }


        //Runtime preference demo:
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MAZE_PREFS, Context.MODE_PRIVATE);

            widthSeekBarPreference = new SeekBarPreference(getActivity());
            this.getPreferenceScreen().addPreference(widthSeekBarPreference);

            widthSeekBarPreference.setTitle(getResources().getString(R.string.columns_pref_title));
            widthSeekBarPreference.setSummary("The number of rows in your maze");
            widthSeekBarPreference.setMaxValue(25);//17 for ascii
            widthSeekBarPreference.setMinValue(2);
            widthSeekBarPreference.setInterval(1);
            widthSeekBarPreference.setMeasurementUnit("rows");
            int currentWidth = sharedPreferences.getInt((String) widthSeekBarPreference.getTitle(), getResources().getInteger(R.integer.default_maze_columns));
            widthSeekBarPreference.setCurrentValue(currentWidth);

            heightSeekBarPreference = new SeekBarPreference(getActivity());
            this.getPreferenceScreen().addPreference(heightSeekBarPreference);

            heightSeekBarPreference.setTitle(getResources().getString(R.string.rows_pref_title));
            heightSeekBarPreference.setSummary("The number of columns in your maze");
            heightSeekBarPreference.setMaxValue(25); //10 for ascii
            heightSeekBarPreference.setMinValue(2);
            heightSeekBarPreference.setInterval(1);
            heightSeekBarPreference.setMeasurementUnit("columns");
            int currentHeight = sharedPreferences.getInt((String) heightSeekBarPreference.getTitle(), getResources().getInteger(R.integer.default_maze_rows));
            heightSeekBarPreference.setCurrentValue(currentHeight);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePrefs();
    }

    private void savePrefs() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MAZE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt((String) widthSeekBarPreference.getTitle(), widthSeekBarPreference.getCurrentValue());
        editor.putInt((String) heightSeekBarPreference.getTitle(), heightSeekBarPreference.getCurrentValue());
        editor.apply();
    }
}