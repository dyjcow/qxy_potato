package com.qxy.potato.bean;

/**
 * {
 * "access_token": "act.3ebde655cd0885cb5a45e527d525dd6bjii83fK01cjyVBM8HjxE1nk5V0Hl",
 * "captcha": "",
 * "desc_url": "",
 * "description": "",
 * "error_code": 0,
 * "expires_in": 1296000,
 * "log_id": "202208121333280102081020783A0AA57F",
 * "open_id": "_00021vY0_Bf5MvEhbErNQmr8KOaNOrZs_kt",
 * "refresh_expires_in": 2592000,
 * "refresh_token": "rft.45aaa2fa07a1e6618e70b751e8e2bbbfdvZ44mScxwWZOFZkqL1dtOFRBUaJ",
 * "scope": "user_info,trial.whitelist"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/13 16:38
 * @description：首次获取AccessToken的类
 * @modified By：
 * @version: 1.0
 *
 */
public class AccessToken {
    private String access_token;
    private String captcha;
    private String desc_url;
    private String description;
    private int error_code;
    private int expires_in;
    private String log_id;
    private String open_id;
    private int refresh_expires_in;
    private String refresh_token;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public int getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(int refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
