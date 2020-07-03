package com.example.root.sub_katalog_dedi.setting;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.example.root.sub_katalog_dedi.BuildConfig;
import com.example.root.sub_katalog_dedi.R;
import com.example.root.sub_katalog_dedi.apihelper.BaseApiService;
import com.example.root.sub_katalog_dedi.apihelper.UtilsApi;
import com.example.root.sub_katalog_dedi.model.ResponseUpComing;
import com.example.root.sub_katalog_dedi.model.ResultsItem;
import com.example.root.sub_katalog_dedi.setting.notification.MovieDailyReceiver;
import com.example.root.sub_katalog_dedi.setting.notification.MovieReleaseReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();

    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        SwitchPreference switchReminder;
        SwitchPreference switchToday;

        MovieDailyReceiver movieDailyReceiver = new MovieDailyReceiver();
        MovieReleaseReceiver movieUpcomingReceiver = new MovieReleaseReceiver();

        List<ResultsItem> resultsItemList;
        List<ResultsItem> resultsSameItemList;
        BaseApiService baseApiService;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_setting);
            baseApiService = UtilsApi.getAPIService();
            resultsItemList = new ArrayList<>();
            resultsSameItemList = new ArrayList<>();

            switchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            switchReminder.setOnPreferenceChangeListener(this);
            switchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            switchToday.setOnPreferenceChangeListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean pilih = (boolean) newValue;

            if(key.equals(getString(R.string.key_today_reminder))){
                if(pilih){
                    movieDailyReceiver.setAlarm(getActivity());

                }else{
                    movieDailyReceiver.cancelAlarm(getActivity());

                }
            }
            else{
                if(pilih){
                    setReleaseAlarm();
                }else{
                    movieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }
            return true;
        }


        private void setReleaseAlarm() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);

            resultsItemList = new ArrayList<>();
            baseApiService.getUpcomingMovie(BuildConfig.API_KEY,"en-US").enqueue(new Callback<ResponseUpComing>() {
                @Override
                public void onResponse(Call<ResponseUpComing> call, Response<ResponseUpComing> response) {
                    Log.i(TAG, "onResponse: "+response.code());
                    if (response.code() == 200){
                        resultsItemList = response.body().getResults();
                        for(ResultsItem resultsItem : resultsItemList){
                            if(resultsItem.getReleaseDate().equals(now)){
                                resultsSameItemList.add(resultsItem);
                                Log.v(TAG, "cek data "+resultsSameItemList.size());
                            }
                        }
                        movieUpcomingReceiver.setAlarm(getActivity(), resultsSameItemList);
                    }
                }

                @Override
                public void onFailure(Call<ResponseUpComing> call, Throwable t) {
                    Log.i(TAG, "onFailure: "+ t.getMessage());
                    Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


}
