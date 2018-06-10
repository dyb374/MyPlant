package com.ecnu.myplant.gson;

import java.util.Date;

/**
 * Created by Andrew Dong on 2018/4/13.
 * 这个类是关键
 * 引用各个gson实体类
 * 到时候需要通过这个类创建天气的实体
 */

public class Weather {

    public String temp;//温度
    public String wind_direction;//风向
    public String wind_strength;//风力
    public String humidity;//湿度
    public String time;//时间
    public String uv_index;//紫外线
    public String drying_index;//干燥度

    public Weather(String temp, String wind_direction, String wind_strength, String humidity, String time, String uv_index, String drying_index) {
        this.temp = temp;
        this.wind_direction = wind_direction;
        this.wind_strength = wind_strength;
        this.humidity = humidity;
        this.time = time;
        this.uv_index = uv_index;
        this.drying_index = drying_index;
    }


}
