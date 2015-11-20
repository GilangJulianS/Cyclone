package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.custom.CustomPagerAdapter;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.ProgramContent;
import com.cyclone.model.ProgramPager;
import com.cyclone.model.RunningProgram;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 06/11/2015.
 */
public class LiveStreamFragment extends RecyclerFragment {

	public LiveStreamFragment(){}

	public static LiveStreamFragment newInstance(String json){
		LiveStreamFragment fragment = new LiveStreamFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
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

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		List<String> images = new ArrayList<>();
		images.add("");
		images.add("");
		images.add("");
		datas.add(new ProgramPager(images, 1));
		datas.add(new RunningProgram("The Dandees", "The Most Wanted Guys In Town"));
		datas.add(new ProgramContent(ProgramContent.TYPE_MUSIC, "10:21", "Biru - Afgan"));
		datas.add(new ProgramContent(ProgramContent.TYPE_MUSIC, "10:18", "Stop Look Listen - " +
				"Patti Austin"));
		datas.add(new ProgramContent(ProgramContent.TYPE_COMMERCIAL, "10:12", "Indihouse Fiber " +
				"Spot"));
		datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station Prambors"));
		datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station 1"));
		datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station Closing 1"));

		return datas;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.streamplayer, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
			case R.id.btn_new_request:
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_REQUEST);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Request");
				getActivity().startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}

