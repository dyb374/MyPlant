package com.ecnu.myplant.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Andrew Dong on 2018/4/17.
 */

public class FirstTypeIndoorPlant extends DataSupport {
    private int id;
    private int fId;//取值0-8，0表示不在fragment中显示，1-8表示显示在哪个fragment中
    private int stateOfLife;//生命状态0-死亡，1-健康，2-不健康
    private int soilColor;//取值为0-100，从而近似为连续取值
    private int leafColor;//取值为0-100
    private int level;//根据成长情况取值，比如0-50是小芽但慢慢变大，51-150是小植株

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}



    public int getSoilColor() {
        return soilColor;
    }

    public void setSoilColor(int soilColor) {
        this.soilColor = soilColor;
    }

    public int getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(int leafColor) {
        this.leafColor = leafColor;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
