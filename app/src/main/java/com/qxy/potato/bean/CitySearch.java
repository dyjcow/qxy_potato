package com.qxy.potato.bean;

/**
 * {
 *   "name": "北京",
 *   "id": "101010100",
 *   "lat": "39.90498",
 *   "lon": "116.40528",
 *   "adm2": "北京",
 *   "adm1": "北京市",
 *   "country": "中国",
 *   "tz": "Asia/Shanghai",
 *   "utcOffset": "+08:00",
 *   "isDst": "0",
 *   "type": "city",
 *   "rank": "10",
 *   "fxLink": "http://hfx.link/2ax1"
 * }
 * {
 *   "name": "海淀",
 *   "id": "101010200",
 *   "lat": "39.95607",
 *   "lon": "116.31031",
 *   "adm2": "北京",
 *   "adm1": "北京市",
 *   "country": "中国",
 *   "tz": "Asia/Shanghai",
 *   "utcOffset": "+08:00",
 *   "isDst": "0",
 *   "type": "city",
 *   "rank": "15",
 *   "fxLink": "http://hfx.link/2ay1"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/5/26 18:04
 * @description：Search City
 * @modified By：
 * @version: 1.0
 */
public class CitySearch {

    private String name;
    private String id;
    private String lat;
    private String lon;
    private String adm2;
    private String adm1;
    private String country;
    private String tz;
    private String utcOffset;
    private String isDst;
    private String type;
    private String rank;
    private String fxLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAdm2() {
        return adm2;
    }

    public void setAdm2(String adm2) {
        this.adm2 = adm2;
    }

    public String getAdm1() {
        return adm1;
    }

    public void setAdm1(String adm1) {
        this.adm1 = adm1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getIsDst() {
        return isDst;
    }

    public void setIsDst(String isDst) {
        this.isDst = isDst;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }
}
