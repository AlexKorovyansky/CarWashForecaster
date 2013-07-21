package com.alexkorovyansky.carwashf.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Alex Korovyansky (korovyansk@gmail.com)
 */
public class ForecastStorage {

    private SharedPreferences mPreferences;
    public float forecastValue;
    public boolean inProgress;
    public long timestamp;

    public static ForecastStorage load(Context context){
        return new ForecastStorage(context.getSharedPreferences("forecast_storage", Context.MODE_PRIVATE));
    }

    public void save(){
        mPreferences.edit()
                .putFloat("forecastValue", forecastValue)
                .putBoolean("inProgress", inProgress)
                .putLong("timestamp", timestamp)
                .commit();
    }

    private ForecastStorage(SharedPreferences sharedPreferences){
        mPreferences = sharedPreferences;
        forecastValue = mPreferences.getFloat("forecastValue", -1.0f);
        inProgress = mPreferences.getBoolean("inProgress", false);
        timestamp = mPreferences.getLong("timestamp", 0);
    }

}
