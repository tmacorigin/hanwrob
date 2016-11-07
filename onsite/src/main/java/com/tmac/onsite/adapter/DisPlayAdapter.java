package com.tmac.onsite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.ImageBean;

import java.util.List;

/**
 * Created by user on 16/11/7.
 */

public class DisPlayAdapter extends CommonAdapter<ImageBean>{


    public DisPlayAdapter(Context context, List<ImageBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView display_iv;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.diaplay_lv_item, parent, false);
            display_iv = (ImageView) convertView.findViewById(R.id.display_item_iv);
            convertView.setTag(display_iv);
        }else {
            display_iv = (ImageView) convertView.getTag();
        }

        display_iv.setImageBitmap(list.get(position).getBitmap());

        return convertView;
    }



}
