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

public class SeedAdapter extends ArrayAdapter<Seed> {

    private int resourceId;

    public SeedAdapter(Context context, int textViewResourceId,
                       List<Seed> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Seed seed  = getItem(position); // 获取当前项的Seed实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.seedImage = (ImageView) view.findViewById (R.id.seeds_image);
            viewHolder.seedName = (TextView) view.findViewById (R.id.seeds_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.seedImage.setImageResource(seed.getImageId());
        viewHolder.seedName.setText(seed.getName());
        return view;
    }

    class ViewHolder {

        ImageView seedImage;

        TextView seedName;

    }

}
