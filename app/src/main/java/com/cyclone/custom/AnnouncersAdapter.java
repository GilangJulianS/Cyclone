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

	public List<Announcer> datas;
	private Context context;

	public AnnouncersAdapter(Context context, String json){
		datas = new ArrayList<>();
	}

	public void add(Announcer a){
		datas.add(a);
	}

	public void add(Announcer a, int position){
		datas.add(position, a);
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
