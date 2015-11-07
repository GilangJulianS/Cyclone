package com.cyclone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.cyclone.R;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.fragment.PlayerFragment;

/**
 * Created by gilang on 07/11/2015.
 */
public abstract class MasterActivity extends AppCompatActivity implements GestureDetector
		.OnGestureListener{

	public static final int LAYOUT_PROGRAM_PAGE = 101;
	public static final int LAYOUT_PERSON_PROFILE = 102;
	public static final int LAYOUT_PLAYER = 103;
	public static final int LAYOUT_ALBUM = 104;
	public static final int LAYOUT_ARTIST = 105;
	public static final int LAYOUT_HOME = 106;
	public static final int LAYOUT_VIRTUAL_CARD = 107;
	public static final int LAYOUT_CLUB = 108;
	public static final int LAYOUT_NOTIFICATION = 109;
	public static final int LAYOUT_SETTINGS = 110;
	public static final int LAYOUT_LIVE = 111;
	public static final int LAYOUT_PROGRAMS = 112;
	public static final int LAYOUT_ANNOUNCERS = 113;
	public static final int LAYOUT_FEED = 114;
	public static final int LAYOUT_PEOPLE = 115;
	public static final int LAYOUT_ACCOUNT_SETTINGS = 116;

	public AppBarLayout appBarLayout;
	public boolean changing = false;

	protected GestureDetectorCompat gd;
	protected View miniPlayer;
	protected OnOffsetChangedListener callback;
	protected Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void setupToolbar(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	protected void setupMiniPlayer(){
		miniPlayer = findViewById(R.id.minimized_player);
		SharedPreferences pref = getSharedPreferences(getString(R.string.preference_key), Context
				.MODE_PRIVATE);
		if(pref.getInt("state", PlayerFragment.STATE_STOP) == PlayerFragment.STATE_STOP){
			miniPlayer.setVisibility(View.GONE);
		}else{
			miniPlayer.setVisibility(View.VISIBLE);
		}
	}

	protected void setupGestureListener(){
		gd = new GestureDetectorCompat(this, this);
		CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
		layout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("touch");
				if(!changing)
					return gd.onTouchEvent(event);
				else
					return true;
			}
		});
	}

	protected void setupAppbarLayout(){
		appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				float percent = (float)Math.abs(verticalOffset) / (float)appBarLayout
						.getTotalScrollRange()
						* 100;
//				System.out.println(percent);
				if(percent == 100 || percent == 0){
					changing = false;
//					System.out.println("finish");
				}
				if(callback != null) {
					callback.onChanged(percent);
				}
			}
		});
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		System.out.println("fling header: " + velocityX + " " + velocityY);
		if(Math.abs(velocityY) > 50){
			changing = true;
			if(velocityY < 0){
				appBarLayout.setExpanded(false);
				System.out.println("collapsed");
			}else if(velocityY > 0){
				appBarLayout.setExpanded(true);
				System.out.println("expanded");
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


}
