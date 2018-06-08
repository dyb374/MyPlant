package com.ecnu.myplant.db;

import org.litepal.crud.DataSupport;

/**
 * Created by xsk on 2018/6/6.
 */

public class ProvincePlant extends DataSupport{
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String province;
    private String plant;

}
