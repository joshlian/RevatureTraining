package com.joshua.controller;

import java.util.List;

import com.joshua.repository.PlaylistRepository;
import com.joshua.service.PlaylistService;
import com.joshua.utility.InputHandler;
import com.joshua.service.model.Playlist;

public class PlaylistController {
    
    //passing in an instatition so we can mock it 
    private final PlaylistService playlistService;
    public PlaylistController() {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        playlistService = new PlaylistService(playlistRepository);
    }

    int choice = 0;
    public void start() {
        do {
            //features of the application 
            System.out.println("\n=== Playlsit Menu ===");
            System.out.println("1.  Create playlist");
            System.out.println("2.  View all playlist");
            System.out.println("3.  Update playlist");
            System.out.println("4.  Delete playlists");
            System.out.println("5.  Exit\n");

            choice = InputHandler.getIntInput("\nplease enter a number from 1 - 5");
            switch (choice) {
                case 1:
                    createPlaylist();
                    break;
                case 2: 
                    viewPlaylist();
                    break;
                case 3: 
                    updatePlaylist();
                    break;
                case 4:
                    deletePlaylist();
                    break;
            }

        } while(choice != 5);
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
        boolean created = playlistService.create(playlistName);
        if (created) {
            System.out.println("\nPlaylist successfully created.");
        } else {
            System.out.println("\nPlaylist creation failed.");
        }
    }  
   
    public boolean viewPlaylist() {
        List<Playlist> playlist = playlistService.getAllModels();
        if (playlist.isEmpty()) {
            System.out.println("\nNo Playlist yet, Add some now!");
            return false;
        }
        System.out.println();
        for (Playlist p : playlist){
            System.out.println(p);
        }
        return true;
    }

    public void updatePlaylist() {
        boolean exist = viewPlaylist();
        if (!exist) {return;}

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
        boolean updated = playlistService.update(id, newPlaylistName);
        if (updated) {
            System.out.println("\nPlaylist successfully updated.");
        } else {
            System.out.println("\nNo Playlist found");
        }
    }

    public void deletePlaylist() {
        boolean exist = viewPlaylist();
        if (!exist) {return;}
        
        Integer id;
        while (true) {
            id = InputHandler.getIntInput("\nEnter playlist ID: ");
            if (id > 0) break;
            System.out.println("Invalid input! Try again.");
        }
        boolean deleted = playlistService.deletebyId(id);
        if (deleted) {
            System.out.println("\nPlaylist successfully deleted.");
        } else {
            System.out.println("\nPlaylist ID not found");
        }
    }
}
