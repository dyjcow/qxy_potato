package com.qxy.potato.bean;

/**
 *[
 *   {
 *     "date": "2022-05-25",
 *     "type": "1",
 *     "name": "运动指数",
 *     "level": "2",
 *     "category": "较适宜",
 *     "text": "天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。"
 *   },
 *   {
 *     "date": "2022-05-25",
 *     "type": "2",
 *     "name": "洗车指数",
 *     "level": "2",
 *     "category": "较适宜",
 *     "text": "较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"
 *   }
 * ]
 */

/**
 *
 * @author ：Dyj
 * @date ：Created in 2022/5/26 17:55
 * @description：Feel of weather
 * @modified By：
 * @version: 1.0
 */

public class DailyFeel {

    private String date;
    private String type;
    private String name;
    private String level;
    private String category;
    private String text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
