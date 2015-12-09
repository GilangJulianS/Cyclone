package com.cyclone.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Queue;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends RecyclerFragment {

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private List<Object> persistentDatas;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ViewGroup btnArtist, btnAlbum;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime;
	private SlidingLayer slidingLayer;
	private SeekBar seekbar;


	public PlayerFragment(){}

	public static PlayerFragment newInstance(String json){
		PlayerFragment fragment = new PlayerFragment();
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
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return R.layout.part_header_player;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeaderView(v);
		SharedPreferences pref = activity.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		persistentDatas = parse(json);
		minimizedPlayer = activity.findViewById(R.id.minimized_player);
		minimizedPlayer.setVisibility(View.GONE);
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
			activity.appBarLayout.setExpanded(true);
		}
		return super.onOptionsItemSelected(item);
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
		btnArtist = (ViewGroup) v.findViewById(R.id.btn_artist);
		btnAlbum = (ViewGroup) v.findViewById(R.id.btn_album);
		seekbar = (SeekBar) v.findViewById(R.id.seekbar);

		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		seekbar.setPadding(0, 0, 0, 0);

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

		btnRepeat.setOnClickListener(new View.OnClickListener() {
			private boolean activated = false;
			@Override
			public void onClick(View v) {
				if(!activated) {
					btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnRepeat.setColorFilter(Color.WHITE);
					activated = false;
				}
			}
		});

		btnShuffle.setOnClickListener(new View.OnClickListener() {
			private boolean activated = false;
			@Override
			public void onClick(View v) {
				if(!activated) {
					btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnShuffle.setColorFilter(Color.WHITE);
					activated = false;
				}
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
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
				Queue p = (Queue) persistentDatas.get(counter % persistentDatas.size());
				txtTitle.setText(p.title);
				txtArtist.setText(p.artist);
				txtTotalTime.setText(p.duration);
				counter++;
			}
		});

		btnPrevious.setOnClickListener(new View.OnClickListener() {
			private int counter = 0;
			@Override
			public void onClick(View v) {
				imgTemp.setImageDrawable(imgCover.getDrawable());
				if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
				Queue p = (Queue) persistentDatas.get(counter % persistentDatas.size());
				txtTitle.setText(p.title);
				txtArtist.setText(p.artist);
				txtTotalTime.setText(p.duration);
				counter++;
			}
		});

		btnMinimize = (ImageButton) v.findViewById(R.id.btn_minimize);
		btnMinimize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity != null) {
					activity.appBarLayout.setExpanded(false);
				}
			}
		});
		imgCover.setImageResource(R.drawable.wallpaper);
		setPlayerColor();


		btnArtist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ARTIST);
				i.putExtra("title", "Artist Name");
				activity.startActivity(i);
			}
		});

		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ALBUM);
				i.putExtra("title", "Album Name");
				activity.startActivity(i);
			}
		});
	}

	public void setPlayerColor(){
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
				r += 50;
				g += 50;
				b += 50;
				System.out.println("RGB : " + r + " " + g + " " + b);
				if (r > 255)
					r = 255;
				if (g > 255)
					g = 255;
				if (b > 255)
					b = 255;
				int lightColor = Color.rgb(r, g, b);
				groupInfo.setBackgroundColor(color);
				groupControl.setBackgroundColor(lightColor);
			}
		});
	}

	public List<Object> parse(String json){
		List<Object> playlists = new ArrayList<>();
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Queue("Drones", "Muse", "05:45"));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Queue("Drones", "Muse", "05:45"));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Queue("Drones", "Muse", "05:45"));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Queue("Drones", "Muse", "05:45"));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Queue("Drones", "Muse", "05:45"));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		return playlists;
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
