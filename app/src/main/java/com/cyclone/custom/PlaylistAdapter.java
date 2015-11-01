package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 31/10/2015.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

	public List<Playlist> datas;
	private Context context;
	private  Activity activity;

	public PlaylistAdapter(Activity activity, String json){
		datas = new ArrayList<>();
		this.activity = activity;
		this.context = (Context) activity;
	}

	public void add(Playlist playlist){
		datas.add(playlist);
	}

	public void add(Playlist playlist, int position){
		datas.add(position, playlist);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playlist, parent,
				false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Playlist playlist = datas.get(position);
		holder.txtTitle.setText(playlist.artist + " - " + playlist.title);
		holder.txtArtist.setText(playlist.artist);
		holder.txtDuration.setText(playlist.duration);
	}


	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public TextView txtTitle;
		public TextView txtArtist;
		public TextView txtDuration;

		public ViewHolder(View v) {
			super(v);
			txtTitle = (TextView) v.findViewById(R.id.txt_song_title);
			txtArtist = (TextView) v.findViewById(R.id.txt_song_artist);
			txtDuration = (TextView) v.findViewById(R.id.txt_duration);
		}
	}
}
