package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.RunningProgram;

/**
 * Created by gilang on 07/11/2015.
 */
public class RunningProgramHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnPlay;

	public RunningProgramHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((RunningProgram)object, activity);
	}

	public void bind(RunningProgram program, final Activity activity){
		txtPrimary.setText(program.name);
		txtSecondary.setText(program.description);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", "Live Stream");
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_STREAM_PLAYER);
				activity.startActivity(i);
			}
		});
	}
}
