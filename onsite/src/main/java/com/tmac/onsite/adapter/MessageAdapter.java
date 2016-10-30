package com.tmac.onsite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_lv_item, parent, false);
            holder = new ViewHolder();
            holder.msg_content = (TextView) convertView.findViewById(R.id.msg_item_content);
            holder.msg_num = (TextView) convertView.findViewById(R.id.msg_item_num);
            holder.msg_date = (TextView) convertView.findViewById(R.id.msg_item_date);
            holder.msg_delete = (ImageView) convertView.findViewById(R.id.msg_item_delete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.msg_content.setText(list.get(position).getMsg_content());
        holder.msg_num.setText(list.get(position).getMsg_num());
        if(mEditVisiable){
            holder.msg_delete.setVisibility(View.VISIBLE);
            holder.msg_date.setVisibility(View.GONE);
        }else {
            holder.msg_delete.setVisibility(View.GONE);
            holder.msg_date.setVisibility(View.VISIBLE);
            holder.msg_date.setText(list.get(position).getMsg_date());
        }
        holder.msg_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder{

        private TextView msg_content;
        private TextView msg_num;
        private TextView msg_date;
        private ImageView msg_delete;

    }

    private static boolean mEditVisiable = false;

    public void setEditVisiable(boolean editVisiable){
        mEditVisiable = editVisiable;
        notifyDataSetChanged();
    }

}
