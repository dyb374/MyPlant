package com.ecnu.myplant;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeedActivity extends AppCompatActivity {

    private ImageView back;
    private LinearLayout linlar;
    private List<Seed> seedList = new ArrayList<Seed>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        back = (ImageView) findViewById(R.id.return_main);
        // Set up the ViewPager with the sections adapter.
        linlar  = (LinearLayout) findViewById(R.id.linla);
        initSeeds(); // 初始化seed数据
        SeedAdapter adapter = new SeedAdapter(SeedActivity.this, R.layout.seed_item, seedList);
        ListView listView = (ListView) findViewById(R.id.list_viewex);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Seed seed = seedList.get(position);
                Toast.makeText(SeedActivity.this, seed.getName(), Toast.LENGTH_SHORT).show();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeedActivity.this, IndoorSceneActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initSeeds() {
        for (int i = 0; i < 2; i++) {
            Seed apple = new Seed("Apple", R.drawable.ok);
            seedList.add(apple);
            Seed banana = new Seed("Banana", R.drawable.ok);
            seedList.add(banana);
            Seed orange = new Seed("Orange", R.drawable.ok);
            seedList.add(orange);
            Seed watermelon = new Seed("Watermelon", R.drawable.ok);
            seedList.add(watermelon);
            Seed pear = new Seed("Pear", R.drawable.ok);
            seedList.add(pear);
            Seed grape = new Seed("Grape", R.drawable.ok);
            seedList.add(grape);
            Seed pineapple = new Seed("Pineapple", R.drawable.ok);
            seedList.add(pineapple);
            Seed strawberry = new Seed("Strawberry", R.drawable.ok);
            seedList.add(strawberry);
            Seed cherry = new Seed("Cherry", R.drawable.ok);
            seedList.add(cherry);
            Seed mango = new Seed("Mango", R.drawable.ok);
            seedList.add(mango);
        }
    }
}