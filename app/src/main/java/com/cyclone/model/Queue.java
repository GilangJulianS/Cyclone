package com.cyclone.model;

/**
 * Created by gilang on 31/10/2015.
 */
public class Queue {

	private static int counter = 0;
	public int id;
	public String artist;
	public String title;
	public String duration;

	public Queue(String artist, String title, String duration){
		id = counter;
		counter++;
		this.artist = artist;
		this.title = title;
		this.duration = duration;
	}

	public static void reset(){
		counter = 0;
	}
}
