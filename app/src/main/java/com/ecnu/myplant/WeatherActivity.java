package com.ecnu.myplant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;


import com.ecnu.myplant.gson.Weather;
import com.ecnu.myplant.service.AutoUpdateService;
import com.ecnu.myplant.service.CustomWeatherReport;
import com.ecnu.myplant.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;

    private Typeface typeface;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherinfo;
    //private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView sugges;

    private TextView forecasttitle;

    private TextView airquility;

    private TextView aqititle;

    private TextView pm25title;

    private TextView forecasttext;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private String mWeatherId;

    private Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // 初始化各控件
        typeface = Typeface.createFromAsset(getAssets(),"fonts/ddyy.ttf");
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        weatherinfo = (TextView) findViewById(R.id.weather_info);
        sugges = (TextView) findViewById(R.id.sugges);
        forecasttitle = (TextView) findViewById(R.id.forecast_tile);
        airquility = (TextView) findViewById(R.id.quility);
        aqititle = (TextView) findViewById(R.id.aqi_title);
        pm25title = (TextView) findViewById(R.id.pm25_title);
        forecasttext = (TextView) findViewById(R.id.forecast_text);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        //weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        requestWeather();
        String info = "     温度：" + weather.temp + "℃" + "\n"
                + "     风向：" + weather.wind_direction + "\n"
                + "     风力：" + weather.wind_strength + "\n"
                + "     湿度：" + weather.humidity + "\n"
                + "     时间：" + weather.time + "\n"
                + "     紫外线：" + weather.uv_index;                                 //API的干燥度都为空


        //weatherInfoText.setText(info);
        weatherinfo.setTypeface(typeface);
        weatherinfo.setText(info);
        sugges.setTypeface(typeface);
        sugges.setText("天气状况");
        forecasttitle.setTypeface(typeface);
        forecasttitle.setText("今日");
        airquility.setTypeface(typeface);
        airquility.setText("空气质量");
        aqititle.setTypeface(typeface);
        aqititle.setText("");
        pm25title.setTypeface(typeface);
        pm25title.setText("");
        aqiText.setTypeface(typeface);
        aqiText.setText("37   优");
        pm25Text.setTypeface(typeface);
        pm25Text.setText("25μg/m³   优");
        forecasttext.setTypeface(typeface);
        forecasttext.setText(  "     上海   晴  " + weather.temp + "℃");

        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            mWeatherId = "CN101021500";//以普陀区为例
            requestWeather(mWeatherId);
        }*/
        requestWeather();


    }

    /**
     * 根据天气id请求城市天气信息。
     */
    /*public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            //editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }*/


    /**
     * 处理并展示Weather实体类中的数据。
     */
    /*private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        comfortText.setText(comfort);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }*/

    public void requestWeather() {
        //在模拟器上无法获取city的值，用真机时用注释替换就行，这里用上海代替
        /*String city = MainActivity.city.replace("市","");              //地图API和天气API的城市名字差了一个"市"字
        CustomWeatherReport.getWeatherJSON(city);
        */
        String city = "上海";
        CustomWeatherReport.getWeatherJSON(city);
        CustomWeatherReport.getSimpleWeatherJSON();
        weather = CustomWeatherReport.JSONToWeather();
    }

}
