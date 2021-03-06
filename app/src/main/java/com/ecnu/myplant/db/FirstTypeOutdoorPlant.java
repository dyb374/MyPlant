package com.ecnu.myplant.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Andrew Dong on 2018/4/17.
 */

public class FirstTypeOutdoorPlant extends DataSupport {
    private int id;
    private String name;
    private int fId;//取值0-8，0表示不在fragment中显示，1-8表示显示在哪个fragment中
    private int stateOfLife;//生命状态0-死亡，1-健康，2-不健康
    private int level;//根据成长情况取值，比如0-50是小芽但慢慢变大，51-150是小植株
    private int waterContent;//0-100
    private int soilFertility;//0-100
    private int leafCondition;//0-100
    private int pestsContent;//0-100


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
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

    public int getPestsContent() {
        return pestsContent;
    }

    public void setPestsContent(int pestsContent) {
        this.pestsContent = pestsContent;
    }
}
