package com.joshua.service.model;

import java.util.List;

public class Playlist {
    private Integer id;
    private String name;
    private List<Song> songs;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }

    @Override
    public String toString() {
        return id + " --> " + name;
    }
}