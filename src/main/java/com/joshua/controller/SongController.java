package com.joshua.controller;

import com.joshua.repository.entities.SongEntity;
import com.joshua.service.SongService;
import com.joshua.utility.InputHandler;

public class SongController {
    SongService songService = new SongService();

    int choice = 0;
    public void start() {
        do {
            //features of the application 
            System.out.println("\n=== Song Menu ===");
            System.out.println("1.  Add song to playlist");
            System.out.println("2.  View all songs");
            System.out.println("3.  View song by ID");
            System.out.println("4.  Update song by ID");
            System.out.println("5.  Delete song by ID");
            System.out.println("6.  Exit\n");

            choice = InputHandler.getIntInput("\nplease enter a number from 1 - 6");
            switch (choice) {
                case 1:
                    addSongToPlaylist();
                    break;
                case 2: 
                    //viewAllSongs();
                    break;
                case 3: 
                    //viewSongByID();
                    break;
                case 4:
                    //updateSongByID();
                    break;
                case 5:
                    //deleteSongByID()
                    break;
            }

        } while(choice != 6);
    }

    //song functions 
    public void addSongToPlaylist() {
        String songName;
        String artistName;
        Integer playlistId;
        while(true) {
            songName = InputHandler.getStringInput("Enter song name: ").trim();
            if (!songName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        while(true) {
            artistName = InputHandler.getStringInput("Enter artist name: ").trim();
            if (!artistName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }

        PlaylistController plc = new PlaylistController();
        plc.viewPlaylist();

        while (true) {
            playlistId = InputHandler.getIntInput("\nEnter playlist ID: ");
            if (playlistId > 0) break;
            System.out.println("Invalid input! Try again.");
        }

        SongEntity songEntity = new SongEntity();
        songEntity.setSongName(songName);
        songEntity.setArtistName(artistName);
        songService.addSong(songEntity);
        boolean added = songService.addSongtoPlaylist(playlistId, songEntity);
        if (added) {
            System.out.println("Song successfully added.");
        } else {
            System.out.println("song could not be added.");
        }
    }
}
