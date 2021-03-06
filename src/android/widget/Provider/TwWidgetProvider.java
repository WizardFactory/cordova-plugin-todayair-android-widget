package net.wizardfactory.todayweather.widget.Provider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import net.wizardfactory.todayair.MainActivity;
import net.wizardfactory.todayair.R;
import net.wizardfactory.todayweather.widget.Data.WidgetData;
import net.wizardfactory.todayweather.widget.SettingsActivity;
import net.wizardfactory.todayweather.widget.WidgetMenuActivity;
import net.wizardfactory.todayweather.widget.WidgetUpdateService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by aleckim on 2016. 9. 11..
 */
public class TwWidgetProvider extends AppWidgetProvider {
    protected static String TAG;
    protected int mLayoutId;

    public TwWidgetProvider() {
        TAG = "TwWidgetProvider";
        mLayoutId = -1;
    }

    static public void setWidgetStyle(Context context, int appWidgetId, RemoteViews views) {
        int fransparency = SettingsActivity.loadTransparencyPref(context, appWidgetId);
        int bgColor = SettingsActivity.loadBgColorPref(context, appWidgetId);
        int color = Color.argb(255*(100-fransparency)/100, Color.red(bgColor), Color.green(bgColor), Color.blue(bgColor));

        views.setInt(R.id.bg_layout, "setBackgroundColor", color);
    }

    static public void setWidgetInfoStyle(Context context, int appWidgetId, RemoteViews views) {
        if (Build.MANUFACTURER.equals("samsung")) {
            if (Build.VERSION.SDK_INT >= 16) {
                views.setTextViewTextSize(R.id.location, TypedValue.COMPLEX_UNIT_DIP, 16);
                views.setTextViewTextSize(R.id.pubdate, TypedValue.COMPLEX_UNIT_DIP, 16);
            }
        }

        int fontColor = SettingsActivity.loadFontColorPref(context, appWidgetId);
        views.setTextColor(R.id.location, fontColor);
        views.setTextColor(R.id.pubdate, fontColor);

        views.setInt(R.id.ic_settings, "setColorFilter", fontColor);
        views.setInt(R.id.ic_refresh, "setColorFilter", fontColor);
    }

    static public void setWidgetInfoData(Context context, RemoteViews views, WidgetData wData) {
        if (wData == null) {
            Log.e(TAG, "weather data is NULL");
            return;
        }

        if (wData.getLoc() != null) {
            // setting town
            views.setTextViewText(R.id.location, wData.getLoc());
        }

        SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(wData.dataTime);
        views.setTextViewText(R.id.pubdate, context.getString(R.string.update)+" "+
                transFormat.format(calendar.getTime()));

        return;
    }

    /**
     * call by WidgetUpdateService
     * @param context
     * @param appWidgetId
     * @param views
     */
    static public void setPendingIntentToMenu(Context context, int appWidgetId, RemoteViews views) {
        Intent intent = new Intent(context, WidgetMenuActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.bg_layout, pendingIntent);
    }

    static public void setPendingIntentToSettings(Context context, int appWidgetId, RemoteViews views) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ic_settings, pendingIntent);
        views.setOnClickPendingIntent(R.id.location, pendingIntent);
    }

    static public void setPendingIntentToRefresh(Context context, int appWidgetId, RemoteViews views) {
        Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.putExtra("ManualUpdate", true);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(context, appWidgetId,
                    serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else {
            pendingIntent = PendingIntent.getService(context, appWidgetId, serviceIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        views.setOnClickPendingIntent(R.id.ic_refresh, pendingIntent);
        views.setOnClickPendingIntent(R.id.pubdate, pendingIntent);
    }

    static public void setPendingIntentToApp(Context context, int appWidgetId, RemoteViews views) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.weather_layout, pendingIntent);
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "update app widget widgetId="+appWidgetId);

        // Get the layout for the App Widget and attach an on-click listener
        RemoteViews views = new RemoteViews(context.getPackageName(), mLayoutId);


        setWidgetStyle(context, appWidgetId, views);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // update widget weather data using service
        Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName provider = new ComponentName(context, getClass());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(provider);
        for (int appWidgetId : appWidgetIds) {
            SettingsActivity.cancelAlarmManager(context, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            SettingsActivity.cancelAlarmManager(context, appWidgetId);
            SettingsActivity.deleteWidgetPref(context, appWidgetId);
        }
    }

    /**
     * 추가될때, update 한 번 해서 사이즈 맞추어야 함
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param newOptions
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i(TAG, action);
    }

}
