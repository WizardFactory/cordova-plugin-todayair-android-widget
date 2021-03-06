package net.wizardfactory.todayweather.widget.Data;

import net.wizardfactory.todayair.R;
import net.wizardfactory.todayweather.widget.JsonElement.WeatherElement;

import java.util.Calendar;
import java.util.Date;

/**
 * This class consist of weather data for one day . data from JsonElement.
 */
public class WeatherData {
    private int sky = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int pty = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int lgt = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private double temperature = WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL;
    private double maxTemperature = WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL;
    private double minTemperature = WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL;
    private String summary = null;
    private String summaryAir = null;
    private Date pubDate = null;
    private int aqiGrade = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int pm10Grade = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int pm25Grade = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int o3Grade = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int aqiValue = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int pm10Value = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private int pm25Value = WeatherElement.DEFAULT_WEATHER_INT_VAL;
    private double o3Value = WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL;
    private String aqiStr = null;
    private String pm10Str = null;
    private String pm25Str = null;
    private String o3Str = null;
    private Date aqiPubDate = null;
    private Date date = null; //날씨정보 날짜
    private String rn1Str = null;
    private double rn1 = WeatherElement.DEFAULT_WEATHER_DOUBLE_VAL;
    private int timeZoneOffsetMS = WeatherElement.DEFAULT_WEATHER_INT_VAL;

    public void setTimeZoneOffsetMS(int timeZoneOffsetMS) {
        this.timeZoneOffsetMS = timeZoneOffsetMS;
    }

    public int getTimeZoneOffsetMS() {
        return timeZoneOffsetMS;
    }

    public void setRn1(double rn1) {
        this.rn1 = rn1;
    }

    public double getRn1() {
        return rn1;
    }

    public void setRn1Str(String rn1Str) {
        this.rn1Str = rn1Str;
    }

    public String getRn1Str() {
        return rn1Str;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Date getAqiPubDate() {
        return aqiPubDate;
    }

    public void setAqiPubDate(Date aqiPubDate) {
        this.aqiPubDate = aqiPubDate;
    }

    public String getPm25Str() {
        return pm25Str;
    }

    public void setPm25Str(String pm25Str) {
        this.pm25Str = pm25Str;
    }

    public String getPm10Str() {
        return pm10Str;
    }

    public void setPm10Str(String pm10Str) {
        this.pm10Str = pm10Str;
    }

    public String getAqiStr() {
        return aqiStr;
    }

    public void setAqiStr(String aqiStr) {
        this.aqiStr = aqiStr;
    }

    public String getO3Str() {
        return o3Str;
    }

    public void setO3Str(String o3Str) {
        this.o3Str = o3Str;
    }

    public int getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(int pm25Value) {
        this.pm25Value = pm25Value;
    }

    public int getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(int pm10Value) {
        this.pm10Value = pm10Value;
    }

    public int getAqiValue() {
        return aqiValue;
    }

    public void setAqiValue(int aqiValue) {
        this.aqiValue = aqiValue;
    }

    public void setO3Value(double o3Value) {
        this.o3Value = o3Value;
    }

    public double getO3Value() {
        return o3Value;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public int getAqiGrade() {
        return aqiGrade;
    }

    public int getPm10Grade() {
        return pm10Grade;
    }

    public int getPm25Grade() {
        return pm25Grade;
    }

    public int getO3Grade() {
        return o3Grade;
    }

    public void setO3Grade(int o3Grade) {
        this.o3Grade = o3Grade;
    }

    public int getSky() {
        return sky;
    }

    public int getLgt() {
        return lgt;
    }

    public int getPty() {
        return pty;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public String getSummary() {
        return summary;
    }

    public String getSummaryAir() {
        return summaryAir;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setAqiGrade(int aqiGrade) {
        this.aqiGrade = aqiGrade;
    }

    public void setPm10Grade(int pm10Grade) {
        this.pm10Grade = pm10Grade;
    }

    public void setPm25Grade(int pm25Grade) {
        this.pm25Grade = pm25Grade;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSummaryAir(String summaryAir) {
        this.summaryAir = summaryAir;
    }

    public void setSky(double sky) {
        this.sky = (int)sky;
    }

    public void setPty(double pty) {
        this.pty = (int)pty;
    }

    public void setLgt(double lgt) {
        this.lgt = (int)lgt;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getSkyImageName() {
        return this.getSkyImageName(false);
    }

    /**
     *
     * @param {Number} sky 맑음(1) 구름조금(2) 구름많음(3) 흐림(4) , invalid : -1
     * @param {Number} pty 없음(0) 비(1) 비/눈(2) 눈(3), invalid : -1
     * @param {Number} lgt 없음(0) 있음(1), invalid : -1
     * @param {Boolean} isNight
     */
    public String getSkyImageName(boolean isDaily) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        boolean isNight = (hour >= 7 && hour < 18) ? false : true;
        if (isDaily == true) {
            isNight = false;
        }

        String strSkyImage = "";
        if (sky == 4) {
            strSkyImage += "cloud";
        }
        else {
            if (isNight)  {
                strSkyImage += "moon";
            }
            else {
                strSkyImage += "sun";
            }
            if (sky == 2) {
                strSkyImage += "_smallcloud";
            }
            else if (sky == 3) {
                strSkyImage += "_bigcloud";
            }
        }
        switch (pty) {
            case 1:
                strSkyImage += "_rain";
                break;
            case 2:
                strSkyImage += "_rain_snow";
                break;
            case 3:
                strSkyImage += "_snow";
                break;
        }
        if (lgt == 1) {
            strSkyImage += "_lightning";
        }

        return strSkyImage;
    }

    /**
     *
     * @param {Number} sky 맑음(1) 구름조금(2) 구름많음(3) 흐림(4) , invalid : -1
     * @param {Number} pty 없음(0) 비(1) 비/눈(2) 눈(3), invalid : -1
     * @param {Number} lgt 없음(0) 있음(1), invalid : -1
     * @param {Boolean} isNight
     */
    public int parseSkyState() {
        int retSkyIconRscId = -1;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        boolean isNight = (hour > 7 && hour < 18) ? false : true;

        switch (pty) {
            case 1:
                if (lgt == 1) {
                    return R.drawable.cloud_lightning;
                }
                return R.drawable.cloud_rain;
            case 2:
                return R.drawable.cloud_rain_snow;//Todo need RainWithSnow icon";
            case 3:
                return R.drawable.cloud_snow;
        }

        if (lgt == 1) {
            return R.drawable.cloud_lightning;
        }

        switch (sky) {
            case 1:
                if (isNight) {
                    retSkyIconRscId = R.drawable.moon;
                } else {
                    retSkyIconRscId = R.drawable.sun;
                }
                break;
            case 2:
                if (isNight) {
                    retSkyIconRscId = R.drawable.moon_bigcloud;
                } else {
                    retSkyIconRscId = R.drawable.sun_bigcloud;
                }
                break;
            case 3:
                retSkyIconRscId = R.drawable.cloud; //Todo need new icon
                break;
            case 4:
                retSkyIconRscId = R.drawable.cloud;
                break;
        }

        return retSkyIconRscId;
    }
}
