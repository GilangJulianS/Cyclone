package com.cyclone.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.custom.SongAdapter;
import com.cyclone.model.Song;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends Fragment implements GestureDetector.OnGestureListener{

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private RecyclerView recyclerView;
	private List<Song> datas, persistentDatas;
	private SongAdapter adapter;
	private LinearLayoutManager layoutManager;
	private CollapseActivity activity;
	private GestureDetectorCompat gd;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime;
	private SlidingLayer slidingLayer;

	public PlayerFragment(){}

	public static PlayerFragment newInstance(){
		PlayerFragment fragment = new PlayerFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		setHasOptionsMenu(true);

		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		bindView(v);

		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);


		adapter = new SongAdapter(getActivity(), "");

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

	public void bindView(View v){
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.player, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.btn_collapse){
			activity.changing = true;
			activity.appBarLayout.setExpanded(true);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);

		SharedPreferences pref = context.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		datas = parse("");
		persistentDatas = new ArrayList<>();
		persistentDatas.addAll(datas);

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
			minimizedPlayer = activity.findViewById(R.id.minimized_player);
			minimizedPlayer.setVisibility(View.GONE);
			parallaxHeader.addView(header);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		minimizedPlayer.setVisibility(View.VISIBLE);
	}

	public void bindHeaderView(View v){
		btnRepeat = (ImageButton) v.findViewById(R.id.btn_repeat);
		btnPrevious = (ImageButton) v.findViewById(R.id.btn_previous);
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
		btnNext = (ImageButton) v.findViewById(R.id.btn_next);
		btnShuffle = (ImageButton) v.findViewById(R.id.btn_shuffle);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		txtArtist = (TextView) v.findViewById(R.id.txt_artist);
		txtTotalTime = (TextView) v.findViewById(R.id.txt_total_time);
		groupInfo = (ViewGroup) v.findViewById(R.id.group_player_info);
		groupControl = (ViewGroup) v.findViewById(R.id.group_player_control);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		imgTemp = (ImageView) v.findViewById(R.id.img_temp);
		slidingLayer = (SlidingLayer) v.findViewById(R.id.sliding_layer);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);

		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state == STATE_STOP) {
					state = STATE_PLAYING;
					btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
				} else {
					state = STATE_STOP;
					btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
				}
				SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string
						.preference_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt("state", state);
				editor.commit();
			}
		});


		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingLayer.openLayer(true);
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {

			private int counter = 0;
			@Override
			public void onClick(View v) {
				imgTemp.setImageDrawable(imgCover.getDrawable());
				if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}
				if(Build.VERSION.SDK_INT >= 21)
					showImage(imgCover);
				txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
				txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
				txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);
				counter++;
			}
		});

		btnMinimize = (ImageButton) v.findViewById(R.id.btn_minimize);
		btnMinimize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity != null) {
					activity.changing = true;
					activity.appBarLayout.setExpanded(false);
				}
			}
		});
		imgCover.setImageResource(R.drawable.wallpaper);
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
		if(Math.abs(velocityY) > 50){
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

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void showImage(final View v){

		// get the center for the clipping circle
		int cx = v.getWidth() / 2;
		int cy = v.getHeight() / 2;

		// get the final radius for the clipping circle
		int finalRadius = Math.max(v.getWidth(), v.getHeight());

		// create the animator for this view (the start radius is zero)
		Animator anim =
				ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);

		// make the view visible and start the animation
		v.setVisibility(View.VISIBLE);
		anim.start();
	}
}