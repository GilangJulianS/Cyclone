package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 20/11/2015.
 */
public class HomeFragment extends RecyclerFragment {

	public HomeFragment(){}

	public static HomeFragment newInstance(String json){
		HomeFragment fragment = new HomeFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return R.layout.part_header_home;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeader(v, json);
	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void bindHeader(View v, String json){
		Button btnPlay = (Button) v.findViewById(R.id.btn_play);
		Button btnMix = (Button) v.findViewById(R.id.btn_mix);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Play radio pressed", Toast.LENGTH_SHORT).show();
			}
		});

		btnMix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Mix for you pressed", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public List<Object> parse(String json){
		Contents contents;
		Categories categories;
		List<Object> datas = new ArrayList<>();
		List<Category> categoryList;
		List<Content> contentList;

		categoryList = new ArrayList<>();
		categoryList.add(new Category("Radio Content", "Radio Content"));
		categoryList.add(new Category("Music", "Music"));
		categoryList.add(new Category("Showlist", "Showlist"));
		categoryList.add(new Category("Uploaded", "Uploaded"));
		categories = new Categories(categoryList);
		datas.add(categories);

		datas.add(new Section("Latest News", "news", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Latest News", "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contentList.add(new Content("", "Latest News", "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contentList.add(new Content("", "Latest News", "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", "talk", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talk", "Talkshow GOWASDSA", "Prambors FM Jakarta", null));
		contentList.add(new Content("", "Talk", "Hampir 30 film", "Prambors FM Jakarta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("New Release", "release", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "New Release", "Love never feel", "Michael Jackson", null));
		contentList.add(new Content("", "New Release", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "New Release", "Smells like te", "Nirvana", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Recommended Music", "Its My Life", "Bon Jovi", null));
		contentList.add(new Content("", "Recommended Music", "Don't Look Back", "Oasis", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Hits Playlist", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "Hits Playlist", "Rock Yeah", "Imam Darto", null));
		contentList.add(new Content("", "Hits Playlist", "HipHopYo!", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Top mix", "Mix max", "Nycta Gina", null));
		contentList.add(new Content("", "Top mix", "DUbldbldb", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Most Played Upload", "Cover Love", "Dimas Danang", null));
		contentList.add(new Content("", "Most Played Upload", "Cover Demons", "Imam Darto", null));
		contentList.add(new Content("", "Most Played Upload", "Cover Smells Like", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Newly Uploaded", "Cover Its-me", "Nycta Gina", null));
		contentList.add(new Content("", "Newly Uploaded", "Cover Don't Look Back", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
