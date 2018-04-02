package com.wfl.kits.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.wfl.kits.R;

/**
 * Implementation of App Widget functionality.
 */
public class ClockWidgetClass extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        //getting shered prefrences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString("randomString", "");
        if(!name.equalsIgnoreCase(""))
        {
            name = name;  /* Edit the value here*/
        }

//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget_class);
//
//        //set chosen design visible
//        //set all visibility to 0
//        views.setViewVisibility(R.id.AnalogClock0, View.INVISIBLE);
//        views.setViewVisibility(R.id.AnalogClock1, View.INVISIBLE);
//        views.setViewVisibility(R.id.AnalogClock2, View.INVISIBLE);
//        views.setViewVisibility(R.id.AnalogClock3, View.INVISIBLE);
//
//        //turning on the correct clock
//        if (name.equals("1")) {
//            views.setViewVisibility(R.id.AnalogClock0, View.VISIBLE);
//        } else if (name.equals("2")) {
//            views.setViewVisibility(R.id.AnalogClock1, View.VISIBLE);
//        } else if (name.equals("3")) {
//            views.setViewVisibility(R.id.AnalogClock2, View.VISIBLE);
//        } else {
//            views.setViewVisibility(R.id.AnalogClock3, View.VISIBLE);
//        }
//
////        views.setTextViewText(R.id.appwidget_text, name);
//
//        //updateting the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Chain up to the super class so the onEnabled, etc callbacks get dispatched
        super.onReceive(context, intent);
        // Handle a different Intent
        Log.d("reciving", "onReceive()" + intent.getAction());

    }


}