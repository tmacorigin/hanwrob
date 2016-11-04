/**
 * 
 */
package com.tmac.onsite.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.NoBeginBean;
import com.tmac.onsite.bean.TaskBean;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tmac
 */
public class NoBeginAdapter extends CommonAdapter<TaskBean> {

	public NoBeginAdapter(Context context, List<TaskBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nobegin_list_item, parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			holder.read_state_img = (ImageView) convertView.findViewById(R.id.read_state_img);
			holder.timeRemin = (TextView) convertView.findViewById(R.id.tv_time);
			holder.number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.area = (TextView) convertView.findViewById(R.id.tv_workarea);
			holder.finishTime = (TextView) convertView.findViewById(R.id.tv_finishtime);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(list.get(position).getRobState().equals("0")){
			holder.imageView.setVisibility(View.VISIBLE);
		}else {
			holder.imageView.setVisibility(View.INVISIBLE);
		}
		if(list.get(position).getReadState().equals("0")){
			holder.read_state_img.setVisibility(View.VISIBLE);
		}else {
			holder.read_state_img.setVisibility(View.INVISIBLE);
		}
		holder.number.setText(list.get(position).getTaskId());
		holder.area.setText(list.get(position).getPreformAddress());
		holder.finishTime.setText(list.get(position).getFinishedTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = sdf.parse(list.get(position).getFinishedTime());
			Date date2 = new Date();
			int days = (int)((date1.getTime() - date2.getTime())/86400000);
			holder.timeRemin.setText(days + "");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	static class ViewHolder{
		public ImageView imageView;
		public ImageView read_state_img;
        public TextView timeRemin;
        public TextView number;
        public TextView area;
        public TextView finishTime; 
	}
	
	/**
     * 解决ViewPager嵌套ListView，退出App报IllegalArgumentException:The observer is
     * null错的问题。
     */
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

}
