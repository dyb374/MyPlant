package com.ecnu.myplant.db;

import android.media.Image;

import org.litepal.crud.DataSupport;

/**
 * Created by xsk on 2018/6/6.
 */

public class Plant extends DataSupport{
    private int plantId;
    private String name;

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
