package com.joshua;
import com.joshua.controller.PlaylistController;
import com.joshua.controller.SongController;
import com.joshua.utility.InputHandler;

public class Main {
    public static void main(String[] args) {
        PlaylistController plc = new PlaylistController();
        SongController sc = new SongController();
    
        boolean run = true;
        int choice = 0;
        while(run) {
            System.out.println("\n=== MENU ===");
            System.out.println("1: Playlist functions");
            System.out.println("2: Song functions");
            System.out.println("3: Exit application");

            choice = InputHandler.getIntInput("\nplease enter a number from 1 - 3");
            switch (choice) {
                case 1:
                    plc.start();
                    break;
                case 2:
                    sc.start();
                    break;
                case 3:
                    System.out.println("\nThank you for Listening!");
                    run = false;
            }

        }
        
    }
}