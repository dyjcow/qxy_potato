package com.qxy.potatos.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.qxy.potatos.common.EventCode;

import java.util.List;

/**
 * {
 * "cursor": 10,
 * "description": "",
 * "error_code": 0,
 * "has_more": true,
 * "list": [
 * {
 * "active_time": "2022-08-08",
 * "end_time": "2022-08-08",
 * "start_time": "2022-08-01",
 * "type": 1,
 * "version": 141
 * },
 * ...
 * ]
 * }
 */

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/13 16:56
 * @description：影视版本类
 * @modified By：
 * @version: 1.0
 */
public class VideoVersion {
    private int cursor;
    private String description;
    private int error_code;
    private boolean has_more;
    private List<Version> list;

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
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

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<Version> getList() {
        return list;
    }

    public void setList(List<Version> list) {
        this.list = list;
    }

    public static class Version implements IPickerViewData {
        private String active_time;
        private String end_time;
        private String start_time;
        private int type;
        private int version;

        private int tag = 0;

        public String getActive_time() {
            return active_time;
        }

        public void setActive_time(String active_time) {
            this.active_time = active_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        @Override
        public String getPickerViewText() {
            if (tag == EventCode.IS_FIRST_LIST) {
                return "本周榜单" + "  " + start_time + "-" + end_time;
            } else {
                return version + "  " + start_time + "-" + end_time;
            }

        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }
}
