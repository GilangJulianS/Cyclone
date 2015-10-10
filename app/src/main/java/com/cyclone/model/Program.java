package com.cyclone.model;

/**
 * Created by gilang on 10/10/2015.
 */
public class Program {

	public String imgUrl;
	public String title;
	public String schedule;
	public float rating;

	public Program(String imageUrl, String programTitle, String schedule, float programRating){
		imgUrl = imageUrl;
		title = programTitle;
		this.schedule = schedule;
		rating = programRating;
	}
}
