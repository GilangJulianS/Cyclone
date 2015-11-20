package com.cyclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cyclone.fragment.AccountSettingFragment;
import com.cyclone.fragment.AlbumFragment;
import com.cyclone.fragment.AnnouncersFragment;
import com.cyclone.fragment.ArtistFragment;
import com.cyclone.fragment.ClubRadioFragment;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.fragment.LiveStreamFragment;
import com.cyclone.fragment.NotificationFragment;
import com.cyclone.fragment.PersonListFragment;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.ProgramPageFragment;
import com.cyclone.fragment.ProgramsFragment;
import com.cyclone.fragment.RadioProfileFragment;
import com.cyclone.fragment.RequestFragment;
import com.cyclone.fragment.SettingsFragment;
import com.cyclone.fragment.StreamPlayerFragment;
import com.cyclone.fragment.VirtualCardFragment;

public class DrawerActivity extends MasterActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private boolean isParentView = false;
	private boolean isCollapseLayout = false;
	private ActionBarDrawerToggle toggle;
	CollapsingToolbarLayout toolbarLayout;
	private boolean showMiniPlayer = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent caller = getIntent();
		if(caller != null && caller.getExtras() != null) {
			int rootLayout = caller.getExtras().getInt("activity", R.layout.activity_drawer);
			setContentView(rootLayout);
			if(rootLayout == R.layout.activity_drawer)
				isCollapseLayout = true;
		}
		else {
			setContentView(R.layout.activity_drawer);
			isCollapseLayout = true;
		}

		setupToolbar();
		setupMiniPlayer();
		setupAppbarLayout();
		setupGestureListener();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		if(isCollapseLayout) {
			toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
					.collapsing_toolbar_layout);
			toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
			toolbarLayout.setTitle("K-Lite FM Bandung");
		}

		if(caller != null && caller.getExtras() != null) {
			isParentView = caller.getExtras().getBoolean("parent", false);
			String title = caller.getExtras().getString("title", "");
			int layout = caller.getExtras().getInt("layout", LAYOUT_RADIO_PROFILE);
			int mode = caller.getExtras().getInt("mode", -1);
			String transitionId = caller.getExtras().getString("transition", "profile");
			FragmentManager manager = getSupportFragmentManager();
			if(title != null && !title.equals("")) {
				if(toolbarLayout != null)
					toolbarLayout.setTitle(title);
				else
					getSupportActionBar().setTitle(title);
			}
			if(layout == LAYOUT_RADIO_PROFILE){
				manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
			}else if(layout == LAYOUT_HOME){
				manager.beginTransaction().replace(R.id.container, HomeFragment.newInstance("")).commit();
			}else if(layout == LAYOUT_VIRTUAL_CARD){
				manager.beginTransaction().replace(R.id.container, VirtualCardFragment.newInstance()).commit();
			}else if(layout == LAYOUT_CLUB){
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
			}else if(layout == LAYOUT_NOTIFICATION){
				manager.beginTransaction().replace(R.id.container, NotificationFragment.newInstance("")).commit();
			}else if(layout == LAYOUT_SETTINGS){
				manager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit();
			}else if(layout == LAYOUT_LIVE){
				manager.beginTransaction().replace(R.id.container, LiveStreamFragment.newInstance("")).commit();
			}else if (layout == LAYOUT_PROGRAM_PAGE) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramPageFragment
						.newInstance()).commit();
			} else if (layout == LAYOUT_PERSON_PROFILE) {
				PersonProfileFragment fragment = PersonProfileFragment.newInstance(mode,
						transitionId, "");
				callback = fragment;
				manager.beginTransaction().replace(R.id.container, fragment).commit();
			} else if (layout == LAYOUT_PLAYER) {
				callback = null;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, PlayerFragment.newInstance(""))
						.commit();
			} else if (layout == LAYOUT_ALBUM) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, AlbumFragment.newInstance(""))
						.commit();
			} else if (layout == LAYOUT_ARTIST) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ArtistFragment.newInstance(""))
						.commit();
			} else if (layout == LAYOUT_PROGRAMS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramsFragment.newInstance("")).commit();
			}else if (layout == LAYOUT_ANNOUNCERS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AnnouncersFragment.newInstance("")).commit();
			}else if (layout == LAYOUT_FEED){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
			}else if (layout == LAYOUT_PEOPLE){
				callback = null;
				manager.beginTransaction().replace(R.id.container, PersonListFragment.newInstance("")).commit();
			}else if (layout == LAYOUT_ACCOUNT_SETTINGS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AccountSettingFragment.newInstance()).commit();
			}else if(layout == LAYOUT_STREAM_PLAYER){
				callback = null;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, StreamPlayerFragment
						.newInstance()).commit();
			}else if(layout == LAYOUT_REQUEST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, RequestFragment.newInstance(""))
						.commit();
			}
		}else{
			isParentView = true;
			FragmentManager manager = getSupportFragmentManager();
			manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
		}

		if(isParentView){
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			toggle = new ActionBarDrawerToggle(
					this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			toggle.syncState();
		}else{
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			toggle = new ActionBarDrawerToggle(
					this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences pref = getSharedPreferences(getString(R.string.preference_key), Context
				.MODE_PRIVATE);
		if(!showMiniPlayer && pref.getInt("state", PlayerFragment.STATE_STOP) == PlayerFragment
				.STATE_STOP){
			miniPlayer.setVisibility(View.GONE);
		}else if(showMiniPlayer){
			miniPlayer.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if(isParentView){
			super.onBackPressed();
		}else{
			supportFinishAfterTransition();
		}
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
				if(!isParentView) {
					onBackPressed();
					return true;
				}
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		Intent intent = new Intent(this, DrawerActivity.class);
		intent.putExtra("parent", true);
		switch (id){
			case R.id.nav_home:
				intent.putExtra("layout", MasterActivity.LAYOUT_HOME);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_live:
				intent.putExtra("layout", MasterActivity.LAYOUT_LIVE);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_klub:
				intent.putExtra("title", "Imam Darto");
				intent.putExtra("layout", MasterActivity.LAYOUT_CLUB);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_profile:
				intent.putExtra("layout", MasterActivity.LAYOUT_PERSON_PROFILE);
				intent.putExtra("title", "Dimas Danang");
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				break;
			case R.id.nav_notification:
				intent.putExtra("title", "Notifications");
				intent.putExtra("layout", MasterActivity.LAYOUT_NOTIFICATION);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_virtual_card:
				intent.putExtra("title", "Virtual Card");
				intent.putExtra("layout", MasterActivity.LAYOUT_VIRTUAL_CARD);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				break;
			case R.id.nav_setting:
				intent.putExtra("title", "Settings");
				intent.putExtra("layout", MasterActivity.LAYOUT_SETTINGS);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_player:
				intent.putExtra("title", "Player");
				intent.putExtra("layout", MasterActivity.LAYOUT_PLAYER);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				break;
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
