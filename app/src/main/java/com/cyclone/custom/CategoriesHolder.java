package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;

/**
 * Created by gilang on 20/11/2015.
 */
public class CategoriesHolder extends UniversalHolder{

	public RecyclerView recyclerView;
	public UniversalAdapter adapter;

	public CategoriesHolder(View v, Activity activity){
		super(v, activity);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Categories)object);
	}

	public void bind(Categories categories){
		adapter = new UniversalAdapter(activity);
		for(Category c : categories.list) {
			adapter.add(c);
			System.out.println("add");
		}
		recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
		recyclerView.setAdapter(adapter);
	}
}
