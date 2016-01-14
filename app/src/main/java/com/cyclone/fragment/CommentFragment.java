package com.cyclone.fragment;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.cyclone.R;
import com.cyclone.model.Comment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentFragment extends RecyclerFragment {

	private EditText formComment;
	private ImageButton btnSubmit;

	public CommentFragment(){}

	public static CommentFragment newInstance(String json){
		CommentFragment fragment = new CommentFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View commentView = inflater.inflate(R.layout.part_comment, parent, false);
		((ViewGroup) v).addView(commentView);

		formComment = (EditText) commentView.findViewById(R.id.form_comment);
		btnSubmit = (ImageButton) commentView.findViewById(R.id.btn_submit);

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
				adapter.add(new Comment("", "Dimas Danang", formComment.getText().toString(), time));
				adapter.notifyItemInserted(adapter.getItemCount() - 1);
				recyclerView.scrollToPosition(adapter.getItemCount() - 1);
				formComment.setText("");
			}
		});
	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		return datas;
	}
}
