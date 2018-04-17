package com.ecnu.myplant.gson;

/**
 * Created by Andrew Dong on 2018/4/13.
 * 这个类是关键
 * 引用各个gson实体类
 * 到时候需要通过这个类创建天气的实体
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;


}
