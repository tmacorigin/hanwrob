/**
 * 
 */
package com.tmac.onsite.fragment;

import com.tmac.onsite.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tmac
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener{

	private TextView check_update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_leftmenu, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		check_update = (TextView) view.findViewById(R.id.check_update);

		initEvents();
	}

	private void initEvents() {
		check_update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.check_update:

				break;

			default:
				break;
		}
	}
}
