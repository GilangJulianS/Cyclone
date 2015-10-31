package com.cyclone.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.custom.PersonAdapter;
import com.cyclone.custom.SongAdapter;
import com.cyclone.model.Person;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends Fragment implements GestureDetector.OnGestureListener{

	private RecyclerView recyclerView;
	private List<Song> datas;
	private SongAdapter adapter;
	private LinearLayoutManager layoutManager;
	private CollapseActivity activity;
	private GestureDetectorCompat gd;
	private ImageButton btnMinimize;
	private ViewGroup groupInfo, groupControl;
	private ImageView imgCover;

	public PlayerFragment(){}

	public static PlayerFragment newInstance(){
		PlayerFragment fragment = new PlayerFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);


		adapter = new SongAdapter(getActivity(), "");
		datas = parse("");
		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		recyclerView.setAdapter(adapter);

		animate(datas.get(0));

		if(activity != null){
			gd = new GestureDetectorCompat(activity, this);
			recyclerView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("touch recycler");
					if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && !activity.changing)
						return gd.onTouchEvent(event);
					else if(activity.changing)
						return true;
					return false;
				}
			});
		}

		return v;
	}



	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		AppCompatActivity activity;
		if(context instanceof CollapseActivity){
			this.activity = (CollapseActivity) context;
		}
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_player, parallaxHeader,
					false);
			bindHeaderView(header);
			parallaxHeader.addView(header);
		}
	}

	public void bindHeaderView(View v){
		btnMinimize = (ImageButton) v.findViewById(R.id.btn_minimize);
		btnMinimize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(activity != null){
					activity.changing = true;
					activity.appBarLayout.setExpanded(false);
				}
			}
		});
		groupInfo = (ViewGroup) v.findViewById(R.id.group_player_info);
		groupControl = (ViewGroup) v.findViewById(R.id.group_player_control);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		imgCover.setImageResource(R.drawable.background_login);
		Bitmap bitmap = ((BitmapDrawable)imgCover.getDrawable()).getBitmap();
		Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

			@Override
			public void onGenerated(Palette palette) {
				int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
				color = palette.getDarkVibrantColor(color);
				int r = (color >> 16) & 0xFF;
				int g = (color >> 8) & 0xFF;
				int b = (color >> 0) & 0xFF;
				System.out.println("RGB : " + r + " " + g + " " + b);
				r+=50;
				g+=50;
				b+=50;
				System.out.println("RGB : " + r + " " + g + " " + b);
				if(r > 255)
					r = 255;
				if(g > 255)
					g = 255;
				if(b > 255)
					b = 255;
				int lightColor = Color.rgb(r, g, b);
				groupInfo.setBackgroundColor(color);
				groupControl.setBackgroundColor(lightColor);
			}
		});
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		System.out.println("fling recycler: " + velocityX + " " + velocityY);
		AppBarLayout appBarLayout = activity.appBarLayout;
		if(Math.abs(velocityY) > 200){
			activity.changing = true;
			if(velocityY > 0){
				appBarLayout.setExpanded(true);
				System.out.println("expanded");
			}else if(velocityY < 0){
				appBarLayout.setExpanded(false);
				System.out.println("collapsed");
			}
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	private void animate(final Song song){
		final Handler handler = new Handler();
		final Song s = song;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(s);
				datas.remove(s);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animate(datas.get(0));
				}
			}
		}, 200);
	}

	public List<Song> parse(String json){
		List<Song> songs = new ArrayList<>();
		songs.add(new Song("The Celestials", "The Smashing Pumpkins", "03:20"));
		songs.add(new Song("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		songs.add(new Song("Drones", "Muse", "05:45"));
		songs.add(new Song("Extraordinary", "Clean Bandit", "04:48"));
		songs.add(new Song("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		songs.add(new Song("The Celestials", "The Smashing Pumpkins", "03:20"));
		songs.add(new Song("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		songs.add(new Song("Drones", "Muse", "05:45"));
		songs.add(new Song("Extraordinary", "Clean Bandit", "04:48"));
		songs.add(new Song("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		songs.add(new Song("The Celestials", "The Smashing Pumpkins", "03:20"));
		songs.add(new Song("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		songs.add(new Song("Drones", "Muse", "05:45"));
		songs.add(new Song("Extraordinary", "Clean Bandit", "04:48"));
		songs.add(new Song("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		songs.add(new Song("The Celestials", "The Smashing Pumpkins", "03:20"));
		songs.add(new Song("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		songs.add(new Song("Drones", "Muse", "05:45"));
		songs.add(new Song("Extraordinary", "Clean Bandit", "04:48"));
		songs.add(new Song("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		songs.add(new Song("The Celestials", "The Smashing Pumpkins", "03:20"));
		songs.add(new Song("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		songs.add(new Song("Drones", "Muse", "05:45"));
		songs.add(new Song("Extraordinary", "Clean Bandit", "04:48"));
		songs.add(new Song("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		return songs;
	}
}
