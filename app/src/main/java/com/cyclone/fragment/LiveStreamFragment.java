package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.CustomPagerAdapter;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.ProgramContent;
import com.cyclone.model.RunningProgram;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 06/11/2015.
 */
public class LiveStreamFragment extends Fragment {

	private ViewPager viewPager;
	private CustomPagerAdapter pagerAdapter;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private UniversalAdapter adapter;
	private List<Object> objects;
	private SwipeRefreshLayout swipeLayout;
	private CirclePageIndicator indicator;

	public LiveStreamFragment(){}

	public static LiveStreamFragment newInstance(){
		LiveStreamFragment fragment = new LiveStreamFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeLayout.setRefreshing(false);
						adapter.datas.clear();
						adapter.notifyDataSetChanged();
						objects = parse("");
						animate(objects.get(0));
					}
				}, 5000);
			}
		});

		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		recyclerView.setAdapter(adapter);

		objects = parse("");

		animate(objects.get(0));


		return v;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		AppCompatActivity activity;
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
			ViewGroup header = (ViewGroup) activity.findViewById(R.id.header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View v = inflater.inflate(R.layout.part_header_live_streaming, header,false);
			viewPager = (ViewPager) v.findViewById(R.id.view_pager);
			indicator = (CirclePageIndicator) v.findViewById(R.id.view_pager_indicator);
			pagerAdapter = new CustomPagerAdapter(activity.getSupportFragmentManager());
			pagerAdapter.add(SimpleImageFragment.newInstance("/* your image url here */"));
			pagerAdapter.add(SimpleImageFragment.newInstance("/* your image url here */"));
			pagerAdapter.add(SimpleImageFragment.newInstance("/* your image url here */"));
			viewPager.setAdapter(pagerAdapter);
			viewPager.setCurrentItem(1);
			indicator.setViewPager(viewPager);
			header.removeAllViews();
			header.addView(v);
		}
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
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

	private void animate(final Object object){
		final Handler handler = new Handler();
		final Object o = object;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(o);
				objects.remove(o);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!objects.isEmpty()) {
					animate(objects.get(0));
				}
			}
		}, 50);
	}
}

