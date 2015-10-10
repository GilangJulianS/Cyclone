package com.cyclone.custom;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

	private List<Program> datas;

	public ProgramAdapter(String json){
		datas = parseData(json);
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
		holder.imgCover.setImageResource(R.drawable.background_login);
		holder.txtTitle.setText(p.title);
		holder.txtSchedule.setText(p.schedule);
		holder.ratingBar.setRating(p.rating);
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

		public ViewHolder(View v){
			super(v);
			imgCover = (ImageView) v.findViewById(R.id.img_cover);
			txtTitle = (TextView) v.findViewById(R.id.txt_title);
			txtSchedule = (TextView) v.findViewById(R.id.txt_schedule);
			ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
		}

	}
}
