package com.ecnu.myplant.db;

import android.service.autofill.Dataset;

import org.litepal.crud.DataSupport;

/**
 * Created by xsk on 2018/6/7.
 */

public class MyPlant extends DataSupport{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getStateOfLife() {
        return stateOfLife;
    }

    public void setStateOfLife(int stateOfLife) {
        this.stateOfLife = stateOfLife;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWaterContent() {
        return waterContent;
    }

    public void setWaterContent(int waterContent) {
        this.waterContent = waterContent;
    }

    public int getSoilFertility() {
        return soilFertility;
    }

    public void setSoilFertility(int soilFertility) {
        this.soilFertility = soilFertility;
    }

    public int getLeafCondition() {
        return leafCondition;
    }

    public void setLeafCondition(int leafCondition) {
        this.leafCondition = leafCondition;
    }

    private int id;
    private String plant;
    private int stateOfLife;//生命状态0-死亡，1-健康，2-不健康
    private int level;//根据成长情况取值，比如0-50是小芽但慢慢变大，51-150是小植株
    private int waterContent;//0-100
    private int soilFertility;//0-100
    private int leafCondition;//0-100
    private int pestsContent;//0-100

    public int getPestsContent() {
        return pestsContent;
    }

    public void setPestsContent(int pestsContent) {
        this.pestsContent = pestsContent;
    }
}
