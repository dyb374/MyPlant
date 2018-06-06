package com.ecnu.myplant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ecnu.myplant.R;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;

import org.litepal.crud.DataSupport;

import java.util.List;

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
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        //为image设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //通过修改imageview的src来加载不同植物状态显示的图片
        List<ProvincePlant> pps = DataSupport.findAll(ProvincePlant.class);
        boolean has = false;
        for(ProvincePlant pp : pps) {
            if(has == true)
                break;
            String plantName = pp.getPlant();
            List<Plant> plants = DataSupport.findAll(Plant.class);
            for(Plant p : plants){
                if(plantName.equals(p.getName()) && p.getPlantId() == 1) {
                    has = true;
                    break;
                }
            }
        }
        if(has)
            imageView.setImageResource(R.drawable.flower_pot);
        /*else
            imageView.setImageResource(R.drawable.flower_pot);*/
        return view;
    }
}
