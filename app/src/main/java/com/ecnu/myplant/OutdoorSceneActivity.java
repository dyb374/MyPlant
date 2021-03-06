package com.ecnu.myplant;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecnu.myplant.fragments.FragmentEight;
import com.ecnu.myplant.fragments.FragmentFive;
import com.ecnu.myplant.fragments.FragmentFour;
import com.ecnu.myplant.fragments.FragmentOne;
import com.ecnu.myplant.fragments.FragmentSeven;
import com.ecnu.myplant.fragments.FragmentSix;
import com.ecnu.myplant.fragments.FragmentThree;
import com.ecnu.myplant.fragments.FragmentTwo;


public class OutdoorSceneActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private OutdoorSceneActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ImageView image;
    private ImageView imageMap;
    private ImageView imageWeather;
    private ImageView imageAchievement;
    private ImageView changeScene;
    private boolean isAdd = false;
    private AnimatorSet addBillTranslate1;
    private AnimatorSet addBillTranslate2;
    private AnimatorSet addBillTranslate3;
    private int[] llId = new int[]{R.id.ll01, R.id.ll02, R.id.ll03};
    private LinearLayout[] ll = new LinearLayout[llId.length];
    private RelativeLayout addBill;//fab按钮点击后弹出的布局
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_scene);

        /*
        //最上方工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        initView();
        setDefaultValues();


        //悬浮按钮操作
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdd = !isAdd;
                addBill.setVisibility(isAdd ? View.VISIBLE : View.GONE);
                if (isAdd) {
                    addBillTranslate1.setTarget(ll[0]);
                    addBillTranslate1.start();
                    addBillTranslate2.setTarget(ll[1]);
                    addBillTranslate2.setStartDelay(100);
                    addBillTranslate2.start();
                    addBillTranslate3.setTarget(ll[2]);
                    addBillTranslate3.setStartDelay(200);
                    addBillTranslate3.start();
                }
                hideFAB();
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(IndoorSceneActivity.this, "Test", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                */
            }
        });
        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFABMenu();
                showFAB();
            }
        });

        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorSceneActivity.this, MapActivity.class);
                startActivity(intent);
                hideFABMenu();
                showFAB();
            }
        });
        imageWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorSceneActivity.this, WeatherActivity.class);
                startActivity(intent);
                hideFABMenu();
                showFAB();
            }
        });
        imageAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorSceneActivity.this, AchieveActivity.class);
                startActivity(intent);
                hideFABMenu();
                showFAB();
            }
        });


        //切换场景按钮
        changeScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorSceneActivity.this, IndoorSceneActivity.class);
                startActivity(intent);
            }
        });
    }


    //隐藏fab菜单按钮时的操作
    private void hideFABMenu(){
        addBill.setVisibility(View.GONE);
        isAdd = false;
    }

    //隐藏fab按钮时的操作
    private void hideFAB(){
        image.setVisibility(View.GONE);
    }

    private void showFAB(){
        image.setVisibility(View.VISIBLE);
    }

    //实例化控件
    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        imageMap = (ImageView) findViewById(R.id.miniImage01);
        imageWeather = (ImageView) findViewById(R.id.miniImage02);
        imageAchievement = (ImageView) findViewById(R.id.miniImage03);
        changeScene = (ImageView) findViewById(R.id.change_scene);
        addBill = (RelativeLayout) findViewById(R.id.addBill);

        // Create the adapter that will return a fragment
        // primary sections of the activity.
        mSectionsPagerAdapter = new OutdoorSceneActivity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        for (int i = 0; i < llId.length; i++) {
            ll[i] = (LinearLayout) findViewById(llId[i]);
        }
    }

    private void setDefaultValues() {
        addBillTranslate1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
        addBillTranslate3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.add_bill_anim);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outdoor_scene, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentSix();
                case 1:
                    return new FragmentSeven();
                case 2:
                    return new FragmentEight();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
