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

import com.ecnu.myplant.LongTouchBtn;
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
    int waterNum = 0;
    int fertilizerNum = 0;
    int soilNum = 0;
    int pestNum = 0;
    int waterFlag = 0;
    int fertilizerFlag = 0;
    int soilFlag = 0;
    int pestFlag = 0;
    ImageView imageView = null;
    LinearLayout tools = null;
    boolean has = false;
    String plantName = null;
    LongTouchBtn fertilizerProgress;
    LongTouchBtn waterProgress;
    LongTouchBtn soilProgress;
    LongTouchBtn pestProgress;
    FrameLayout board;
    ImageView watchSoil;
    ImageView watchLeaf;
    ImageView watchOk;
    ImageView waterOk;
    ImageView waterCancel;
    ImageView soilOk;
    ImageView soilCancel;
    ImageView pestOk;
    ImageView peatCancel;
    ImageView fertilizerOk;
    ImageView fertilizerCancel;
    ImageView outdoorWatch;
    ImageView fertilizer;
    ImageView water;
    ImageView soil;
    ImageView pest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_six, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);//盆栽
        tools = (LinearLayout) view.findViewById(R.id.outdoor_tools);
        outdoorWatch = (ImageView) view.findViewById(R.id.outdoor_watch);//观察按钮
        fertilizer = (ImageView) view.findViewById(R.id.outdoor_fertilizer);//施肥按钮
        water = (ImageView) view.findViewById(R.id.outdoor_water);//浇水按钮
        soil = (ImageView) view.findViewById(R.id.outdoor_soil);//松土按钮
        pest = (ImageView) view.findViewById(R.id.outdoor_pest);//施肥按钮
        board = (FrameLayout) view.findViewById(R.id.outdoor_board);//面板
        watchSoil = (ImageView) view.findViewById(R.id.outdoor_watch_soil);//观察面板的土壤
        watchLeaf = (ImageView) view.findViewById(R.id.outdoor_watch_leaf);//观察面板的叶子
        watchOk = (ImageView) view.findViewById(R.id.outdoor_watch_ok);//确认观察
        waterOk = (ImageView) view.findViewById(R.id.outdoor_water_ok);//确认浇水
        waterCancel = (ImageView) view.findViewById(R.id.outdoor_water_cancel);//取消浇水
        soilOk = (ImageView) view.findViewById(R.id.outdoor_soil_ok);//确认松土
        soilCancel = (ImageView) view.findViewById(R.id.outdoor_soil_cancel);//取消松土
        pestOk = (ImageView) view.findViewById(R.id.outdoor_pest_ok);//确认除虫
        peatCancel = (ImageView) view.findViewById(R.id.outdoor_pest_cancel);//取消除虫
        fertilizerOk = (ImageView) view.findViewById(R.id.outdoor_fertilizer_ok);//确认施肥
        fertilizerCancel = (ImageView) view.findViewById(R.id.outdoor_fertilizer_cancel);//取消施肥
        waterProgress = (LongTouchBtn) view.findViewById(R.id.outdoor_water_progress);//浇水进度条
        fertilizerProgress = (LongTouchBtn) view.findViewById(R.id.outdoor_fertilizer_progress);//施肥进度条
        pestProgress = (LongTouchBtn) view.findViewById(R.id.outdoor_pest_progress);//除虫进度条
        soilProgress = (LongTouchBtn) view.findViewById(R.id.outdoor_soil_progress);//松土进度条

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
                fertilizerProgress.setVisibility(View.VISIBLE);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {//浇水按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                waterOk.setVisibility(View.VISIBLE);
                waterCancel.setVisibility(View.VISIBLE);
                waterProgress.setVisibility(View.VISIBLE);
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {//松土按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                soilOk.setVisibility(View.VISIBLE);
                soilCancel.setVisibility(View.VISIBLE);
                soilProgress.setVisibility(View.VISIBLE);
            }
        });
        pest.setOnClickListener(new View.OnClickListener() {//除虫按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                pestOk.setVisibility(View.VISIBLE);
                peatCancel.setVisibility(View.VISIBLE);
                pestProgress.setVisibility(View.VISIBLE);
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
                waterProgress.setVisibility(View.GONE);
                //data
                waterNum = 0;
                waterProgress.setProgress(0);
                waterFlag = 0;
            }
        });

        waterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                waterCancel.setVisibility(View.GONE);
                waterOk.setVisibility(View.GONE);
                waterProgress.setVisibility(View.GONE);
                waterNum = 0;
                waterProgress.setProgress(0);
                waterFlag = 0;
            }
        });

        soilOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                soilCancel.setVisibility(View.GONE);
                soilOk.setVisibility(View.GONE);
                soilProgress.setVisibility(View.GONE);
                //data
                soilNum = 0;
                soilProgress.setProgress(0);
                soilFlag = 0;
            }
        });

        soilCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                soilCancel.setVisibility(View.GONE);
                soilOk.setVisibility(View.GONE);
                soilProgress.setVisibility(View.GONE);
                soilNum = 0;
                soilProgress.setProgress(0);
                soilFlag = 0;
            }
        });

        fertilizerOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
                fertilizerProgress.setVisibility(View.GONE);
                //data
                fertilizerNum = 0;
                fertilizerProgress.setProgress(0);
                fertilizerFlag = 0;
            }
        });

        fertilizerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
                fertilizerProgress.setVisibility(View.GONE);
                fertilizerNum = 0;
                fertilizerProgress.setProgress(0);
                fertilizerFlag = 0;
            }
        });

        pestOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                peatCancel.setVisibility(View.GONE);
                pestOk.setVisibility(View.GONE);
                pestProgress.setVisibility(View.GONE);
                //data
                pestNum = 0;
                pestProgress.setProgress(0);
                pestFlag = 0;
            }
        });

        peatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                peatCancel.setVisibility(View.GONE);
                pestOk.setVisibility(View.GONE);
                pestProgress.setVisibility(View.GONE);
                pestNum = 0;
                pestProgress.setProgress(0);
                pestFlag = 0;
            }
        });

        waterProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Log.i("test", "自定义按钮处理单击");

            }
        });
        waterProgress.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                return false;
            }
        });

        waterProgress.setOnLongTouchListener(new LongTouchBtn.LongTouchListener() {//浇水进度条控件

            @Override
            public void onLongTouch() {

                if(waterFlag == 0) {
                    waterNum = waterNum + 5;
                    waterProgress.setProgress(waterNum);

                }
                else if(waterFlag == 1) {
                    waterNum = waterNum - 5;
                    waterProgress.setProgress(waterNum);
                }
                if(waterNum == 100) {
                    waterFlag = 1;
                }
                if(waterNum == 0) {
                    waterFlag = 0;
                }

            }
        },100);

        fertilizerProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Log.i("test", "自定义按钮处理单击");

            }
        });
        fertilizerProgress.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                return false;
            }
        });

        fertilizerProgress.setOnLongTouchListener(new LongTouchBtn.LongTouchListener() {//施肥进度条控件

            @Override
            public void onLongTouch() {

                if(fertilizerFlag == 0) {
                    fertilizerNum = fertilizerNum + 5;
                    fertilizerProgress.setProgress(fertilizerNum);

                }
                else if(fertilizerFlag == 1) {
                    fertilizerNum = fertilizerNum - 5;
                    fertilizerProgress.setProgress(fertilizerNum);
                }
                if(fertilizerNum == 100) {
                    fertilizerFlag = 1;
                }
                if(fertilizerNum == 0) {
                    fertilizerFlag = 0;
                }

            }
        },100);

        soilProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Log.i("test", "自定义按钮处理单击");

            }
        });
        soilProgress.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                return false;
            }
        });

        soilProgress.setOnLongTouchListener(new LongTouchBtn.LongTouchListener() {//浇水进度条控件

            @Override
            public void onLongTouch() {

                if(soilFlag == 0) {
                    soilNum = soilNum + 5;
                    soilProgress.setProgress(soilNum);

                }
                else if(soilFlag == 1) {
                    soilNum = soilNum - 5;
                    soilProgress.setProgress(soilNum);
                }
                if(soilNum == 100) {
                    soilFlag = 1;
                }
                if(soilNum == 0) {
                    soilFlag = 0;
                }

            }
        },100);

        pestProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Log.i("test", "自定义按钮处理单击");

            }
        });
        pestProgress.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                return false;
            }
        });

        pestProgress.setOnLongTouchListener(new LongTouchBtn.LongTouchListener() {//施肥进度条控件

            @Override
            public void onLongTouch() {

                if(pestFlag == 0) {
                    pestNum = pestNum + 5;
                    pestProgress.setProgress(pestNum);

                }
                else if(pestFlag == 1) {
                    pestNum = pestNum - 5;
                    pestProgress.setProgress(pestNum);
                }
                if(pestNum == 100) {
                    pestFlag = 1;
                }
                if(pestNum == 0) {
                    pestFlag = 0;
                }

            }
        },100);


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
                if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                    count++;
                    if (count == 1){
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
                    Intent intent1 = new Intent(getActivity(), OutdoorsSeedActivity.class);
                    startActivity(intent1);
                }
            });

        }
    }

}
