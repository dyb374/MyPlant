package com.ecnu.myplant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.AudioManager;
import android.content.Context;


import com.ecnu.myplant.R;
import com.ecnu.myplant.SeedActivity;
import com.ecnu.myplant.db.MyPlant;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.service.ViewAnimation;
import com.ecnu.myplant.LongTouchBtn;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Andrew Dong on 2018/4/19.
 * fragment for第一盆室内植物
 */

public class FragmentOne extends Fragment {
    private int indoorFragmentNumber = 1;
    int waterNum = 0;
    int fertilizerNum = 0;
    int waterFlag = 0;
    int fertilizerFlag = 0;
    ImageView imageView = null;
    LinearLayout tools = null;
    boolean has = false;
    String plantName = null;
    ImageView watchSoil;
    ImageView fertilizer;
    ImageView water;
    FrameLayout board;
    ImageView watchLeaf;
    ImageView waterOk;
    ImageView fertilizerOk;
    ImageView waterCancel;
    ImageView fertilizerCancel;
    ImageView watchOk;
    LongTouchBtn waterProgress;
    LongTouchBtn fertilizerProgress;
    ImageView indoorWatch;
    SoundPool fertilizersp;
    int fertilizermusic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);//盆栽
        tools = (LinearLayout) view.findViewById(R.id.indoor_tools);
        indoorWatch = (ImageView) view.findViewById(R.id.indoor_watch);//观察按钮
        fertilizer = (ImageView) view.findViewById(R.id.indoor_fertilizer);//施肥按钮
        water = (ImageView) view.findViewById(R.id.indoor_water);//浇水按钮
        board = (FrameLayout) view.findViewById(R.id.board);//面板
        watchSoil = (ImageView) view.findViewById(R.id.watch_soil);//观察面板的土壤
        watchLeaf = (ImageView) view.findViewById(R.id.watch_leaf);//观察面板的叶子
        waterOk = (ImageView) view.findViewById(R.id.indoor_water_ok);//浇水面板确认键
        fertilizerOk = (ImageView) view.findViewById(R.id.indoor_fertilizer_ok);//施肥面板确认键
        waterCancel = (ImageView) view.findViewById(R.id.indoor_water_cancel);//浇水面板取消键
        fertilizerCancel = (ImageView) view.findViewById(R.id.indoor_fertilizer_cancel); //施肥面板取消键
        watchOk = (ImageView) view.findViewById(R.id.indoor_watch_ok);//观察面板确认键
        waterProgress = (LongTouchBtn) view.findViewById(R.id.water_progress);//浇水进度条
        fertilizerProgress = (LongTouchBtn) view.findViewById(R.id.fertilizer_progress);//施肥进度条
        fertilizersp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        fertilizermusic = fertilizersp.load(this.getActivity(),R.raw.pesticide,1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明

        indoorWatch.setOnClickListener(new View.OnClickListener() {//观察按钮监听器
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
                fertilizerCancel.setVisibility(View.VISIBLE);
                fertilizerOk.setVisibility(View.VISIBLE);
                fertilizerProgress.setVisibility(View.VISIBLE);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {//浇水按钮监听器
            @Override
            public void onClick(View view) {
                board.setVisibility(View.VISIBLE);
                waterCancel.setVisibility(View.VISIBLE);
                waterOk.setVisibility(View.VISIBLE);
                waterProgress.setVisibility(View.VISIBLE);
            }
        });

        watchOk.setOnClickListener(new View.OnClickListener() {//观察面板确认键
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                watchOk.setVisibility(View.GONE);
                watchLeaf.setVisibility(View.GONE);
                watchSoil.setVisibility(View.GONE);
            }
        });

        waterOk.setOnClickListener(new View.OnClickListener() {//浇水面板确认键
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                waterCancel.setVisibility(View.GONE);
                waterOk.setVisibility(View.GONE);
                waterProgress.setVisibility(View.GONE);
                //数据库更新操作,获取waterNum数据，之后waterNum归零
                int count = 0;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 1 && p.getId() <= 5){
                            count++;
                            if (count == indoorFragmentNumber){
                                int newWaterNum = mp.getWaterContent() + waterNum / 3;
                                if (newWaterNum >= 0 && newWaterNum <= 100){
                                    int id = mp.getId();
                                    MyPlant  mp2 = new MyPlant();
                                    mp2.setWaterContent(newWaterNum);
                                    mp2.update(id);
                                    Toast.makeText(getActivity(), "已成功浇水：" + waterNum / 3 + "！", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getActivity(), "植物水量已满，无法浇水！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                waterNum = 0;
                waterProgress.setProgress(0);
                waterFlag = 0;
            }
        });

        waterCancel.setOnClickListener(new View.OnClickListener() {//浇水面板取消键
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

        fertilizerOk.setOnClickListener(new View.OnClickListener() {//施肥面板确认键
            @Override
            public void onClick(View view) {
                board.setVisibility(View.GONE);
                fertilizerCancel.setVisibility(View.GONE);
                fertilizerOk.setVisibility(View.GONE);
                fertilizerProgress.setVisibility(View.GONE);
                //数据库更新操作，获取fertilizerNum数据，之后fertilizerNum归零
                int count = 0;

                fertilizersp.play(fertilizermusic, 1, 1, 0, 0, 1);//开启音频,(对音频文件播放的设置 例如左右声道等)
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                List<Plant> ps = DataSupport.findAll(Plant.class);
                for(MyPlant mp : mps) {
                    for(Plant p : ps) {
                        if(p.getName().equals(mp.getPlant()) && p.getId() >= 1 && p.getId() <= 5){
                            count++;
                            if (count == indoorFragmentNumber){
                                int newFertilizerNum = mp.getLeafCondition() + fertilizerNum / 3;
                                if (newFertilizerNum >= 0 && newFertilizerNum <= 100){
                                    int id = mp.getId();
                                    MyPlant  mp2 = new MyPlant();
                                    mp2.setLeafCondition(newFertilizerNum);
                                    mp2.update(id);
                                    Toast.makeText(getActivity(), "已成功施肥：" + fertilizerNum / 3 + "！", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getActivity(), "土壤肥度已满，无法施肥！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                fertilizerNum = 0;
                fertilizerProgress.setProgress(0);
                fertilizerFlag = 0;
            }
        });

        fertilizerCancel.setOnClickListener(new View.OnClickListener() {//施肥面板取消键
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

        //获取数据
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
                    if (count == indoorFragmentNumber){
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
