package com.cyclone.model;

/**
 * Created by gilang on 01/11/2015.
 */
public class Album {

	public String imgUrl;
	public String primary;
	public String secondary;

	public Album(String imgUrl, String primaryInfo, String secondaryInfo){
		this.imgUrl = imgUrl;
		primary = primaryInfo;
		secondary = secondaryInfo;
	}
}
