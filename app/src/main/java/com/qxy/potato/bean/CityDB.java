package com.qxy.potato.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/26 19:31
 * @description：DataBase of City
 * @modified By：
 * @version: 1.0
 */
public class CityDB extends LitePalSupport {

    private int id;

    private String cityName;

    private String cityAdm2;

    private String location;
    private String isLocationCity;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityAdm2() {
        return cityAdm2;
    }

    public void setCityAdm2(String cityAdm2) {
        this.cityAdm2 = cityAdm2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getIsLocationCity() {
        return isLocationCity;
    }

    public void setIsLocationCity(String isLocationCity) {
        this.isLocationCity = isLocationCity;
    }
}
