package com.ecnu.myplant;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;


import com.dreamlive.hotimglibrary.entity.HotArea;
import com.dreamlive.hotimglibrary.utils.FileUtils;
import com.dreamlive.hotimglibrary.view.HotClickView;

public class MapActivity extends AppCompatActivity implements HotClickView.OnClickListener{

    private HotClickView mHotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initParam();
        initDatas();
    }

    private void initParam() {
        mHotView = (HotClickView) findViewById(R.id.hotview);
//        mHotView.setCanMove(false);
//        mHotView.setCanScale(false);
    }

    protected void initDatas() {
        AssetManager assetManager = getResources().getAssets();
        InputStream imgInputStream = null;
        InputStream fileInputStream = null;
        try {
            imgInputStream = assetManager.open("china.png");
            fileInputStream = assetManager.open("china.xml");
            mHotView.setImageBitmap(fileInputStream, imgInputStream, HotClickView.FIT_XY);
            mHotView.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeInputStream(imgInputStream);
            FileUtils.closeInputStream(fileInputStream);
        }
    }


    @Override
    public void OnClick(View view, HotArea hotArea) {
        Toast.makeText(MapActivity.this, "你点击了" + hotArea.getDesc(), Toast.LENGTH_SHORT).show();
    }
}