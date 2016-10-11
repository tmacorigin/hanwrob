package com.tmac.onsite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.MessageBean;

import java.util.List;

/**
 * Created by user on 16/10/10.
 */

public class MessageAdapter extends CommonAdapter<MessageBean> {


    public MessageAdapter(Context context, List<MessageBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_lv_item, parent, false);
            holder = new ViewHolder();
            holder.msg_content = (TextView) convertView.findViewById(R.id.msg_item_content);
            holder.msg_num = (TextView) convertView.findViewById(R.id.msg_item_num);
            holder.msg_date = (TextView) convertView.findViewById(R.id.msg_item_date);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.msg_content.setText(list.get(position).getMsg_content());
        holder.msg_num.setText(list.get(position).getMsg_num());
        holder.msg_date.setText(list.get(position).getMsg_date());
        return convertView;
    }

    class ViewHolder{

        private TextView msg_content;
        private TextView msg_num;
        private TextView msg_date;

    }


}
