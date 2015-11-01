package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Post;

/**
 * Created by gilang on 17/10/2015.
 */
public class ClubRadioFragment extends Fragment {

	private RecyclerView recyclerView;

	public ClubRadioFragment(){}

	public static ClubRadioFragment newInstance(){
		ClubRadioFragment fragment = new ClubRadioFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		UniversalAdapter adapter = new UniversalAdapter(getActivity(), "");
		adapter.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		adapter.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		adapter.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		recyclerView.setAdapter(adapter);

		return v;
	}
}
