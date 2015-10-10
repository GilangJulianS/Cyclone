package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;

/**
 * Created by gilang on 09/10/2015.
 */
public class GetStartedFragment extends Fragment{

	public GetStartedFragment(){}

	public static GetStartedFragment newInstance(){
		GetStartedFragment fragment = new GetStartedFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_get_started, parent, false);
		return v;
	}
}
