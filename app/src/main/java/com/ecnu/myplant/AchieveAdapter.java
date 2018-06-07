package com.ecnu.myplant;

/**
 * Created by Type1551 on 2018/6/7.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AchieveAdapter extends ArrayAdapter<Achieve> {

    private int resourceId;

    public AchieveAdapter(Context context, int textViewResourceId,
                       List<Achieve> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Achieve achieve  = getItem(position); // 获取当前项的Achieve实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.achieveImage = (ImageView) view.findViewById (R.id.achieve_image);
            viewHolder.achieveName = (TextView) view.findViewById (R.id.achieve_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.achieveImage.setImageResource(achieve.getImageId());
        viewHolder.achieveName.setText(achieve.getName());
        return view;
    }

    class ViewHolder {

        ImageView achieveImage;

        TextView achieveName;

    }

}
