package com.cyclone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.ProgramPageFragment;
import com.cyclone.fragment.RadioProfileFragment;

public class CollapseActivity extends AppCompatActivity {

	public static final int LAYOUT_PROGRAM_PAGE = 101;
	public static final int LAYOUT_PERSON_PROFILE = 102;

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
//				onBackPressed();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
