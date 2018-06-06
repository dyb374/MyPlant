package com.ecnu.myplant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;


import com.dreamlive.hotimglibrary.entity.HotArea;
import com.dreamlive.hotimglibrary.utils.FileUtils;
import com.dreamlive.hotimglibrary.view.HotClickView;
import com.ecnu.myplant.service.FindPlant;

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
    public void OnClick(View view, final HotArea hotArea) {
        String place = hotArea.getAreaId();
        AlertDialog.Builder dialog = new AlertDialog.Builder(MapActivity.this);
        dialog.setMessage("确认去" + hotArea.getAreaTitle() + "寻找植物？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(mHotView, "小人已经去" + hotArea.getAreaTitle() + "寻找植物", Snackbar.LENGTH_LONG)
                        .setAction("取消行程", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MapActivity.this, "你已取消旅程", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                Intent intent = new Intent(MapActivity.this, FindPlant.class);
                intent.putExtra("province", hotArea.getAreaTitle());
                startService(intent);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
