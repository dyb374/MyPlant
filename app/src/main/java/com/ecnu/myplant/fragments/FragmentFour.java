package com.ecnu.myplant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecnu.myplant.R;

/**
 * Created by Andrew Dong on 2018/4/19.
 * 第四盆室内植物
 */

public class FragmentFour extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //植物的不同成长阶段就加载不同的layout
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }
}
