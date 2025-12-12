package com.joshua.controller;

import java.util.List;

import com.joshua.repository.entities.PlaylistEntity;
import com.joshua.service.PlaylistService;
import com.joshua.utility.InputHandler;
import com.joshua.service.model.Playlist;

public class PlaylistController {
    private final PlaylistService playlistService = new PlaylistService();

    int choice = 0;
    public void start() {
        do {
            //features of the application 
            System.out.println("\n=== Playlsit Menu ===");
            System.out.println("1.  Create playlist");
            System.out.println("2.  View all playlist");
            System.out.println("3.  View songs in a playlist");
            System.out.println("4.  Update playlist");
            System.out.println("5.  Delete playlists");
            System.out.println("6.  Exit\n");

            choice = InputHandler.getIntInput("\nplease enter a number from 1 - 6");
            switch (choice) {
                case 1:
                    createPlaylist();
                    break;
                case 2: 
                    viewPlaylist();
                    break;
                case 3: 
                    //viewSongsinPlaylist();
                    break;
                case 4:
                    updatePlaylist();
                    break;
                case 5:
                    deletePlaylist();
                    break;
            }

        } while(choice != 6);
    }
 
    //functions for the features of the application 
    public void createPlaylist() {
        String playlistName;
        while(true) {
            playlistName = InputHandler.getStringInput("Enter new playlist name: ").trim();
            if (!playlistName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setPlaylistName(playlistName);
        boolean created = playlistService.create(playlistEntity);
        if (created) {
            System.out.println("Playlist successfully created.");
        } else {
            System.out.println("Playlist creation failed.");
        }
    }
   
    public void viewPlaylist() {
        List<Playlist> playlist = playlistService.getAllModels();
        if (playlist.isEmpty()) {
            System.out.println("No Playlist yet, Add some now!");
            return;
        }
        System.out.println();
        for (Playlist p : playlist){
            System.out.println(p);
        }
    }

    public void updatePlaylist() {
        viewPlaylist();
        
        Integer id;
        String newPlaylistName;
        while (true) {
            id = InputHandler.getIntInput("\nEnter playlist ID: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        while(true) {
            newPlaylistName = InputHandler.getStringInput("Enter new playlist name: ").trim();
            if (!newPlaylistName.isEmpty()) {
                break;
            }
            System.out.println("Invalid input!");
        }
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setPlaylistId(id);
        playlistEntity.setPlaylistName(newPlaylistName);
        boolean updated = playlistService.update(id, playlistEntity);
        if (updated) {
            System.out.println("Playlist successfully updated.");
        } else {
            System.out.println("Playlist ID not found or update failed.");
        }
    }

    public void deletePlaylist() {
        viewPlaylist();
        int id;
        while (true) {
            id = InputHandler.getIntInput("\nEnter playlist ID: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        boolean deleted = playlistService.deletebyId(id);
        if (deleted) {
            System.out.println("Playlist successfully deleted.");
        } else {
            System.out.println("Playlist ID not found or deletion failed.");
        }
    }
}
