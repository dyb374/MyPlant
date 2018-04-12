package com.ecnu.stu.myplant.gson;

/**
 * Created by Andrew Dong on 2018/4/13.
 */

public class AQI {
    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;
    }
}
