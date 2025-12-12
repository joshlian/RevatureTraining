package com.joshua.service.model;

public class Song {
    private int id;
    private String name;
    private String artist;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    @Override
    public String toString() {
        return id + " --> " + name +" by "+ artist;
    }
}
