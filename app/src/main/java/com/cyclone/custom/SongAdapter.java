package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Song;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 31/10/2015.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

	public List<Song> datas;
	private Context context;
	private  Activity activity;

	public SongAdapter(Activity activity, String json){
		datas = new ArrayList<>();
		this.activity = activity;
		this.context = (Context) activity;
	}

	public void add(Song song){
		datas.add(song);
	}

	public void add(Song song, int position){
		datas.add(position, song);
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
		Song song = datas.get(position);
		holder.txtNumber.setText(position);
		holder.txtInfo.setText(song.artist + " - " + song.title);
		holder.txtDuration.setText(song.duration);
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public TextView txtNumber;
		public TextView txtInfo;
		public TextView txtDuration;

		public ViewHolder(View v) {
			super(v);
			txtNumber = (TextView) v.findViewById(R.id.txt_number);
			txtInfo = (TextView) v.findViewById(R.id.txt_song_info);
			txtDuration = (TextView) v.findViewById(R.id.txt_duration);
		}
	}
}
