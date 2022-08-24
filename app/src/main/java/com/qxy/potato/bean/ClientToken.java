package com.qxy.potato.bean;

/**
 * {
 * "access_token": "clt.daf250061c2d6a58d86d25ff6dd6e1d08mnlK9W8Xnyo67mXZLbMnsDWSDSv",
 * "captcha": "",
 * "desc_url": "",
 * "description": "",
 * "error_code": 0,
 * "expires_in": 7200,
 * "log_id": "202208131047400102121572210F3F74E2"
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/13 16:42
 * @description：获取ClientToken
 * @modified By：
 * @version: 1.0
 */
public class ClientToken {

    private String access_token;
    private String captcha;
    private String desc_url;
    private String description;
    private int error_code;
    private int expires_in;
    private String log_id;

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
}
