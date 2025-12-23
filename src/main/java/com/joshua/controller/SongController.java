package com.joshua.controller;

import java.util.List;
import java.util.Optional;

import com.joshua.repository.SongRepository;
import com.joshua.service.SongService;
import com.joshua.service.model.Playlist;
import com.joshua.service.model.Song;
import com.joshua.utility.InputHandler;

public class SongController {

    //passing in an instatition so we can mock it 
    private PlaylistController plc = new PlaylistController();
    private final SongService songService;
    public SongController () {
        SongRepository songRepository = new SongRepository();
        songService = new SongService(songRepository);
    }

    int choice = 0; 
    public void start() {
        do {
            //features of the application 
            System.out.println("\n=== Song Menu ===");
            System.out.println("1.  Add song to playlist");
            System.out.println("2.  View all songs");
            System.out.println("3.  View song by ID");
            System.out.println("4.  View songs in a playlist");
            System.out.println("5.  Update song by ID");
            System.out.println("6.  Delete song by ID");
            System.out.println("7.  Delete song in a Playlist");
            System.out.println("8.  Exit\n");

            choice = InputHandler.getIntInput("\nplease enter a number from 1 - 8");
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
                    viewSongsinPlaylist();
                    break;
                case 5:
                    updateSongByID();
                    break;
                case 6:
                    deleteSongByID();
                    break;
                case 7:
                    deleteSongInPlaylist();
                    break;
            }   

        } while(choice != 8);
    }

    //song functions 
    public void addSongToPlaylist() {

        String songName;
        String artistName;
        Integer playlistId = 0;
        while(true) {
            songName = InputHandler.getStringInput("Enter song name: ").trim().toLowerCase();
            if (!songName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        while(true) {
            artistName = InputHandler.getStringInput("Enter artist name: ").trim().toLowerCase();;
            if (!artistName.isEmpty()) {
                break;
            } 
            System.out.println("Invalid input!");
        }

        boolean exist = plc.viewPlaylist();
        if(exist) {
            while (true) {
                playlistId = InputHandler.getIntInput("\nEnter playlist ID: ");
                if (playlistId > 0) break;
                System.out.println("Invalid input! Try again.");
            }
        }

        Integer songID = songService.addSong(songName, artistName);
        if (songID == null) {
            System.err.println("Could not add song to the database");
            return;
        }
        if (!exist) {return;}
        boolean added = songService.addSongtoPlaylist(playlistId, songID);
        if (added) {
            System.out.println("\nSong successfully added.");
        } else {
            System.out.println("\nSong already in that playlist or invalid playlsit ID.");
        }
    }

    public boolean viewAllSongs() {
        List <Song> songs = songService.getAllModels();
        if (songs.isEmpty()) {
            System.out.println("\nNo song exists, add some now!");
            return false;
        }
        else {
            System.out.println();
            for (Song s : songs) {
                System.out.println(s);
            }
            return true;
        }
    }

    public void viewSongByID () {
        Integer id;
        boolean exist = viewAllSongs();
        if (!exist) {return;}
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
            System.out.println("\nSong does not exist");
        }
    }

    public void viewSongsinPlaylist () {
        boolean exist = plc.viewPlaylist();
        if (!exist) {return;}
        Integer id;
        while (true) {
            id = InputHandler.getIntInput("\nEnter playlist ID you want to view: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }

        Playlist playlist = songService.getSongsByPlaylsitId(id);
        if(playlist.getSongs().isEmpty())
        {
            System.out.println("\nSongs or playlist does not exist in playlist ID " +playlist.getId());
        }
        else {
            System.out.println("\nSongs for playlist ID " + playlist.getId() + "\n");
            for (Song s : playlist.getSongs()) {
            System.out.println(s);
            }
        }
    }

    public void updateSongByID() {
        String songName;
        String artistName;
        Integer id;
        boolean exist = viewAllSongs();
        if (!exist) {return;}
        while (true) {
            id = InputHandler.getIntInput("\nEnter song ID you want to update: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        while(true) {
            songName = InputHandler.getStringInput("Enter a new song name: ").trim().toLowerCase();
            if (!songName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        while(true) {
            artistName = InputHandler.getStringInput("Enter a new artist name: ").trim().toLowerCase();
            if (!artistName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        boolean updated = songService.update(id, songName, artistName);
        if(updated) {
            System.out.println("\nSong was updated");
        }
        else {
            System.err.println("\nSong does not exist");
        }
    }

    public void deleteSongByID() {
        Integer id;
        boolean exist = viewAllSongs();
        if (!exist) {return;}
        while (true) {
            id = InputHandler.getIntInput("\nEnter song ID: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        boolean deleted = songService.deletebyId(id);
        if(deleted) {
            System.out.println("\nSong was deleted");
        }
        else {
            System.err.println("\nSong does not exist");
        }
    }

    public void deleteSongInPlaylist () {
        boolean playExcist = plc.viewPlaylist();
        if (!playExcist) {return;}
        Integer playlistId;
        Integer songId;
        while (true) {
            playlistId = InputHandler.getIntInput("\nEnter playlist ID you want to delete from: ");
            if (playlistId > 0) break;
            System.out.println("Invalid input! Try again.");
        }

        Playlist songs = songService.getSongsByPlaylsitId(playlistId);
        if(songs.getSongs().isEmpty()) {
            System.out.println("\nNo songs exist in that playlist");
            return;
        }
        else {
            System.out.println();
            for (Song s : songs.getSongs()) {
                System.out.println(s);
            }
            while (true) {
                songId = InputHandler.getIntInput("\nEnter song ID you want to delete: ");
                if (songId > 0) break;
                System.out.println("Invalid input! Try again.");
            }
            boolean deleted = songService.deleteSongInPlaylist(songId,playlistId);
            if(deleted) {
                System.out.println("\nSong was successfully deleted in playlist");
            }
            else {
                System.out.println("\nSong does not exist in playlist");
            }
        }
    }
}
