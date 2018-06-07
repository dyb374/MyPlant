package com.ecnu.myplant;

import android.content.Intent;
import android.drm.ProcessedData;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ecnu.myplant.db.MyPlant;
import com.ecnu.myplant.db.Plant;
import com.ecnu.myplant.db.ProvincePlant;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class AchieveActivity extends AppCompatActivity {

    private ImageView back;
    private LinearLayout linlar0;
    private LinearLayout linlar1;
    private LinearLayout linlar2;
    private List<Achieve> achieveList = new ArrayList<Achieve>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        back = (ImageView) findViewById(R.id.return_main);
        // Set up the ViewPager with the sections adapter.
        linlar0  = (LinearLayout) findViewById(R.id.linla0);
        linlar1  = (LinearLayout) findViewById(R.id.linla1);
        linlar2  = (LinearLayout) findViewById(R.id.linla2);
        Intent intent = getIntent();
        int plantId = intent.getIntExtra("plantId", -1);
        initAchieves(plantId); // 初始化Achieve数据
        AchieveAdapter adapter = new AchieveAdapter(AchieveActivity.this, R.layout.achieve_item, achieveList);
        ListView listView = (ListView) findViewById(R.id.list_viewex);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                com.ecnu.myplant.Achieve achieve = achieveList.get(position);
                boolean has = false;
                List<MyPlant> mps = DataSupport.findAll(MyPlant.class);
                for(MyPlant mp : mps) {
                    if (mp.getPlant().equals(achieve.getName())) {
                        has = true;
                        Toast.makeText(AchieveActivity.this, "你已领养了植物:" + achieve.getName() + "！", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!has){
                    MyPlant mp = new MyPlant();
                    mp.setPlant(achieve.getName());
                    mp.save();
                    Toast.makeText(AchieveActivity.this, "成功领养植物："+ achieve.getName() +"！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AchieveActivity.this, IndoorSceneActivity.class);
                    startActivity(intent);
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AchieveActivity.this, IndoorSceneActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initAchieves(int plantId) {
        List<ProvincePlant> pps = DataSupport.findAll(ProvincePlant.class);
        for(ProvincePlant pp : pps) {
            String plantName = pp.getPlant();
            List<Plant> plants = DataSupport.findAll(Plant.class);
            for(Plant p : plants){
                if(plantName.equals(p.getName()) && p.getId() == plantId) {
                    Achieve achieve = new Achieve(plantName, R.drawable.ok);
                    achieveList.add(achieve);
                }
            }
        }
    }
}