package com.cyclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cyclone.fragment.AnnouncersFragment;
import com.cyclone.fragment.ClubRadioFragment;
import com.cyclone.fragment.PersonListFragment;
import com.cyclone.fragment.ProgramsFragment;

public class StandardActivity extends AppCompatActivity {

	public static final int LAYOUT_PROGRAMS = 101;
	public static final int LAYOUT_ANNOUNCERS = 102;
	public static final int LAYOUT_FEED = 103;
	public static final int LAYOUT_PEOPLE = 104;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent caller =  getIntent();
		if(caller != null) {
			int mode = caller.getExtras().getInt("layout", 0);
			switch (mode) {
				case LAYOUT_PROGRAMS:
					FragmentManager manager = getSupportFragmentManager();
					manager.beginTransaction().replace(R.id.container, ProgramsFragment.newInstance()).commit();
					break;
				case LAYOUT_ANNOUNCERS:
					getSupportFragmentManager().beginTransaction().replace(R.id.container,
							AnnouncersFragment.newInstance()).commit();
					break;
				case LAYOUT_FEED:
					getSupportFragmentManager().beginTransaction().replace(R.id.container,
							ClubRadioFragment.newInstance()).commit();
					break;
				case LAYOUT_PEOPLE:
					getSupportFragmentManager().beginTransaction().replace(R.id.container,
							PersonListFragment.newInstance()).commit();
					break;
			}
			String title = caller.getExtras().getString("title", "");
			if(!title.equals("")){
				getSupportActionBar().setTitle(title);
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
				super.onBackPressed();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
