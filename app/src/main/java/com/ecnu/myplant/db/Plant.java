package com.ecnu.myplant.db;

import android.media.Image;

import org.litepal.crud.DataSupport;

/**
 * Created by xsk on 2018/6/6.
 */

public class Plant extends DataSupport{
    private int id;
    private String name;

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
}
