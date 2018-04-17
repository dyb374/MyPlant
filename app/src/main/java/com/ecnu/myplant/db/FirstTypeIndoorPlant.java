package com.ecnu.myplant.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Andrew Dong on 2018/4/17.
 */

public class FirstTypeIndoorPlant extends DataSupport {
    private int state_of_life;//生命状态0-死亡，1-健康，2-不健康
    private int soilColor;//取值为0-100，从而近似为连续取值
    private int leafColor;//取值为0-100
    private int level;//根据成长情况取值，比如0-50是小芽但慢慢变大，51-150是小植株

    public int getState_of_life() {
        return state_of_life;
    }

    public void setState_of_life(int state_of_life) {
        this.state_of_life = state_of_life;
    }

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
