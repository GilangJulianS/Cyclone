package com.cyclone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.ProgramPageFragment;
import com.cyclone.fragment.RadioProfileFragment;

public class CollapseActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

	public static final int LAYOUT_PROGRAM_PAGE = 101;
	public static final int LAYOUT_PERSON_PROFILE = 102;
	public static final int LAYOUT_PLAYER = 103;

	private GestureDetectorCompat gd;
	public AppBarLayout appBarLayout;
	public boolean isExpanded = true;
	public boolean changing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collapse);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
				.collapsing_toolbar_layout);
		toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
		toolbarLayout.setTitle("K-Lite FM Bandung");

		FragmentManager manager = getSupportFragmentManager();
		Intent caller = getIntent();
		if(caller != null) {
			int layout = caller.getExtras().getInt("layout", -1);
			int mode = caller.getExtras().getInt("mode", -1);
			String transitionId = caller.getExtras().getString("transition", "profile");
			String title = caller.getExtras().getString("title", "");
			if (!title.equals("")) {
				getSupportActionBar().setTitle(title);
			}
			if (layout == LAYOUT_PROGRAM_PAGE) {
				manager.beginTransaction().replace(R.id.container, ProgramPageFragment.newInstance()).commit();
			} else if (layout == LAYOUT_PERSON_PROFILE) {
				manager.beginTransaction().replace(R.id.container, PersonProfileFragment.newInstance
						(mode, transitionId)).commit();
			} else if(layout == LAYOUT_PLAYER){
				manager.beginTransaction().replace(R.id.container, PlayerFragment.newInstance())
						.commit();
			}
		}
//		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//		fab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
//			}
//		});
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
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id){
			case android.R.id.home:
				supportFinishAfterTransition();
//				onBackPressed();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		System.out.println("fling header: " + velocityX + " " + velocityY);
		if(Math.abs(velocityY) > 200){
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
