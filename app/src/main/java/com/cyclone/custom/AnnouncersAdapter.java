package com.cyclone.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Announcer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 11/10/2015.
 */
public class AnnouncersAdapter extends RecyclerView.Adapter<AnnouncersAdapter.ViewHolder> {

	private List<Announcer> datas;
	private Context context;

	public AnnouncersAdapter(Context context, String json){
		datas = parseData(json);
	}

	public List<Announcer> parseData(String json){
		List<Announcer> announcers = new ArrayList<>();


//		--------------- dummy ------------
		announcers.add(new Announcer("http://imgurl.com", "Adiel"));
		announcers.add(new Announcer("http://imgurl.com", "Arien"));
		announcers.add(new Announcer("http://imgurl.com", "Arie W"));
		announcers.add(new Announcer("http://imgurl.com", "Chandra"));
		announcers.add(new Announcer("http://imgurl.com", "Erdina"));
		announcers.add(new Announcer("http://imgurl.com", "Hasanah"));
		announcers.add(new Announcer("http://imgurl.com", "Indira"));
		announcers.add(new Announcer("http://imgurl.com", "Indra"));
		announcers.add(new Announcer("http://imgurl.com", "Ivan"));
		announcers.add(new Announcer("http://imgurl.com", "Kujang"));
		announcers.add(new Announcer("http://imgurl.com", "Rini"));
		announcers.add(new Announcer("http://imgurl.com", "Reno"));
		announcers.add(new Announcer("http://imgurl.com", "Sam"));
		announcers.add(new Announcer("http://imgurl.com", "Tira"));

		return announcers;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_announcer,
				parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.image.setImageResource(R.drawable.background_login);
		holder.txtName.setText(datas.get(position).name);
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public TextView txtName;
		public ImageView image;

		public ViewHolder(View v){
			super(v);
			txtName = (TextView) v.findViewById(R.id.txt_announcer_name);
			image = (ImageView) v.findViewById(R.id.img_announcer);
		}

	}
}
