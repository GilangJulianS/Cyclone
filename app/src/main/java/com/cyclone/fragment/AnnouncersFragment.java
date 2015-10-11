package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.custom.AnnouncersAdapter;

/**
 * Created by gilang on 11/10/2015.
 */
public class AnnouncersFragment extends Fragment {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	public AnnouncersFragment(){}

	public static AnnouncersFragment newInstance(){
		AnnouncersFragment fragment = new AnnouncersFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		layoutManager = new GridLayoutManager(getContext(), 3);
		recyclerView.setLayoutManager(layoutManager);

		adapter = new AnnouncersAdapter(getContext(), "");
		recyclerView.setAdapter(adapter);
		return v;
	}
}
