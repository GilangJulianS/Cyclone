package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.custom.ProgramAdapter;
import com.cyclone.model.Program;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramFragment extends Fragment {

	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private RecyclerView.Adapter mAdapter;

	public ProgramFragment(){}

	public static ProgramFragment newInstance(){
		ProgramFragment fragment = new ProgramFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_programs, parent, false);
		mRecyclerView = (RecyclerView) v.findViewById(R.id.program_container);

		mLayoutManager = new GridLayoutManager(getActivity(), 2);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new ProgramAdapter("");
		mRecyclerView.setAdapter(mAdapter);

		return v;
	}
}
