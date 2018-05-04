package net.wizardfactory.todayweather.widget.Provider;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import net.wizardfactory.todayair.R;
import net.wizardfactory.todayweather.widget.Data.Units;
import net.wizardfactory.todayweather.widget.Data.WeatherData;
import net.wizardfactory.todayweather.widget.Data.WidgetData;
import net.wizardfactory.todayweather.widget.JsonElement.WeatherElement;
import net.wizardfactory.todayweather.widget.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class W4x1AirStatus extends TwWidgetProvider {

    public W4x1AirStatus() {
        TAG = "W4x1 AirStatus";
        mLayoutId = R.layout.w4x1_air_status;
    }

    static public void setWidgetStyle(Context context, int appWidgetId, RemoteViews views) {
        TwWidgetProvider.setWidgetStyle(context, appWidgetId, views);
        TwWidgetProvider.setWidgetInfoStyle(context, appWidgetId, views);

        int fontColor = SettingsActivity.loadFontColorPref(context, appWidgetId);
        views.setTextColor(R.id.errMsg, fontColor);
        views.setTextColor(R.id.msg, fontColor);
        views.setTextColor(R.id.txt_pm25, fontColor);
        views.setTextColor(R.id.txt_pm10, fontColor);
        views.setTextColor(R.id.txt_o3, fontColor);

        if (Build.MANUFACTURER.equals("samsung")) {
            if (Build.VERSION.SDK_INT >= 16) {
                views.setTextViewTextSize(R.id.txt_pm25, TypedValue.COMPLEX_UNIT_DIP, 18);
                views.setTextViewTextSize(R.id.txt_pm10, TypedValue.COMPLEX_UNIT_DIP, 18);
                views.setTextViewTextSize(R.id.txt_o3, TypedValue.COMPLEX_UNIT_DIP, 18);
            }
        }

        TwWidgetProvider.setPendingIntentToRefresh(context, appWidgetId, views);
        TwWidgetProvider.setPendingIntentToSettings(context, appWidgetId, views);
        TwWidgetProvider.setPendingIntentToApp(context, appWidgetId, views);
    }

    static public void setWidgetData(Context context, RemoteViews views, WidgetData wData, Units localUnits) {
        if (wData == null) {
            Log.e(TAG, "weather data is NULL");
            return;
        }

        if (wData.getLoc() != null) {
            // setting town
            views.setTextViewText(R.id.location, wData.getLoc());
        }

        WeatherData currentData = wData.getCurrentWeather();
        if (currentData == null) {
            Log.e(TAG, "currentElement is NULL");
            return;
        }

        SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm");
        views.setTextViewText(R.id.pubdate, transFormat.format(Calendar.getInstance().getTime()));

        int aqiGrade = currentData.getAqiGrade();
        int pm10Grade = currentData.getPm10Grade();
        int pm25Grade = currentData.getPm25Grade();
	    int o3Grade = currentData.getO3Grade();
        int color = -1;

        if (aqiGrade != WeatherElement.DEFAULT_WEATHER_INT_VAL && aqiGrade != 0) {
            views.setTextViewText(R.id.msg, currentData.getSummaryAir());
        }

        if (pm25Grade != WeatherElement.DEFAULT_WEATHER_INT_VAL && pm25Grade != 0) {
            String msg = context.getString(R.string.pm25);
            msg += " " + String.valueOf(currentData.getPm25Value());
            views.setTextViewText(R.id.txt_pm25, msg);
            color = getColorAqiGrade(context, pm25Grade, localUnits.getAirUnit());
            if (color != -1) {
                views.setInt(R.id.img_pm25, "setColorFilter", color);
            }
        }

        if (pm10Grade != WeatherElement.DEFAULT_WEATHER_INT_VAL && pm10Grade != 0) {
            String msg = context.getString(R.string.pm10);
            msg += " " + String.valueOf(currentData.getPm10Value());
            views.setTextViewText(R.id.txt_pm10, msg);
            color = getColorAqiGrade(context, pm10Grade, localUnits.getAirUnit());
            if (color != -1) {
                views.setInt(R.id.img_pm10, "setColorFilter", color);
            }
        }

        if (o3Grade != WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL && o3Grade != 0) {
            String msg = context.getString(R.string.o3);
            msg += " " + String.valueOf(currentData.getO3Value());
            views.setTextViewText(R.id.txt_o3, msg);
            color = getColorAqiGrade(context, o3Grade, localUnits.getAirUnit());
            if (color != -1) {
                views.setInt(R.id.img_o3, "setColorFilter", color);
            }
        }
    }

    static private int getColorAirKoreaGrade(Context context, int grade) {
        switch (grade) {
            case 1:
                return ContextCompat.getColor(context, android.R.color.holo_blue_dark);
            case 2:
                return ContextCompat.getColor(context, android.R.color.holo_green_dark);
            case 3:
                return ContextCompat.getColor(context, android.R.color.holo_orange_dark);
            case 4:
                return ContextCompat.getColor(context, android.R.color.holo_red_dark);
            default:
                Log.e(TAG, "Fail to find airkorea color grade="+grade);
        }
        return -1;
    }

    static private int getColorAirNowGrade(Context context, int grade) {
        switch (grade) {
            case 1:
                return ContextCompat.getColor(context, android.R.color.holo_green_dark);
            case 2:
                return ContextCompat.getColor(context, android.R.color.holo_orange_light);
            case 3:
                return ContextCompat.getColor(context, android.R.color.holo_orange_dark);
            case 4:
                return ContextCompat.getColor(context, android.R.color.holo_red_dark);
            case 5:
                return ContextCompat.getColor(context, android.R.color.holo_purple);
            case 6:
                return Color.parseColor("#800000"); //maroon
            default:
                Log.e(TAG, "Fail to find airnow color grade="+grade);
        }
        return -1;
    }

    static private int getColorAqiGrade(Context context, int grade, String airUnit) {
        if (airUnit.equals("airkorea") || airUnit.equals("airkorea_who"))  {
            return getColorAirKoreaGrade(context, grade);
        }
        else {
            return getColorAirNowGrade(context, grade);
        }
    }
}

