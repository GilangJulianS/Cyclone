package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 05/12/2015.
 */
public class FavoritesFragment extends RecyclerFragment {

	public FavoritesFragment(){}

	public static FavoritesFragment newInstance(String json){
		FavoritesFragment fragment = new FavoritesFragment();
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
		List<Content> contentList;
		Contents contents;

		contentList = new ArrayList<>();
		datas.add(new Section("Playlist", "palylist"));
		contentList.add(new Content("", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "My Playlist 1", "Imam Darto", null));
		contentList.add(new Content("", "My Playlist 2", "Siapa", null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Mix", "mix"));
		contentList.add(new Content("", "Mix max", "Nycta Gina", null));
		contentList.add(new Content("", "Dubidududu", "Julia", null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Tracks", "tracks"));
		contentList.add(new Content("", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Demons", "Imagine Dragons", null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Artist", "artist"));
		contentList.add(new Content("", "Sheila on 7", null, null));
		contentList.add(new Content("", "Oasis", null, null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Album", "album"));
		contentList.add(new Content("", "Xscape", "Michael Jackson", null));
		contentList.add(new Content("", "Night Vision", "Imagine Dragons", null));
		contentList.add(new Content("", "The Very Best", "Nirvana", null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Uploaded", "uploaded"));
		contentList.add(new Content("", "It's me - cover", "Nycta Gina", null));
		contentList.add(new Content("", "Don't remember - cover", "Julia", null));
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Radio Content", "radio content"));
		contentList.add(new Content("", "Di atas sesuatu", "Apalah", null));
		contentList.add(new Content("", "Hampir 30 film", "Hampir sebanyak 30 film terbaru ditayangkan dalam acara ini", null));
		contentList.add(new Content("", "Melawan Asap", "Dalam perjuangannya melawan asap yang semakin memburuk", null));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
