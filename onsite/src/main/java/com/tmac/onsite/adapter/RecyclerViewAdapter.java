package com.tmac.onsite.adapter;

import java.io.File;
import java.util.ArrayList;

import me.tmac.photopicker.PhotoPreview;
import me.tmac.photopicker.utils.AndroidLifecycleUtils;

import com.bumptech.glide.Glide;
import com.tmac.onsite.R;
import com.tmac.onsite.activity.UploadImgActivity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PhotoViewHolder>{

	
	private ArrayList<String> photoPaths = new ArrayList<String>();
	private LayoutInflater inflater;

	private Context mContext;
	
	
	 public RecyclerViewAdapter(Context mContext, ArrayList<String> photoPaths) {
	    this.photoPaths = photoPaths;
	    this.mContext = mContext;
	    inflater = LayoutInflater.from(mContext);
	
	  }

	@Override
	public void onBindViewHolder(PhotoViewHolder holder, final int position) {
		// TODO Auto-generated method stub
		Uri uri = Uri.fromFile(new File(photoPaths.get(position)));

	    boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.iv.getContext());

	    if (canLoadImage) {
	      Glide.with(mContext)
	              .load(uri)
	              .centerCrop()
	              .thumbnail(0.1f)
	              .placeholder(R.drawable.__picker_ic_photo_black_48dp)
	              .error(R.drawable.__picker_ic_broken_image_black_48dp)
	              .into(holder.iv);
	    }

		holder.iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PhotoPreview.builder()
						.setPhotos(photoPaths)
						.setCurrentItem(position)
						.start((Activity) mContext);
			}
		});

		holder.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				photoPaths.remove(position);
				notifyDataSetChanged();
			}
		});

	}

	@Override
	public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View itemView = inflater.inflate(R.layout.recy_item, parent, false);
		return new PhotoViewHolder(itemView);
	}
	
	public static class PhotoViewHolder extends RecyclerView.ViewHolder {
		private ImageView iv;
		private ImageView delete;

		public PhotoViewHolder(View itemView) {
			super(itemView);
			iv = (ImageView) itemView.findViewById(R.id.recy_img);
			delete = (ImageView) itemView.findViewById(R.id.delete);
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return photoPaths.size();
	}
	
}
