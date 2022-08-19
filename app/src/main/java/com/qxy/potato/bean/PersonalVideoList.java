package com.qxy.potato.bean;

import java.util.List;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.bean
 * @date :2022/8/19 15:06
 */
public class PersonalVideoList {

    private DataDTO data;
    private ExtraDTO extra;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public static class DataDTO {
        private String description;
        private long cursor;
        private boolean has_more;
        private List<ListDTO> list;
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

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public static class ListDTO {
            private int media_type;
            private String share_url;
            private String video_id;
            private int create_time;
            private String item_id;
            private boolean is_top;
            private StatisticsDTO statistics;
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

            public StatisticsDTO getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsDTO statistics) {
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

            public static class StatisticsDTO {
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

    public static class ExtraDTO {
        private int error_code;
        private String description;
        private int sub_error_code;
        private String sub_description;
        private int now;
        private String logid;

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

        public int getSub_error_code() {
            return sub_error_code;
        }

        public void setSub_error_code(int sub_error_code) {
            this.sub_error_code = sub_error_code;
        }

        public String getSub_description() {
            return sub_description;
        }

        public void setSub_description(String sub_description) {
            this.sub_description = sub_description;
        }

        public int getNow() {
            return now;
        }

        public void setNow(int now) {
            this.now = now;
        }

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }
    }
}
