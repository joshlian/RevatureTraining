package com.joshua.utility;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    public static Integer getIntInput(String prompt){
        while(true){
            System.out.println(prompt);
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.print("Invalid choice:");
            }
        }
    }

    public static String getStringInput(String prompt){
        while(true){
            System.out.println(prompt);
            try{
                return scanner.nextLine();
            }catch(RuntimeException e){
                System.out.print("Invalid: Please enter a String ");
            }
        }
    }
}
