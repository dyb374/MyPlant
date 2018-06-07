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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ecnu.myplant.R;
import com.ecnu.myplant.SeedActivity;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;
import com.ecnu.myplant.service.ViewAnimation;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Andrew Dong on 2018/4/19.
 * 第二盆室内植物
 */

public class FragmentTwo extends Fragment {
    private static final String TAG = "FragmentTwo";
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        final LinearLayout tools = (LinearLayout) view.findViewById(R.id.indoor_tools);

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
        return view;
    }
}
