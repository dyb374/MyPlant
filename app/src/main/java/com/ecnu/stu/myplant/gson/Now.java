package com.ecnu.stu.myplant.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew Dong on 2018/4/13.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}
