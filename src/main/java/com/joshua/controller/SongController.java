package com.joshua.controller;

import java.util.List;
import java.util.Optional;

import com.joshua.repository.entities.SongEntity;
import com.joshua.service.SongService;
import com.joshua.service.model.Song;
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
                    viewAllSongs();
                    break;
                case 3: 
                    viewSongByID();
                    break;
                case 4:
                    updateSongByID();
                    break;
                case 5:
                    deleteSongByID();
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
        Integer songID = songService.addSong(songEntity);
        if (songID == null) {
            System.err.println("could not access the database");
        }
        else {
            songEntity.setSongId(songID);
            boolean added = songService.addSongtoPlaylist(playlistId, songEntity);
            if (added) {
                System.out.println("\nSong successfully added.");
            } else {
                System.out.println("\nsong could not be added.");
            }
        }
    }

    public void viewAllSongs() {
        List <Song> songs = songService.getAllModels();
        if (songs.isEmpty()) {
            System.out.println("oops, no song exists, add some now!");
        }
        else {
            System.out.println();
            for (Song s : songs) {
                System.out.println(s);
            }
        }
    }

    public void viewSongByID () {
        Integer id;
        viewAllSongs();
        while (true) {
            id = InputHandler.getIntInput("\nEnter song ID: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        Optional<Song> song = songService.getModelById(id);
        if(song.isPresent()) {
            System.out.println("\n" + song.get());
        }
        else {
            System.out.println("\nsong does not exist");
        }
    }
    private void deleteSongByID() {
    
    }

    private void updateSongByID() {
    
    }
}
