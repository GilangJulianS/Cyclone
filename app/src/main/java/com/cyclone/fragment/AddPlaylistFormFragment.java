package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.R;

/**
 * Created by gilang on 07/12/2015.
 */
public class AddPlaylistFormFragment extends Fragment {

	private Button btnNext;

	public AddPlaylistFormFragment(){}

	public static AddPlaylistFormFragment newInstance(){
		AddPlaylistFormFragment fragment = new AddPlaylistFormFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_add_mix_playlist, parent, false);

		btnNext = (Button) v.findViewById(R.id.btn_next);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), DrawerActivity.class);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("layout", DrawerActivity.LAYOUT_ADD_PLAYLIST);
				i.putExtra("title", "Add Playlist");
				startActivity(i);
			}
		});

		return v;
	}
}
