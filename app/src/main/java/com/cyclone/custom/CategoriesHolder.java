package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Section;

/**
 * Created by gilang on 20/11/2015.
 */
public class CategoriesHolder extends UniversalHolder{

	private int counter;
	public ViewGroup group1, group2;

	public CategoriesHolder(View v, Activity activity){
		super(v, activity);
		counter = 0;
		group1 = (ViewGroup) v.findViewById(R.id.group_1);
		group2 = (ViewGroup) v.findViewById(R.id.group_2);
	}


	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Categories)object);
	}

	public void bind(Categories categories){
		group1.removeAllViews();
		group2.removeAllViews();
		for(Category c : categories.list) {
			Button button = (Button) activity.getLayoutInflater().inflate(R.layout.card_single_button,
					group1, false);
			button.setText(c.text);

			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			if(counter % 4 == 0){
				button.setBackgroundResource(R.drawable.btn_normal_green);
			}else if(counter % 4 == 1){
				button.setBackgroundResource(R.drawable.btn_normal_blue);
			}else if(counter % 4 == 2){
				button.setBackgroundResource(R.drawable.btn_normal_yellow);
			}else if(counter % 4 == 3){
				button.setBackgroundResource(R.drawable.btn_normal_orange);
			}
			if(counter % 2 == 0){
				group1.addView(button);
			}else{
				group2.addView(button);
			}
			counter++;
		}
	}
}
