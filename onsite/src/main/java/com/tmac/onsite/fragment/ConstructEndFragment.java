package com.tmac.onsite.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tmac.onsite.R;
import com.tmac.onsite.adapter.DisPlayAdapter;
import com.tmac.onsite.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16/10/5.
 */

public class ConstructEndFragment extends Fragment {

    private ListView listView;
    private DisPlayAdapter adapter;
    private List<ImageBean> list;
    private Bitmap bitmap1;
    private Bitmap bitmap2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_construct_pre, container, false);
        return contextView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.display_listview);
        list = new ArrayList<>();
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.nobegin_audio);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.play_audio);
        list.add(new ImageBean(bitmap1));
        list.add(new ImageBean(bitmap2));
        adapter = new DisPlayAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}
