package com.qxy.potatos.bean;

import java.util.List;

/**
 * {
 * "has_more": false,
 * "list": [
 * {
 * "open_id": "_000PDo4ZAdrtBIaLfLtRsDK27tuo-aSRc4y",
 * "province": "云南",
 * "union_id": "",
 * "avatar": "https://p3.douyinpic.com/aweme/720x720/aweme-avatar/tos-cn-i-0813_afaa7f9600f4450e80b8a1041a802e38.jpeg?from=4010531038",
 * "city": "大理",
 * "country": "中国",
 * "gender": 1,
 * "nickname": "🌈nnnn"
 * },
 * {
 * "gender": 0,
 * "nickname": "啊啊啊啊啊啊啊啊",
 * "open_id": "_0001O3B03_sCaj6k4UFrk3MABBOtFg4hL_J",
 * "province": "",
 * "union_id": "",
 * "avatar": "https://p3.douyinpic.com/aweme/720x720/aweme-avatar/mosaic-legacy_3795_3033762272.jpeg?from=4010531038",
 * "city": "",
 * "country": ""
 * },
 * {
 * "open_id": "_000ZTutfqOyn7QYP_4yieOS6xcwgDLw_QRd",
 * "province": "广东",
 * "union_id": "",
 * "avatar": "https://p3.douyinpic.com/aweme/720x720/aweme-avatar/tos-cn-i-0813_f498bf416ce449369f256e94746d575a.jpeg?from=4010531038",
 * "city": "湛江",
 * "country": "中国",
 * "gender": 0,
 * "nickname": "70godplease"
 * },
 * {
 * "country": "",
 * "gender": 0,
 * "nickname": "SoulMateJJWW",
 * "open_id": "_000CG_Me-c6QzHRNWhqGGz-fXUzsYL7gwgn",
 * "province": "",
 * "union_id": "",
 * "avatar": "https://p3.douyinpic.com/aweme/720x720/aweme-avatar/tos-cn-i-0813_675418f4e4a34cebb20a984e9126ea1d.jpeg?from=4010531038",
 * "city": ""
 * }
 * ],
 * "error_code": 0,
 * "description": "",
 * "cursor": -1
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/18 22:51
 * @description：用户关注者列表
 * @modified By：
 * @version: 1.0
 */
public class Followings {
    private boolean has_more;
    private List<Following> list;
    private int error_code;
    private String description;
    private int cursor;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<Following> getList() {
        return list;
    }

    public void setList(List<Following> list) {
        this.list = list;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public static class Following {
        private String open_id;
        private String province;
        private String union_id;
        private String avatar;
        private String city;
        private String country;
        private int gender;
        private String nickname;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
