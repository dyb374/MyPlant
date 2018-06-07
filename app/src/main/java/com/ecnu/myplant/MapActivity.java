package com.ecnu.myplant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


import com.dreamlive.hotimglibrary.entity.HotArea;
import com.dreamlive.hotimglibrary.utils.FileUtils;
import com.dreamlive.hotimglibrary.view.HotClickView;
import com.ecnu.myplant.layout.MapDialogLayout;
import com.ecnu.myplant.service.FindPlant;
import com.ecnu.myplant.service.ViewAnimation;

public class MapActivity extends AppCompatActivity implements HotClickView.OnClickListener{

    private HotClickView mHotView;
    private LinearLayout bottom;
    private ImageView cancel;
    private MapDialogLayout dialogLayout;
    ImageView dialogCancel;
    ImageView ok;
    TextView dialogText;
    TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initParam();
        initDatas();
    }

    private void initParam() {
        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/ddyy.ttf");
        mHotView = (HotClickView) findViewById(R.id.hotview);
        bottom = (LinearLayout) findViewById(R.id.map_bottom);
        cancel = (ImageView) findViewById(R.id.bottom_cancel_button);
        dialogLayout = (MapDialogLayout) findViewById(R.id.map_dialog);
        dialogCancel = (ImageView) findViewById(R.id.cancel_button);
        ok = (ImageView) findViewById(R.id.ok_button);
        dialogText = (TextView) findViewById(R.id.dialog_text);
        dialogText.setTypeface(typeface);
        bottomText = (TextView) findViewById(R.id.bottom_text);
        bottomText.setTypeface(typeface);
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
        dialogLayout.setVisibility(View.VISIBLE);
        dialogText.setText("确认去" + hotArea.getAreaTitle() + "寻找植物?");
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLayout.setVisibility(View.GONE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLayout.setVisibility(View.GONE);
                TranslateAnimation animation = ViewAnimation.enterAnimation("up");
                bottom.startAnimation(animation);
                bottom.setVisibility(View.VISIBLE);
                bottomText.setText("取消去" + hotArea.getAreaTitle() + "的行程");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TranslateAnimation animation = ViewAnimation.hideAnimation("down");
                        bottom.startAnimation(animation);
                        bottom.setVisibility(View.GONE);
                        //停止服务
                        Intent intent = new Intent(MapActivity.this, FindPlant.class);
                        stopService(intent);
                }
                });
                Intent intent = new Intent(MapActivity.this, FindPlant.class);
                intent.putExtra("province", hotArea.getAreaTitle());
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onDestroy();
    }


}
