package com.cyclone.custom;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Data;

/**
 * Created by gilang on 21/11/2015.
 */
public class ContentsHolder extends UniversalHolder {

	public ViewGroup container;

	public ContentsHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		container = (ViewGroup) v.findViewById(R.id.content_container);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Contents)object);
	}

	public void bind(Contents contents){
		container.removeAllViews();
		for(Content c : contents.list){
			View v = activity.getLayoutInflater().inflate(R.layout.card_thumbnail, container, false);
			final ImageView imgCover = (ImageView) v.findViewById(R.id.img_cover);
			final ImageView imgHeart = (ImageView) v.findViewById(R.id.img_heart);
			TextView txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
			TextView txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
			TextView txtTertiary = (TextView) v.findViewById(R.id.txt_tertiary);

			final Content temp = c;
			imgCover.setImageResource(R.drawable.wallpaper);
			imgCover.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(temp.targetType == Content.TYPE_FAVORITABLE){
						temp.isFavorited = !temp.isFavorited;
						if(temp.isFavorited) {
							imgHeart.setVisibility(View.VISIBLE);
							Data.add(temp);
							createSnackBar().show();
						}
						else {
							imgHeart.setVisibility(View.GONE);
							Data.remove(temp);
							createSnackBar().show();
						}
					}
				}
			});
			if(temp.isFavorited)
				imgHeart.setVisibility(View.VISIBLE);

			if(c.txtPrimary != null)
				txtPrimary.setText(c.txtPrimary);
			else
				txtPrimary.setVisibility(View.GONE);
			if(c.txtSecondary != null)
				txtSecondary.setText(c.txtSecondary);
			else
				txtSecondary.setVisibility(View.GONE);
			if(c.txtTertiary != null)
				txtTertiary.setText(c.txtTertiary);
			else
				txtTertiary.setVisibility(View.GONE);

			container.addView(v);
		}
	}

	public Snackbar createSnackBar(){
		Snackbar snackbar =
				Snackbar.make(((DrawerActivity) activity).coordinatorLayout, Data.getData().size()
						+ " items added", Snackbar.LENGTH_INDEFINITE)
						.setAction("Done", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								activity.onBackPressed();
							}
						});
		View snack = snackbar.getView();
		TextView txtSnack = (TextView) snack.findViewById(android.support.design.R.id.snackbar_text);
		txtSnack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_SUBCATEGORY);
				i.putExtra("title", "Track List");
				activity.startActivity(i);
			}
		});
		return snackbar;
	}
}
