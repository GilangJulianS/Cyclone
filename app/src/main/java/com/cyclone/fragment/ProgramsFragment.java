package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.custom.ProgramsAdapter;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramsFragment extends Fragment {

	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private RecyclerView.Adapter mAdapter;

	public ProgramsFragment(){}

	public static ProgramsFragment newInstance(){
		ProgramsFragment fragment = new ProgramsFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		mLayoutManager = new GridLayoutManager(getActivity(), 2);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new ProgramsAdapter(getContext(), "");
		mRecyclerView.setAdapter(mAdapter);

		return v;
	}
}
