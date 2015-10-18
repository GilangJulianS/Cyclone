package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.model.Person;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

	public List<Person> datas;
	private Activity activity;
	private Context context;
	private int transitionId;
	public static int autoId = 0;

	public PersonAdapter(Activity activity, String json){
		this.activity = activity;
		context = (Context) activity;
		datas = new ArrayList<>();
		transitionId = autoId;
		autoId++;
	}

	public void add(Person p){
		datas.add(p);
	}

	public void add(Person p, int position){
		datas.add(position, p);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_person, parent,
				false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		holder.imgUser.setImageResource(R.drawable.background_login);
		holder.txtName.setText(datas.get(position).name);
		holder.txtUsername.setText(datas.get(position).username);
		final ImageButton button = holder.btnFollow;
		holder.btnFollow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button.setVisibility(View.GONE);
			}
		});
		final ImageView imageView = holder.imgUser;
		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, CollapseActivity.class);
				i.putExtra("layout", CollapseActivity.LAYOUT_PERSON_PROFILE);
				i.putExtra("title", datas.get(position).name);
				i.putExtra("transition", "profile" + transitionId);
				if(Build.VERSION.SDK_INT >= 16) {
					ActivityOptionsCompat options = ActivityOptionsCompat
							.makeSceneTransitionAnimation(activity, imageView, "profile" + transitionId);
					activity.startActivity(i, options.toBundle());
				}else{
					activity.startActivity(i);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView imgUser;
		public TextView txtName;
		public TextView txtUsername;
		public CardView card;
		public ImageButton btnFollow;

		public ViewHolder(View v) {
			super(v);
			imgUser = (ImageView)v.findViewById(R.id.img_user);
			txtName = (TextView) v.findViewById(R.id.txt_name);
			txtUsername = (TextView) v.findViewById(R.id.txt_username);
			btnFollow = (ImageButton) v.findViewById(R.id.btn_follow);
			card = (CardView) v.findViewById(R.id.card_person);
		}
	}
}
