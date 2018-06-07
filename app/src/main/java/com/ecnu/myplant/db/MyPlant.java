package com.ecnu.myplant.db;

import android.service.autofill.Dataset;

import org.litepal.crud.DataSupport;

/**
 * Created by xsk on 2018/6/7.
 */

public class MyPlant extends DataSupport{
    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    private String plant;

}
