package com.ecnu.stu.myplant.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew Dong on 2018/4/13.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    public class Comfort {

        @SerializedName("txt")
        public String info;
    }
}
