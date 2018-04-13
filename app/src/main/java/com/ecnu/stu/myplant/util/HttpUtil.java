package com.ecnu.stu.myplant.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Andrew Dong on 2018/4/12.
 * 和服务器进行交互
 */

public class HttpUtil {

    public static void sendOkHttpRequest (String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
