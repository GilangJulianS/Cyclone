package com.cyclone.model;

import android.support.annotation.Nullable;

/**
 * Created by gilang on 21/11/2015.
 */
public class Content {

	public static final int TYPE_FAVORITABLE = 100;
	public static final int TYPE_ELSE = 101;
	public String imgUrl;
	public String txtPrimary;
	public String txtSecondary;
	public String txtTertiary;
	public int targetType;
	public boolean isFavorited;

	public Content(String imgUrl, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		this.imgUrl = imgUrl;
		this.txtPrimary = txtPrimary;
		this.txtSecondary = txtSecondary;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		targetType = TYPE_ELSE;
		isFavorited = false;
	}

	public Content(String imgUrl, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		this.imgUrl = imgUrl;
		this.txtPrimary = txtPrimary;
		this.txtSecondary = txtSecondary;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		isFavorited = false;
	}

	public Content(String imgUrl, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, 			boolean isFavorited){
		this.imgUrl = imgUrl;
		this.txtPrimary = txtPrimary;
		this.txtSecondary = txtSecondary;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.isFavorited = isFavorited;
	}
}
