package com.cyclone.custom;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ViewHolder> {

	public List<Program> datas;
	private Context context;
	private Activity activity;

	public ProgramsAdapter(Activity activity, String json){
		datas = new ArrayList<>();
//		datas = parseData(json);
		this.activity = activity;
		context = (Context)activity;
	}

	public void add(Program program, int position){
		datas.add(position, program);
	}

	public void add(Program program){
		datas.add(program);
	}

	public List<Program> parseData(String json){
		List<Program> programs = new ArrayList<>();
//		-------- dummy -------
		programs.add(new Program("http://imgurl.com", "Inspiring Life", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Inspiring Morning", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Easy Busy", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Hit The Beat", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Inspiring Night", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Afternoon Cafe", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Soft Sensation", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Headline News", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Life Sports", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		programs.add(new Program("http://imgurl.com", "Wild Life", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		return programs;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_program, parent,
				false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Program p = datas.get(position);
		final ImageView imageView = holder.imgCover;
		holder.imgCover.setImageResource(R.drawable.background_login);
		holder.txtTitle.setText(p.title);
		holder.txtSchedule.setText(p.schedule);
		holder.ratingBar.setRating(p.rating);
		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, CollapseActivity.class);
				if(Build.VERSION.SDK_INT >= 16) {
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
							(activity, imageView, "program");
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

		public ImageView imgCover;
		public TextView txtTitle;
		public TextView txtSchedule;
		public RatingBar ratingBar;
		public ViewGroup card;

		public ViewHolder(View v){
			super(v);
			imgCover = (ImageView) v.findViewById(R.id.img_cover);
			txtTitle = (TextView) v.findViewById(R.id.txt_title);
			txtSchedule = (TextView) v.findViewById(R.id.txt_schedule);
			ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
			card = (ViewGroup) v.findViewById(R.id.card_program);
		}

	}
}
