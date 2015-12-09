package com.cyclone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/12/2015.
 */
public class Data {

	private static List<Object> data = new ArrayList<>();

	public static void reset(){
		data = new ArrayList<>();
	}

	public static void add(Object o){
		data.add(o);
	}

	public static void remove(Object o){
		data.remove(o);
	}

	public static List<Object> getData(){
		return data;
	}
}
