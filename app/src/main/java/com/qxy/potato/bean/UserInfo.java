package com.qxy.potato.bean;

/**
 * {
 *   "avatar": "https://p3.douyinpic.com/aweme/100x100/aweme-avatar/tos-cn-i-0813_9d30078528ac47b1b5f0b9110a12c149.jpeg?from=4010531038",
 *   "avatar_larger": "https://p3.douyinpic.com/aweme/1080x1080/aweme-avatar/tos-cn-i-0813_9d30078528ac47b1b5f0b9110a12c149.jpeg?from=4010531038",
 *   "captcha": "",
 *   "city": "",
 *   "client_key": "aw8i3d4fhpduf8eg",
 *   "country": "",
 *   "desc_url": "",
 *   "description": "",
 *   "district": "",
 *   "e_account_role": "",
 *   "error_code": 0,
 *   "gender": 0,
 *   "log_id": "202208181614040102121462024A0F5E76",
 *   "nickname": "dyjdyj",
 *   "open_id": "_00021vY0_Bf5MvEhbErNQmr8KOaNOrZs_kt",
 *   "province": "",
 *   "union_id": "7b9a3f90-d781-4231-8c1d-a0ed021009a7"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/18 22:25
 * @description：个人信息类
 * @modified By：
 * @version: 1.0
 */
public class UserInfo {
    private String avatar;
    private String avatar_larger;
    private String captcha;
    private String city;
    private String client_key;
    private String country;
    private String desc_url;
    private String description;
    private String district;
    private String e_account_role;
    private int error_code;
    private int gender;
    private String log_id;
    private String nickname;
    private String open_id;
    private String province;
    private String union_id;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_larger() {
        return avatar_larger;
    }

    public void setAvatar_larger(String avatar_larger) {
        this.avatar_larger = avatar_larger;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClient_key() {
        return client_key;
    }

    public void setClient_key(String client_key) {
        this.client_key = client_key;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesc_url() {
        return desc_url;
    }

    public void setDesc_url(String desc_url) {
        this.desc_url = desc_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getE_account_role() {
        return e_account_role;
    }

    public void setE_account_role(String e_account_role) {
        this.e_account_role = e_account_role;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUnion_id() {
        return union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }
}
