package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 25/10/2015.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

	public List<Notification> datas;
	private Activity activity;
	private Context context;

	public NotificationAdapter(Activity activity, String json){
		datas = new ArrayList<>();
		this.activity = activity;
		this.context = (Context)activity;
	}

	public void add(Notification notification){
		datas.add(notification);
	}

	public void add(Notification notification, int position){
		datas.add(position, notification);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.imgUser.setImageResource(R.drawable.background_login);
		holder.txtInfo.setText(datas.get(position).info);
		holder.txtTime.setText(datas.get(position).time);
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView imgUser;
		public TextView txtInfo;
		public TextView txtTime;

		public ViewHolder(View v) {
			super(v);
			imgUser = (ImageView) v.findViewById(R.id.img_user);
			txtInfo = (TextView) v.findViewById(R.id.txt_info);
			txtTime = (TextView) v.findViewById(R.id.txt_time);
		}
	}
}
