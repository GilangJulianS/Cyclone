package com.cyclone.custom;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Mix;

/**
 * Created by gilang on 23/11/2015.
 */
public class MixHolder extends UniversalHolder {

	public ImageView imgCover;
	public TextView txtTitle;
	public ImageView imgHeart;

	public MixHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		imgHeart = (ImageView) v.findViewById(R.id.img_heart);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Mix) object);
	}

	public void bind(final Mix mix){
		imgCover.setImageResource(R.drawable.wallpaper);
		imgCover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgHeart.setVisibility(View.VISIBLE);
				int oldPosition = adapter.datas.indexOf(mix);
				adapter.datas.remove(mix);
				adapter.add(mix, getLastLikedEntry());
				adapter.notifyItemMoved(oldPosition, getLastLikedEntry());
				mix.isFavorite = true;
			}
		});
		txtTitle.setText(mix.text);
		if(mix.isFavorite){
			imgHeart.setVisibility(View.VISIBLE);
		}else{
			imgHeart.setVisibility(View.GONE);
		}
	}

	public int getLastLikedEntry(){
		int idx = 0;
		for(int i=0; i<adapter.datas.size(); i++){
			if(((Mix)adapter.datas.get(i)).isFavorite)
				idx = i;
		}
		return idx;
	}
}
