package com.ecnu.myplant;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SeedActivity extends AppCompatActivity {

    private ImageView back;
    private int[] llId = new int[]{R.id.ll01, R.id.ll02, R.id.ll021, R.id.ll022, R.id.ll023, R.id.ll024};
    private LinearLayout[] ll = new LinearLayout[llId.length];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeedActivity.this, IndoorSceneActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initView() {
        back = (ImageView) findViewById(R.id.return_main);
        // Set up the ViewPager with the sections adapter.

        for (int i = 0; i < llId.length; i++) {
            ll[i] = (LinearLayout) findViewById(llId[i]);
        }
    }
}