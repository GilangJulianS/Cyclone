package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Category;

/**
 * Created by gilang on 20/11/2015.
 */
public class CategoryHolder extends UniversalHolder {

	public Button button;

	public CategoryHolder(View v, Activity activity) {
		super(v, activity);
		button = (Button) v.findViewById(R.id.button);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Category)object);
	}

	public void bind(Category c){
		button.setText(c.text);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
