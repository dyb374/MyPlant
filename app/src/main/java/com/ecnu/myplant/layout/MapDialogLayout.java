package com.ecnu.myplant.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ecnu.myplant.R;

/**
 * Created by Andrew Dong on 2018/6/6.
 */

public class MapDialogLayout extends LinearLayout {
    public MapDialogLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.map_dialog_layout, this);

    }
}
