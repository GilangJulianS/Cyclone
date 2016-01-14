package com.cyclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cyclone.fragment.AboutFragment;
import com.cyclone.fragment.AccountSettingFragment;
import com.cyclone.fragment.AddMixFormFragment;
import com.cyclone.fragment.AddMixFragment;
import com.cyclone.fragment.AddPlaylistFormFragment;
import com.cyclone.fragment.AddPlaylistFragment;
import com.cyclone.fragment.AlbumFragment;
import com.cyclone.fragment.AnnouncersFragment;
import com.cyclone.fragment.AppSettingFragment;
import com.cyclone.fragment.ArtistFragment;
import com.cyclone.fragment.CategoryFragment;
import com.cyclone.fragment.ClubRadioFragment;
import com.cyclone.fragment.CommentFragment;
import com.cyclone.fragment.FavoritesFragment;
import com.cyclone.fragment.GridMixFragment;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.fragment.LiveStreamFragment;
import com.cyclone.fragment.MixFragment;
import com.cyclone.fragment.NotificationFragment;
import com.cyclone.fragment.NotificationSettingFragment;
import com.cyclone.fragment.PersonListFragment;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.PlaylistFragment;
import com.cyclone.fragment.ProgramPageFragment;
import com.cyclone.fragment.ProgramsFragment;
import com.cyclone.fragment.RadioProfileFragment;
import com.cyclone.fragment.RequestFragment;
import com.cyclone.fragment.SettingsFragment;
import com.cyclone.fragment.StreamPlayerFragment;
import com.cyclone.fragment.SubcategoryFragment;
import com.cyclone.fragment.TrackListFragment;
import com.cyclone.fragment.UploadFinishedFragment;
import com.cyclone.fragment.UploadFragment;
import com.cyclone.fragment.VirtualCardFragment;

public class DrawerActivity extends MasterActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private boolean isParentView = false;
	private boolean isCollapseLayout = false;
	private ActionBarDrawerToggle toggle;
	private CollapsingToolbarLayout toolbarLayout;
	private MenuItem activeMenuItem;
	public CoordinatorLayout coordinatorLayout;
	private boolean showMiniPlayer = true;
	private boolean hasExtras;
	private String title;
	private int fragmentType;
	private int mode;
	private int menuId;
	private String transitionId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent caller = getIntent();
		if(caller != null && caller.getExtras() != null) {
			hasExtras = true;
			isParentView = caller.getExtras().getBoolean("parent", false);
			title = caller.getExtras().getString("title", "");
			fragmentType = caller.getExtras().getInt("fragmentType", FRAGMENT_RADIO_PROFILE);
			mode = caller.getExtras().getInt("mode", -1);
			menuId = caller.getExtras().getInt("menuId", 0);
			transitionId = caller.getExtras().getString("transition", "profile");

			int rootLayout = getBaseLayout(fragmentType);

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
		View headerView = getLayoutInflater().inflate(R.layout.nav_header_main, navigationView, false);
		ImageView radioLogo = (ImageView) headerView.findViewById(R.id.img_drawer_logo);
		radioLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(activeMenuItem != null)
					activeMenuItem.setChecked(false);
				Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_RADIO_PROFILE);
				i.putExtra("parent", true);
				i.putExtra("title", "Prambors FM Jakarta");
				startActivity(i);
				finish();
			}
		});
		navigationView.addHeaderView(headerView);
		navigationView.setNavigationItemSelectedListener(this);

		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

		if(isCollapseLayout) {
			toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
					.collapsing_toolbar_layout);
			toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
			toolbarLayout.setTitle("Prambors FM Jakarta");
		}

		if(hasExtras) {
			FragmentManager manager = getSupportFragmentManager();
			if(title != null && !title.equals("")) {
				if(toolbarLayout != null)
					toolbarLayout.setTitle(title);
				else
					getSupportActionBar().setTitle(title);
			}
			if(fragmentType == FRAGMENT_RADIO_PROFILE){
				manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
			}else if(fragmentType == FRAGMENT_HOME){
				manager.beginTransaction().replace(R.id.container, HomeFragment.newInstance("")).commit();
			}else if(fragmentType == FRAGMENT_VIRTUAL_CARD){
				manager.beginTransaction().replace(R.id.container, VirtualCardFragment.newInstance()).commit();
			}else if(fragmentType == FRAGMENT_CLUB){
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
			}else if(fragmentType == FRAGMENT_NOTIFICATION){
				manager.beginTransaction().replace(R.id.container, NotificationFragment.newInstance("")).commit();
			}else if(fragmentType == FRAGMENT_SETTINGS){
				manager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit();
			}else if(fragmentType == FRAGMENT_LIVE){
				manager.beginTransaction().replace(R.id.container, LiveStreamFragment.newInstance("")).commit();
			}else if (fragmentType == FRAGMENT_PROGRAM_PAGE) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramPageFragment
						.newInstance()).commit();
			} else if (fragmentType == FRAGMENT_PERSON_PROFILE) {
				PersonProfileFragment fragment = PersonProfileFragment.newInstance(mode,
						transitionId, "");
				callback = fragment;
				manager.beginTransaction().replace(R.id.container, fragment).commit();
			} else if (fragmentType == FRAGMENT_PLAYER) {
				PlayerFragment fragment = PlayerFragment.newInstance("");
				callback = fragment;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, fragment)
						.commit();
			} else if (fragmentType == FRAGMENT_ALBUM) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, AlbumFragment.newInstance(""))
						.commit();
			} else if (fragmentType == FRAGMENT_ARTIST) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ArtistFragment.newInstance(""))
						.commit();
			} else if (fragmentType == FRAGMENT_PROGRAMS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramsFragment.newInstance("")).commit();
			}else if (fragmentType == FRAGMENT_ANNOUNCERS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AnnouncersFragment.newInstance("")).commit();
			}else if (fragmentType == FRAGMENT_FEED){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
			}else if (fragmentType == FRAGMENT_PEOPLE){
				callback = null;
				manager.beginTransaction().replace(R.id.container, PersonListFragment.newInstance("")).commit();
			}else if (fragmentType == FRAGMENT_ACCOUNT_SETTINGS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AccountSettingFragment.newInstance()).commit();
			}else if(fragmentType == FRAGMENT_STREAM_PLAYER){
				callback = null;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, StreamPlayerFragment
						.newInstance()).commit();
			}else if(fragmentType == FRAGMENT_REQUEST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, RequestFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_CATEGORY){
				callback = null;
				manager.beginTransaction().replace(R.id.container, CategoryFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_SUBCATEGORY){
				callback = null;
				manager.beginTransaction().replace(R.id.container, SubcategoryFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_ADD_MIX){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AddMixFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_ADD_PLAYLIST){
				callback = null;

				manager.beginTransaction().replace(R.id.container, AddPlaylistFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_ADD_MIX_FORM){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AddMixFormFragment.newInstance())
						.commit();
			}else if(fragmentType == FRAGMENT_ADD_PLAYLIST_FORM){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AddPlaylistFormFragment.newInstance())
						.commit();
			}else if(fragmentType == FRAGMENT_GRID_MIX){
				callback = null;
				manager.beginTransaction().replace(R.id.container, GridMixFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_COMMENT){
				callback = null;
				manager.beginTransaction().replace(R.id.container, CommentFragment.newInstance("", caller.getExtras().getInt("mode")))
						.commit();
			}else if(fragmentType == FRAGMENT_MIX){
				callback = null;
				manager.beginTransaction().replace(R.id.container, MixFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_FAVORITES){
				callback = null;
				manager.beginTransaction().replace(R.id.container, FavoritesFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_APP_SETTINGS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AppSettingFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_NOTIFICATION_SETTINGS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, NotificationSettingFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_ABOUT){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AboutFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_TRACK_LIST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, TrackListFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_PLAYLIST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, PlaylistFragment.newInstance(""))
						.commit();
			}else if(fragmentType == FRAGMENT_UPLOAD){
				callback = null;
				manager.beginTransaction().replace(R.id.container, UploadFragment.newInstance())
						.commit();
			}else if(fragmentType == FRAGMENT_UPLOAD_FINISHED){
				callback = null;
				manager.beginTransaction().replace(R.id.container, UploadFinishedFragment.newInstance())
						.commit();
			}
			navigationView.getMenu().getItem(menuId).setChecked(true);
		}else{
			isParentView = true;
			FragmentManager manager = getSupportFragmentManager();
			manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
			navigationView.getMenu().getItem(0).setChecked(true);
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
				break;
			case R.id.action_settings:
				Intent intent = new Intent(this, DrawerActivity.class);
				intent.putExtra("title", "Settings");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_SETTINGS);
				intent.putExtra("menuId", 7);
				startActivity(intent);
				break;
		}

		return super.onOptionsItemSelected(item);
	}


	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		if(activeMenuItem != null)
			activeMenuItem.setChecked(false);
		item.setChecked(true);
		activeMenuItem = item;

		int id = item.getItemId();
		Intent intent = new Intent(this, DrawerActivity.class);
		intent.putExtra("parent", true);
		switch (id){
			case R.id.nav_home:
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_HOME);
				intent.putExtra("menuId", 0);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_live:
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_LIVE);
				intent.putExtra("title", "Live Stream");
				intent.putExtra("menuId", 1);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_klub:
				intent.putExtra("title", "Klub Radio");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_CLUB);
				intent.putExtra("menuId", 2);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_profile:
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_PERSON_PROFILE);
				intent.putExtra("title", "Dimas Danang");
				intent.putExtra("menuId", 3);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_favorite:
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_FAVORITES);
				intent.putExtra("title", "Favorites");
				intent.putExtra("menuId", 4);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_notification:
				intent.putExtra("title", "Notifications");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_NOTIFICATION);
				intent.putExtra("menuId", 5);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_virtual_card:
				intent.putExtra("title", "Virtual Card");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_VIRTUAL_CARD);
				intent.putExtra("menuId", 6);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_setting:
				intent.putExtra("title", "Settings");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_SETTINGS);
				intent.putExtra("menuId", 7);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_player:
				intent.putExtra("title", "Player");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_PLAYER);
				intent.putExtra("menuId", 8);
				startActivity(intent);
				finish();
				break;
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public int getBaseLayout(int fragmentType){
		switch (fragmentType){
			case MasterActivity.FRAGMENT_PROGRAM_PAGE : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_PERSON_PROFILE : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_PLAYER : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_ALBUM : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_ARTIST : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_RADIO_PROFILE : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_VIRTUAL_CARD : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_CLUB : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_NOTIFICATION : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_SETTINGS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_LIVE : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_PROGRAMS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ANNOUNCERS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_FEED : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_PEOPLE : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ACCOUNT_SETTINGS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_STREAM_PLAYER : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_REQUEST : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_HOME : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_CATEGORY : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_SUBCATEGORY : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_GRID_MIX : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ADD_MIX : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_COMMENT : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_MIX : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_FAVORITES : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_APP_SETTINGS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_NOTIFICATION_SETTINGS : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ABOUT : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ADD_PLAYLIST : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ADD_MIX_FORM : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_ADD_PLAYLIST_FORM : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_TRACK_LIST : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_PLAYLIST : return R.layout.activity_drawer;
			case MasterActivity.FRAGMENT_UPLOAD : return R.layout.activity_drawer_standard;
			case MasterActivity.FRAGMENT_UPLOAD_FINISHED : return R.layout.activity_drawer_standard;
			default : return R.layout.activity_drawer_standard;
		}
	}
}
