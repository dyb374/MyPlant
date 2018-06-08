package com.ecnu.myplant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ecnu.myplant.OutdoorsSeedActivity;
import com.ecnu.myplant.R;
import com.ecnu.myplant.SeedActivity;
import com.ecnu.myplant.db.MyPlant;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;
import com.ecnu.myplant.service.ViewAnimation;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrew Dong on 2018/4/19.
 * 第一株室外植物
 */

public class FragmentSix extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_six, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);//盆栽
        final LinearLayout tools = (LinearLayout) view.findViewById(R.id.outdoor_tools);
        ImageView outdoorWatch = (ImageView) view.findViewById(R.id.outdoor_watch);//观察按钮
        ImageView fertilizer = (ImageView) view.findViewById(R.id.outdoor_fertilizer);//施肥按钮
        ImageView water = (ImageView) view.findViewById(R.id.outdoor_water);//浇水按钮
        ImageView soil = (ImageView) view.findViewById(R.id.outdoor_soil);//松土按钮
        ImageView pest = (ImageView) view.findViewById(R.id.outdoor_pest);//施肥按钮
        final FrameLayout board = (FrameLayout) view.findViewById(R.id.outdoor_board);//面板
        final ImageView watchSoil = (ImageView) view.findViewById(R.id.outdoor_watch_soil);//观察面板的土壤
        final ImageView watchLeaf = (ImageView) view.findViewById(R.id.outdoor_watch_leaf);//观察面板的叶子
        final ImageView watchOk = (ImageView) view.findViewById(R.id.outdoor_watch_ok);//确认观察
        final ImageView waterOk = (ImageView) view.findViewById(R.id.outdoor_water_ok);//确认浇水
        final ImageView waterCancel = (ImageView) view.findViewById(R.id.outdoor_water_cancel);//取消浇水
        final ImageView soilOk = (ImageView) view.findViewById(R.id.outdoor_soil_ok);//确认松土
        final ImageView soilCancel = (ImageView) view.findViewById(R.id.outdoor_soil_cancel);//取消松土
        final ImageView pestOk = (ImageView) view.findViewById(R.id.outdoor_pest_ok);//确认除虫
        final ImageView peatCancel = (ImageView) view.findViewById(R.id.outdoor_pest_cancel);//取消除虫
        final ImageView fertilizerOk = (ImageView) view.findViewById(R.id.outdoor_fertilizer_ok);//确认施肥
        final ImageView fertilizerCancel = (ImageView) view.findViewById(R.id.outdoor_fertilizer_cancel);//取消施肥
        outdoorWatch.setOnClickListener(new View.OnClickListener() {//观察按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                watchLeaf.setVisibility(View.VISIBLE);
                watchSoil.setVisibility(View.VISIBLE);
                watchOk.setVisibility(View.VISIBLE);
            }
        });
        fertilizer.setOnClickListener(new View.OnClickListener() {//施肥按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                fertilizerOk.setVisibility(View.VISIBLE);
                fertilizerCancel.setVisibility(View.VISIBLE);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {//浇水按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                waterOk.setVisibility(View.VISIBLE);
                waterCancel.setVisibility(View.VISIBLE);
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {//松土按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                soilOk.setVisibility(View.VISIBLE);
                soilCancel.setVisibility(View.VISIBLE);
            }
        });
        pest.setOnClickListener(new View.OnClickListener() {//除虫按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                pestOk.setVisibility(View.VISIBLE);
                peatCancel.setVisibility(View.VISIBLE);
            }
        });

        watchOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                watchOk.setVisibility(View.GONE);
                watchLeaf.setVisibility(View.GONE);
                watchSoil.setVisibility(View.GONE);
            }
        });

        waterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                waterCancel.setVisibility(View.GONE);
                waterOk.setVisibility(View.GONE);
            }
        });

        waterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                waterCancel.setVisibility(View.GONE);
                waterOk.setVisibility(View.GONE);
            }
        });

        soilOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                soilCancel.setVisibility(View.GONE);
                soilOk.setVisibility(View.GONE);
            }
        });

        soilCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                soilCancel.setVisibility(View.GONE);
                soilOk.setVisibility(View.GONE);
            }
        });

        fertilizerOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
            }
        });

        fertilizerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
            }
        });

        pestOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                peatCancel.setVisibility(View.GONE);
                pestOk.setVisibility(View.GONE);
            }
        });

        peatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                peatCancel.setVisibility(View.GONE);
                pestOk.setVisibility(View.GONE);
            }
        });
        //通过修改imageview的src来加载不同植物状态显示的图片
        boolean has = false;
        int count = 0;
        String plantNmae = null;
        List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
        List<Plant> ps = DataSupport.findAll(Plant.class);
        for(MyPlant mp : mps) {
            for(Plant p : ps) {
                if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                    count++;
                    if (count == 1){
                        has = true;
                        plantNmae = mp.getPlant();
                        break;
                    }
                }
            }
        }
        if(has){
            imageView.setImageResource(R.drawable.flower_pot);
            //为image设置点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation animation = ViewAnimation.enterAnimation("left");
                    tools.startAnimation(animation);
                    tools.setVisibility(View.VISIBLE);
                }
            });

        }
        else{
            imageView.setImageResource(R.drawable.plus);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getActivity(), OutdoorsSeedActivity.class);
                    startActivity(intent1);
                }
            });

        }
        return view;
    }
}
