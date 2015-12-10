package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.MasterActivity;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class AddMixFragment extends RecyclerFragment {

	public AddMixFragment(){}

	public static AddMixFragment newInstance(String json){
		AddMixFragment fragment = new AddMixFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		List<Mix> mixList;
		Mixes mixes;

		datas.add(new Section("Genre", "genre", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Rock"));
		mixList.add(new Mix("", "Pop"));
		mixList.add(new Mix("", "Electronic"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Artist", "artist", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Raisa"));
		mixList.add(new Mix("", "Daft Punk"));
		mixList.add(new Mix("", "Maroon 5"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Radio Content", "content", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "News"));
		mixList.add(new Mix("", "Travel"));
		mixList.add(new Mix("", "Talk"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		return datas;
	}
}
