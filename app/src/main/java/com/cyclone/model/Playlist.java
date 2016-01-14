package com.cyclone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macair on 1/14/16.
 */
public class Playlist {

    public String name;
    public List<Song> songs;

    public Playlist(String name){
        this.name = name;
        songs = new ArrayList<>();
    }

    public Playlist(String name, List<Song> songs){
        this.name = name;
        this.songs = songs;
    }

    public void truncate(){
        songs = new ArrayList<>();
    }

    public void add(Song s){
        songs.add(s);
    }

    public void remove(Song s){
        songs.remove(s);
    }

    public void remove(int index){
        songs.remove(index);
    }
}
