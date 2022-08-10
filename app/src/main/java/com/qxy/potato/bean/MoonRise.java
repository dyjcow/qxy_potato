package com.qxy.potato.bean;

/**
 * {
 *   "fxTime": "2022-05-30T00:00+08:00",
 *   "value": "0.98",
 *   "name": "残月",
 *   "illumination": "0",
 *   "icon": "807"
 * }
 * {
 *   "fxTime": "2022-05-30T01:00+08:00",
 *   "value": "0.98",
 *   "name": "残月",
 *   "illumination": "0",
 *   "icon": "807"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/26 18:16
 * @description：MoonRise data
 * @modified By：
 * @version: 1.0
 */
public class MoonRise {

    private String fxTime;
    private String value;
    private String name;
    private String illumination;
    private String icon;

    public String getFxTime() {
        return fxTime;
    }

    public void setFxTime(String fxTime) {
        this.fxTime = fxTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIllumination() {
        return illumination;
    }

    public void setIllumination(String illumination) {
        this.illumination = illumination;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
