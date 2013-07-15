package com.alexkorovyansky.carwashforecaster;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.alexkorovyansky.carwashforecaster.data.ForecastStorage;
import com.alexkorovyansky.carwashforecaster.services.ForecastIntentService;

import java.util.Date;

/**
 * @author Alex Korovyansky (korovyansk@gmail.com)
 */
public class ForecasterAppWidgetProvider extends AppWidgetProvider{

    public static final String TAG = ForecasterAppWidgetProvider.class.getSimpleName();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG, "onUpdate started");
        updateAllWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int [] appWidgetIds){
        final RemoteViews views = buildAppWidget(context);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    public static RemoteViews buildAppWidget(Context context){

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.forecaster_app_widget);

        final Intent intent = new Intent(context, ForecastIntentService.class);
        final PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.forecaster_app_widget_container, pendingIntent);

        final ForecastStorage storage = ForecastStorage.load(context);


        if (storage.inProgress) {
            views.setViewVisibility(R.id.forecaster_app_widget_percents, View.INVISIBLE);
            views.setViewVisibility(R.id.forecaster_app_widget_progress, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.forecaster_app_widget_percents, View.VISIBLE);
            views.setViewVisibility(R.id.forecaster_app_widget_progress, View.GONE);

            final long now = new Date().getTime();
            final long twelveHours = 12 * 60 * 60 * 1000;
            if (now - storage.timestamp < twelveHours) {
                final int forecastValue = (int)storage.forecastValue;

                if ( forecastValue >= 0 ){
                    views.setTextViewText(R.id.forecaster_app_widget_percents, (int)storage.forecastValue + "%");
                    if ( storage.forecastValue < 5 ){
                        views.setTextColor(R.id.forecaster_app_widget_percents, context.getResources().getColor(R.color.red));
                    } else if ( storage.forecastValue < 60) {
                        views.setTextColor(R.id.forecaster_app_widget_percents, context.getResources().getColor(R.color.yellow));
                    } else{
                        views.setTextColor(R.id.forecaster_app_widget_percents, context.getResources().getColor(R.color.green));
                    }
                } else {
                    views.setTextColor(R.id.forecaster_app_widget_percents, context.getResources().getColor(R.color.green));
                    views.setTextViewText(R.id.forecaster_app_widget_percents, "?");
                }
            } else{
                views.setTextColor(R.id.forecaster_app_widget_percents, context.getResources().getColor(R.color.green));
                views.setTextViewText(R.id.forecaster_app_widget_percents, "?");
            }
        }
        return views;
    }

}
