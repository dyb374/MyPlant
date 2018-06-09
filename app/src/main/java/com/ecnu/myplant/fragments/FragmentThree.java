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

import com.ecnu.myplant.R;
import com.ecnu.myplant.SeedActivity;
import com.ecnu.myplant.db.MyPlant;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;
import com.ecnu.myplant.service.ViewAnimation;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Andrew Dong on 2018/4/19.
 * 第三盆室内植物
 */

public class FragmentThree extends Fragment {
    ImageView imageView = null;
    LinearLayout tools = null;
    boolean has = false;
    String plantName = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);//盆栽
        tools = (LinearLayout) view.findViewById(R.id.indoor_tools);
        ImageView indoorWatch = (ImageView) view.findViewById(R.id.indoor_watch);//观察按钮
        ImageView fertilizer = (ImageView) view.findViewById(R.id.indoor_fertilizer);//施肥按钮
        ImageView water = (ImageView) view.findViewById(R.id.indoor_water);//浇水按钮
        final FrameLayout board = (FrameLayout) view.findViewById(R.id.board);//面板
        final ImageView watchSoil = (ImageView) view.findViewById(R.id.watch_soil);//观察面板的土壤
        final ImageView watchLeaf = (ImageView) view.findViewById(R.id.watch_leaf);//观察面板的叶子
        indoorWatch.setOnClickListener(new View.OnClickListener() {//观察按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                watchLeaf.setVisibility(View.VISIBLE);
                watchSoil.setVisibility(View.VISIBLE);
            }
        });
        fertilizer.setOnClickListener(new View.OnClickListener() {//施肥按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {//浇水按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
            }
        });

        watchLeaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchLeaf.setVisibility(View.VISIBLE);
            }
        });

        getData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    void getData (){
        //通过修改imageview的src来加载不同植物状态显示的图片
        //计算myplant中的室内植物
        int count = 0;
        List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
        List<Plant> ps = DataSupport.findAll(Plant.class);
        for(MyPlant mp : mps) {
            for(Plant p : ps) {
                if(p.getName().equals(mp.getPlant()) && p.getId() >= 1 && p.getId() <= 5){
                    count++;
                    if (count == 3){
                        has = true;
                        plantName = mp.getPlant();
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
                    Intent intent1 = new Intent(getActivity(), SeedActivity.class);
                    startActivity(intent1);
                }
            });

        }
    }

}
