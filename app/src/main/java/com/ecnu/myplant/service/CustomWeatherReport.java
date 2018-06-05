package com.ecnu.myplant.service;

import com.ecnu.myplant.gson.Weather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by qyy on 2018/6/5.
 */
//http://v.juhe.cn/weather/index?cityname="+cityName+"&key=345ca02d5abdcb528e2f4a2fe60a6853
public class CustomWeatherReport {
    public static String response = "";
    public static void getWeatherJSON(final String city) {
       Thread thread = new Thread("a") {
           public void run() {
               try {
                   URL url = new URL("http://v.juhe.cn/weather/index?cityname="+city+"&key=345ca02d5abdcb528e2f4a2fe60a6853");
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setConnectTimeout(8000);
                   connection.setReadTimeout(8000);
                   connection.setDoInput(true);
                   connection.setDoOutput(true);
                   InputStream in = connection.getInputStream();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                   String line;
                   while ((line = reader.readLine()) != null) {
                       response += line;
                   }
               } catch (Exception e){
                   e.printStackTrace();
               }
           }
       } ;
        thread.start();
        try {
            Thread.sleep(500);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getSimpleWeatherJSON() {
        String originResponse = response;
        response = originResponse.substring(originResponse.indexOf("sk") + 4, originResponse.indexOf("today") - 3) + ",";
        String tmp = originResponse.substring(originResponse.indexOf("uv") - 1, originResponse.indexOf("comfort") - 2);
        response += (tmp + ",");
        String tmp2 = originResponse.substring(originResponse.indexOf("drying") - 1, originResponse.indexOf("future") - 3);
        response += (tmp2 + "}");
    }

    public static Weather JSONToWeather() {
        String temp = response.substring(response.indexOf("temp") + 7, response.indexOf("wind_direction") - 3);
        String wind_direction = response.substring(response.indexOf("wind_direction") + 17, response.indexOf("wind_strength") - 3);
        String wind_strength = response.substring(response.indexOf("wind_strength") + 16, response.indexOf("humidity") - 3);
        String humidity = response.substring(response.indexOf("humidity") + 11, response.indexOf("time") - 3);
        String time = response.substring(response.indexOf("time") + 7, response.indexOf("uv_index") - 3);
        String uv_index = response.substring(response.indexOf("uv_index") + 11, response.indexOf("drying_index") - 3);
        String drying_index = response.substring(response.indexOf("drying_index") + 15, response.indexOf("}") - 1);
        return new Weather(temp, wind_direction, wind_strength, humidity, time, uv_index, drying_index);
    }

}
