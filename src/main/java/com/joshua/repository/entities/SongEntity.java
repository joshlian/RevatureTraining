package com.joshua.repository.entities;

public class SongEntity {
    private int songId;
    private String songName;
    private String artistName;

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }

    public String getSongName() { return songName; }
    public void setSongName(String songName) { this.songName = songName; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
}
