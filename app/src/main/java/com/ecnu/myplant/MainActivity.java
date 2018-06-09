package com.ecnu.myplant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDAbstractLocationListener;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.gson.Weather;
import com.ecnu.myplant.service.CustomWeatherReport;
import com.ecnu.myplant.service.InitializeDatabase;
import com.ecnu.myplant.service.WeatherUpdate;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于初始化的类，初始化完成后再intent到其他的类中
 */

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient = null;
    private TextView positionText;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    public LocationClientOption option = new LocationClientOption();
    public static String city;
    public static Weather weather;                                                 //每隔一天更新此实例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        用户第一次使用时初始化数据库
         */

        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener( myListener );
        setContentView(R.layout.activity_main);
        positionText = (TextView) findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

        //数据库初始化
        LitePal.getDatabase();
        List<Plant> plants = DataSupport.findAll(Plant.class);
        if(plants.size() == 0) {
            Intent intent = new Intent(MainActivity.this, InitializeDatabase.class);
            startService(intent);
        }

        //自动获取天气服务
        Intent intent = new Intent(this, WeatherUpdate.class);
        startService(intent);

        //主界面
        ImageView enter_button = (ImageView) findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, IndoorSceneActivity.class);
                startActivity(intent1);
            }
        });
    }


    /**
     * 开启定位服务
     */
    private void requestLocation() {
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度: ").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度: ").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("定位方式: ");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
            city = bdLocation.getCity();
            positionText.setText(city);
        }
    }


}
