package org.example;

import org.example.controllers.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String OPENING_HEADER = """
                 --------------------------------------------
                 
                   Welcome To Personal Information Manager
                 
                 --------------------------------------------
                """;
        final String MENU = """
                
                Please choose the option below:
                1.  Notes
                2.  Contacts
                3.  Tasks
                4.  Events
                99. Exit
                
                """;
        boolean shouldExit = false;
        Scanner scanner = new Scanner(System.in);
        while(!shouldExit){
            System.out.println(OPENING_HEADER);
            System.out.println(MENU);
            String input = scanner.next();
            switch (input){
                case "1":
                    NoteController noteController = new NoteController();
                    noteController.init();
                    break;
                case "2":
                    ContactController contactController = new ContactController();
                    contactController.init();
                    break;
                case "3":
                    TaskController taskController = new TaskController();
                    taskController.init();
                    break;
                case "4":
                    EventController EventController = new EventController();
                    EventController.init();
                    break;
                case "99":
                    System.out.println();
                    System.out.println("Bye!");
                    shouldExit = true;
                    break;
                default:
                    System.out.println("Error: Invalid input! Please try again");
                    Utilities.PromptToContinue();
            }
        }
    }
}