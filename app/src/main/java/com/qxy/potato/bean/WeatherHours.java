package com.qxy.potato.bean;

/**
 * {
 *   "fxTime": "2022-05-25T19:00+08:00",
 *   "temp": "27",
 *   "icon": "100",
 *   "text": "晴",
 *   "wind360": "268",
 *   "windDir": "西风",
 *   "windScale": "1-2",
 *   "windSpeed": "11",
 *   "humidity": "15",
 *   "pop": "7",
 *   "precip": "0.0",
 *   "pressure": "1001",
 *   "cloud": "11",
 *   "dew": "-1"
 * }
 * {
 *   "fxTime": "2022-05-25T20:00+08:00",
 *   "temp": "25",
 *   "icon": "150",
 *   "text": "晴",
 *   "wind360": "293",
 *   "windDir": "西北风",
 *   "windScale": "1-2",
 *   "windSpeed": "9",
 *   "humidity": "19",
 *   "pop": "7",
 *   "precip": "0.0",
 *   "pressure": "1001",
 *   "cloud": "0",
 *   "dew": "0"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/26 16:33
 * @description：Weather if every hours
 * @modified By：
 * @version: 1.0
 */

public class WeatherHours {

    private String fxTime;
    private String temp;
    private String icon;
    private String text;
    private String wind360;
    private String windDir;
    private String windScale;
    private String windSpeed;
    private String humidity;
    private String pop;
    private String precip;
    private String pressure;
    private String cloud;
    private String dew;

    public String getFxTime() {
        return fxTime;
    }

    public void setFxTime(String fxTime) {
        this.fxTime = fxTime;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        if (text.equals("Thundershower")) return "T~shower";
        else return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWind360() {
        return wind360;
    }

    public void setWind360(String wind360) {
        this.wind360 = wind360;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
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

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getDew() {
        return dew;
    }

    public void setDew(String dew) {
        this.dew = dew;
    }
}
