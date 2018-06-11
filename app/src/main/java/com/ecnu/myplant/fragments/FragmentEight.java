package com.ecnu.myplant.fragments;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
 * 第三株室外植物
 */

public class FragmentEight extends Fragment {
    private int outdoorFragmentNumber = 3;
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
    SoundPool fertilizersp;
    int fertilizermusic;
    SoundPool watersp;
    int watermusic;
    SoundPool soilsp;
    int soilmusic;
    SoundPool spraysp;
    int spraymusic;
    FrameLayout removeBoard;
    ImageView removeOk;
    ImageView removeCancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_eight, container, false);
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
        fertilizersp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        fertilizermusic = fertilizersp.load(this.getActivity(),R.raw.pesticide,1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
        watersp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        watermusic = watersp.load(this.getActivity(),R.raw.water,1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
        soilsp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        soilmusic = soilsp.load(this.getActivity(),R.raw.soil,1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
        spraysp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        spraymusic = spraysp.load(this.getActivity(),R.raw.spray,1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
        removeBoard = (FrameLayout) view.findViewById(R.id.outdoor_remove_board);//移除植物面板
        removeOk = (ImageView) view.findViewById(R.id.outdoor_remove_ok);//确认移除植物
        removeCancel = (ImageView) view.findViewById(R.id.outdoor_remove_cancel); //取消移除植物

        outdoorWatch.setOnClickListener(new View.OnClickListener() {//观察按钮监听器
            @Override
            public void onClick(View view) {
                enabledAll(false);
                board.setVisibility(View.VISIBLE);
                watchLeaf.setVisibility(View.VISIBLE);
                watchSoil.setVisibility(View.VISIBLE);
                watchOk.setVisibility(View.VISIBLE);
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                            count++;
                            if (count == outdoorFragmentNumber){
                                waterNum = mp.getWaterContent();
                                fertilizerNum = mp.getLeafCondition();
                                soilNum = mp.getSoilFertility();
                                pestNum = mp.getPestsContent();
                                if(returnNum(waterNum) == 1)
                                    watchSoil.setImageResource(R.drawable.soil_watch1);
                                else if(returnNum(waterNum) == 2)
                                    watchSoil.setImageResource(R.drawable.soil_watch2);
                                else if(returnNum(waterNum) == 3)
                                    watchSoil.setImageResource(R.drawable.soil_watch3);

                                if(returnNum(fertilizerNum) == 1)
                                    watchLeaf.setImageResource(R.drawable.leaf1);
                                else if(returnNum(fertilizerNum) == 2)
                                    watchLeaf.setImageResource(R.drawable.leaf2);
                                else if(returnNum(fertilizerNum) == 3)
                                    watchLeaf.setImageResource(R.drawable.leaf3);

                                Log.d(TAG, "waterNum: " + waterNum);
                                Log.d(TAG, "fertilizerNum: " + fertilizerNum);
                                Log.d(TAG, "soilNum: " + soilNum);
                                Log.d(TAG, "pestNum: " + pestNum);
                                int level = mp.getLevel();
                                Log.d(TAG, "level: " + level);
                            }
                        }
                    }
                }
            }
        });
        fertilizer.setOnClickListener(new View.OnClickListener() {//施肥按钮监听器
            @Override
            public void onClick(View view) {
                enabledAll(false);
                board.setVisibility(View.VISIBLE);
                fertilizerOk.setVisibility(View.VISIBLE);
                fertilizerCancel.setVisibility(View.VISIBLE);
                fertilizerProgress.setVisibility(View.VISIBLE);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {//浇水按钮监听器
            @Override
            public void onClick(View view) {
                enabledAll(false);
                board.setVisibility(View.VISIBLE);
                waterOk.setVisibility(View.VISIBLE);
                waterCancel.setVisibility(View.VISIBLE);
                waterProgress.setVisibility(View.VISIBLE);
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {//松土按钮监听器
            @Override
            public void onClick(View view) {
                enabledAll(false);
                board.setVisibility(View.VISIBLE);
                soilOk.setVisibility(View.VISIBLE);
                soilCancel.setVisibility(View.VISIBLE);
                soilProgress.setVisibility(View.VISIBLE);
            }
        });
        pest.setOnClickListener(new View.OnClickListener() {//除虫按钮监听器
            @Override
            public void onClick(View view) {
                enabledAll(false);
                board.setVisibility(View.VISIBLE);
                pestOk.setVisibility(View.VISIBLE);
                peatCancel.setVisibility(View.VISIBLE);
                pestProgress.setVisibility(View.VISIBLE);
            }
        });

        watchOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
                board.setVisibility(View.GONE);
                watchOk.setVisibility(View.GONE);
                watchLeaf.setVisibility(View.GONE);
                watchSoil.setVisibility(View.GONE);
            }
        });

        waterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
                board.setVisibility(View.GONE);
                waterCancel.setVisibility(View.GONE);
                waterOk.setVisibility(View.GONE);
                waterProgress.setVisibility(View.GONE);
                //data
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                            count++;
                            if (count == outdoorFragmentNumber){
                                int newWaterNum = mp.getWaterContent() + waterNum / 3;
                                int id = mp.getId();
                                MyPlant  mp2 = new MyPlant();
                                mp2.setWaterContent(newWaterNum > 100 ? 100 : newWaterNum);
                                mp2.update(id);
                                Toast.makeText(getActivity(), "已成功浇水！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                waterNum = 0;
                waterProgress.setProgress(0);
                waterFlag = 0;
            }
        });

        waterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
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
                enabledAll(true);
                board.setVisibility(View.GONE);
                soilCancel.setVisibility(View.GONE);
                soilOk.setVisibility(View.GONE);
                soilProgress.setVisibility(View.GONE);
                //data
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                            count++;
                            if (count == outdoorFragmentNumber){
                                int newSoilNum = mp.getSoilFertility() + soilNum / 3;
                                int id = mp.getId();
                                MyPlant  mp2 = new MyPlant();
                                mp2.setSoilFertility(newSoilNum > 100 ? 100 : newSoilNum);
                                mp2.update(id);
                                Toast.makeText(getActivity(), "已成功松土！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                soilNum = 0;
                soilProgress.setProgress(0);
                soilFlag = 0;
            }
        });

        soilCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
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
                enabledAll(true);
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
                fertilizerProgress.setVisibility(View.GONE);
                //data
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                            count++;
                            if (count == outdoorFragmentNumber){
                                int newFertilizerNum = mp.getLeafCondition() + fertilizerNum / 3;
                                int id = mp.getId();
                                MyPlant  mp2 = new MyPlant();
                                mp2.setLeafCondition(newFertilizerNum > 100 ? 100 : newFertilizerNum);
                                mp2.update(id);
                                Toast.makeText(getActivity(), "已成功施肥！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                fertilizerNum = 0;
                fertilizerProgress.setProgress(0);
                fertilizerFlag = 0;
            }
        });

        fertilizerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
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
                enabledAll(true);
                board.setVisibility(View.GONE);
                peatCancel.setVisibility(View.GONE);
                pestOk.setVisibility(View.GONE);
                pestProgress.setVisibility(View.GONE);
                //data
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 6 && p.getId() <= 8){
                            count++;
                            if (count == outdoorFragmentNumber){
                                int newPestNum = mp.getPestsContent() + pestNum / 3;
                                int id = mp.getId();
                                MyPlant  mp2 = new MyPlant();
                                mp2.setPestsContent(newPestNum > 100 ? 100 : newPestNum);
                                mp2.update(id);
                                Toast.makeText(getActivity(), "已成功除虫！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                pestNum = 0;
                pestProgress.setProgress(0);
                pestFlag = 0;
            }
        });

        peatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabledAll(true);
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
                enabledAll(true);
                //Log.i("test", "自定义按钮处理单击");
                //watersp.stop(watermusic);

            }
        });
        waterProgress.setOnLongClickListener(new View.OnLongClickListener() {
            int waterid = 0;
            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                waterid = watersp.play(watermusic, 1, 1, 0, 0, 1);

                return false;

            }

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    watersp.stop(waterid);
                }
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
                //fertilizersp.stop(fertilizermusic);

            }
        });
        fertilizerProgress.setOnLongClickListener(new View.OnLongClickListener() {
            int fertizerid = 0;
            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                fertizerid = fertilizersp.play(fertilizermusic, 1, 1, 0, 0, 1);
                return false;
            }

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    watersp.stop(fertizerid);
                }
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
                //soilsp.stop(soilmusic);

            }
        });
        soilProgress.setOnLongClickListener(new View.OnLongClickListener() {
            int soilid;
            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                soilid = soilsp.play(soilmusic, 1, 1, 0, 0, 1);
                return false;
            }

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    soilsp.stop(soilid);
                }
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
                //spraysp.stop(spraymusic);

            }
        });
        pestProgress.setOnLongClickListener(new View.OnLongClickListener() {
            int pestid = 0;
            @Override
            public boolean onLongClick(View v) {
                //Log.i("test", "自定义按钮处理长按一次相应");
                pestid = spraysp.play(spraymusic, 1, 1, 0, 0, 1);
                return false;
            }

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    spraysp.stop(pestid);
                }
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

        removeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeBoard.setVisibility(View.GONE);
            }
        });

        removeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据库删除后刷新界面
                int id = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                for(MyPlant mp : mps){
                    if(mp.getFragment() == outdoorFragmentNumber + 5){
                        id = mp.getId();
                        break;
                    }
                }
                DataSupport.delete(MyPlant.class, id);
                getData();
                removeBoard.setVisibility(View.GONE);

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
        int level = -1;
        int waterContent = -1;
        boolean has = false;
        List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
        for(MyPlant mp : mps) {
            if(mp.getFragment() == outdoorFragmentNumber + 5) {
                has = true;
                plantName = mp.getPlant();
                level = mp.getLevel() == 0 ? 0 : mp.getLevel();
                waterContent = mp.getWaterContent();
                break;
            }
        }
        if(has){
            //根据level大小和水分设置植物图片
            if (level == 0){
                imageView.setImageResource(R.drawable.outdoor_l1);
            }
            else if (level > 0 && level < 100 && waterContent < 10){
                imageView.setImageResource(R.drawable.outdoor_l2u);
            }
            else if (level > 0 && level < 100 && waterContent >= 10){
                imageView.setImageResource(R.drawable.outdoor_l2);
            }
            else if (level >= 100 && level < 150 && waterContent < 10){
                imageView.setImageResource(R.drawable.outdoor_l3u);
            }
            else if (level >= 100 && level < 150 && waterContent >= 10){
                imageView.setImageResource(R.drawable.outdoor_l3);
            }
            else if (level == 150){
                imageView.setImageResource(R.drawable.outdoor_l4);
            }
            //为image设置点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslateAnimation animation = ViewAnimation.enterAnimation("left");
                    tools.startAnimation(animation);
                    tools.setVisibility(View.VISIBLE);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    removeBoard.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }
        else{
            imageView.setImageResource(R.drawable.outdoor_soil);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getActivity(), OutdoorsSeedActivity.class);
                    intent1.putExtra("fragment", outdoorFragmentNumber + 5);
                    startActivity(intent1);
                }
            });

        }
    }
    int returnNum(int a){
        if(a>0&&a<=30)
            return 1;
        else if (a>30&&a<=60)
            return 2;
        else if (a>60)
            return 3;
        else
            return 0;
    }


    public void enabledAll(Boolean value) {
        imageView.setEnabled(value);
        water.setEnabled(value);
        soil.setEnabled(value);
        fertilizer.setEnabled(value);
        pest.setEnabled(value);
        outdoorWatch.setEnabled(value);
    }

}
