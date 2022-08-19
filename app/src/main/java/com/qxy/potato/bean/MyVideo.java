package com.qxy.potato.bean;

import java.util.List;

/**
 * {
 *   "description": "",
 *   "cursor": 1660576280000,
 *   "has_more": false,
 *   "list": [
 *     {
 *       "media_type": 2,
 *       "share_url": "https://www.iesdouyin.com/share/video/7132138074468781353/?region=CN&mid=6952717870937671681&u_code=9cd4gb1kc4&did=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title&schema_type=37",
 *       "video_id": "7132138074468781353",
 *       "create_time": 1660580311,
 *       "item_id": "@9Vxc36CWCM84L3O0ZoA4R8791GbrPPuKO5J1rQyuKFkSbfT/60zdRmYqig357zEBIAG/Pi1fAdC+lbvNEeUwGQ==",
 *       "is_top": false,
 *       "statistics": {
 *         "comment_count": 0,
 *         "digg_count": 1,
 *         "download_count": 0,
 *         "forward_count": 0,
 *         "play_count": 82,
 *         "share_count": 0
 *       },
 *       "title": "coding#一起学习 ",
 *       "video_status": 1,
 *       "cover": "https://p3-sign.douyinpic.com/tos-cn-i-0813/7cb09e9fbedf4aba8143b9e982678501~noop.jpeg?x-expires=1662037200&x-signature=p7Uy33zsbioX%2BLhuFCVMEqzOvhQ%3D&from=4257465056&se=false&biz_tag=images_video_cover&l=20220818214139010212044036381D5760",
 *       "is_reviewed": true
 *     },
 *     {
 *       "cover": "",
 *       "create_time": 0,
 *       "is_top": false,
 *       "share_url": "",
 *       "title": "",
 *       "video_id": "7132120785258302755",
 *       "is_reviewed": true,
 *       "item_id": "@9Vxc36CWCM84L3O0ZoA4R8791GbrPPqCPJ10qw+uLFERafT560zdRmYqig357zEBBNijGzIE7LI/TpdFeI2EDQ==",
 *       "statistics": {
 *         "comment_count": 0,
 *         "digg_count": 0,
 *         "download_count": 0,
 *         "forward_count": 0,
 *         "play_count": 0,
 *         "share_count": 0
 *       },
 *       "video_status": 2
 *     }
 *   ],
 *   "error_code": 0
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/18 23:08
 * @description：我的视频列表
 * @modified By：
 * @version: 1.0
 */
public class MyVideo {
    private String description;
    private long cursor;
    private boolean has_more;
    private List<Videos> list;
    private int error_code;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCursor() {
        return cursor;
    }

    public void setCursor(long cursor) {
        this.cursor = cursor;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<Videos> getList() {
        return list;
    }

    public void setList(List<Videos> list) {
        this.list = list;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class Videos {
        private int media_type;
        private String share_url;
        private String video_id;
        private int create_time;
        private String item_id;
        private boolean is_top;
        private Videos.Statistics statistics;
        private String title;
        private int video_status;
        private String cover;
        private boolean is_reviewed;

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public Statistics getStatistics() {
            return statistics;
        }

        public void setStatistics(Statistics statistics) {
            this.statistics = statistics;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVideo_status() {
            return video_status;
        }

        public void setVideo_status(int video_status) {
            this.video_status = video_status;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isIs_reviewed() {
            return is_reviewed;
        }

        public void setIs_reviewed(boolean is_reviewed) {
            this.is_reviewed = is_reviewed;
        }

        public static class Statistics {
            private int comment_count;
            private int digg_count;
            private int download_count;
            private int forward_count;
            private int play_count;
            private int share_count;

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public int getDownload_count() {
                return download_count;
            }

            public void setDownload_count(int download_count) {
                this.download_count = download_count;
            }

            public int getForward_count() {
                return forward_count;
            }

            public void setForward_count(int forward_count) {
                this.forward_count = forward_count;
            }

            public int getPlay_count() {
                return play_count;
            }

            public void setPlay_count(int play_count) {
                this.play_count = play_count;
            }

            public int getShare_count() {
                return share_count;
            }

            public void setShare_count(int share_count) {
                this.share_count = share_count;
            }
        }
    }
}
