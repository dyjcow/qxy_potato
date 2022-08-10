package com.qxy.potato.bean;

/**
 * {
 *   "fxDate": "2022-03-17",
 *   "sunrise": "06:24",
 *   "sunset": "18:23",
 *   "moonrise": "17:15",
 *   "moonset": "06:36",
 *   "moonPhase": "盈凸月",
 *   "moonPhaseIcon": "803",
 *   "tempMax": "3",
 *   "tempMin": "-1",
 *   "iconDay": "404",
 *   "textDay": "雨夹雪",
 *   "iconNight": "104",
 *   "textNight": "阴",
 *   "wind360Day": "135",
 *   "windDirDay": "东南风",
 *   "windScaleDay": "1-2",
 *   "windSpeedDay": "3",
 *   "wind360Night": "0",
 *   "windDirNight": "北风",
 *   "windScaleNight": "1-2",
 *   "windSpeedNight": "3",
 *   "humidity": "73",
 *   "precip": "0.9",
 *   "pressure": "1012",
 *   "vis": "25",
 *   "cloud": "12",
 *   "uvIndex": "1"
 * }
 * {
 *   "fxDate": "2022-03-18",
 *   "sunrise": "06:22",
 *   "sunset": "18:24",
 *   "moonrise": "18:22",
 *   "moonset": "07:00",
 *   "moonPhase": "满月",
 *   "moonPhaseIcon": "804",
 *   "tempMax": "2",
 *   "tempMin": "-2",
 *   "iconDay": "400",
 *   "textDay": "小雪",
 *   "iconNight": "151",
 *   "textNight": "多云",
 *   "wind360Day": "180",
 *   "windDirDay": "南风",
 *   "windScaleDay": "1-2",
 *   "windSpeedDay": "3",
 *   "wind360Night": "45",
 *   "windDirNight": "东北风",
 *   "windScaleNight": "1-2",
 *   "windSpeedNight": "3",
 *   "humidity": "62",
 *   "precip": "1.5",
 *   "pressure": "1013",
 *   "vis": "24",
 *   "cloud": "35",
 *   "uvIndex": "1"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/26 10:24
 * @description：每日天气
 * @modified By：
 * @version: 1.0
 */

public class WeatherDay {

    private String fxDate;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moonPhase;
    private String moonPhaseIcon;
    private String tempMax;
    private String tempMin;
    private String iconDay;
    private String textDay;
    private String iconNight;
    private String textNight;
    private String wind360Day;
    private String windDirDay;
    private String windScaleDay;
    private String windSpeedDay;
    private String wind360Night;
    private String windDirNight;
    private String windScaleNight;
    private String windSpeedNight;
    private String humidity;
    private String precip;
    private String pressure;
    private String vis;
    private String cloud;
    private String uvIndex;

    public String getFxDate() {
        return fxDate;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getMoonPhaseIcon() {
        return moonPhaseIcon;
    }

    public void setMoonPhaseIcon(String moonPhaseIcon) {
        this.moonPhaseIcon = moonPhaseIcon;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getIconDay() {
        return iconDay;
    }

    public void setIconDay(String iconDay) {
        this.iconDay = iconDay;
    }

    public String getTextDay() {
        if (textDay.equals("Thundershower")) return "T~shower";
        else if (textDay.equals("Moderate Rain")) return "M~Rain";
        else return textDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public String getIconNight() {
        return iconNight;
    }

    public void setIconNight(String iconNight) {
        this.iconNight = iconNight;
    }

    public String getTextNight() {
        if (textNight.equals("Thundershower")) return "T~shower";
        else if (textNight.equals("Moderate Rain")) return "M~Rain";
        else return textNight;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }

    public String getWind360Day() {
        return wind360Day;
    }

    public void setWind360Day(String wind360Day) {
        this.wind360Day = wind360Day;
    }

    public String getWindDirDay() {
        return windDirDay;
    }

    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    public String getWindScaleDay() {
        return windScaleDay;
    }

    public void setWindScaleDay(String windScaleDay) {
        this.windScaleDay = windScaleDay;
    }

    public String getWindSpeedDay() {
        return windSpeedDay;
    }

    public void setWindSpeedDay(String windSpeedDay) {
        this.windSpeedDay = windSpeedDay;
    }

    public String getWind360Night() {
        return wind360Night;
    }

    public void setWind360Night(String wind360Night) {
        this.wind360Night = wind360Night;
    }

    public String getWindDirNight() {
        return windDirNight;
    }

    public void setWindDirNight(String windDirNight) {
        this.windDirNight = windDirNight;
    }

    public String getWindScaleNight() {
        return windScaleNight;
    }

    public void setWindScaleNight(String windScaleNight) {
        this.windScaleNight = windScaleNight;
    }

    public String getWindSpeedNight() {
        return windSpeedNight;
    }

    public void setWindSpeedNight(String windSpeedNight) {
        this.windSpeedNight = windSpeedNight;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPrecip() {
        return precip;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }
}
