package org.example.controllers;

import org.example.Errors.UnsupportedOperation;
import org.example.models.*;
import org.example.views.NoteView;

import java.time.LocalDateTime;
import java.util.*;

public class NoteController{
    static boolean changed=false;
    public NoteView view;
    public NoteManager manager;
    public SearchService searchService;
    public Class schema;


    public NoteController(){
        this.view = new NoteView();
        this.manager = new NoteManager();
        this.schema = Note.class;
        this.searchService = new SearchService(this.schema, this.view, this.manager);
    }

    public void init(){
        try {
            manager.load();
        }
        catch (Exception e){}
        // print menu for handling different functions
        final String MENU = """
                ------------------------
                Please select an option:
                1.  Create a new note
                2.  Search for a note
                3.  Update a note
                4.  Delete a note
                5.  View all notes
                6.  Open note
                7.  Save note
                99. Quit
                """;
        System.out.println("""
                -------------------------
                       NOTE MANAGER
                -------------------------
                """);
        try {
            this.manager.load();
        } catch (Exception e) {}
        boolean shouldStop = false;
        Scanner scanner = new Scanner(System.in);
        while(!shouldStop){
            System.out.println();
            System.out.println(MENU);
            String choice = scanner.next();
            switch (choice){
                case "1":
                    this.handleCreate();
                    break;
                case "2":
                    this.handleSearch();
                    break;
                case "3":
                    this.handleUpdate();
                    break;
                case "4":
                    this.handleDelete();
                    break;
                case "5":
                    this.handleViewAll();
                    break;
                case "6":
                    this.handleOpen();
                    break;
                case "7":
                    this.handleSave();
                     break;
                case "99":
                    System.out.println("Closing note manager...");
                    try {
                        if(changed){
                            String c=Utilities.stringPrompter("Unsaved changes are made(type y to save, or press any other key to quit)");
                            if(c.equals("y")){
                                manager.save();
                            }
                        }
                    } catch (Exception e) {}
                    shouldStop = true;
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid input! Please try again");
                    break;
            }
        }
    }

    public void handleCreate(){
        // implement the method to collect user create request
        String title =Utilities.stringPrompter("Please enter the title of the new note: ");
        String content =Utilities.stringPrompter("Please enter the content of the new note: ");

        Note note = new Note(title, content);
        this.manager.create(note);
        System.out.println("Note created successfully with ID: "+note.getId());
        changed=true;
        Utilities.PromptToContinue();
    }

    public void handleUpdate(){
        // implement the method to collect user update request
        int size = this.manager.getAll().size();
        if (size > 0) {
            String id =Utilities.stringPrompter("Please enter the UID of the note: ");

            if (this.manager.checkId(id) == true) {

                String title =Utilities.stringPrompter("Please enter the new title of the note: ");
                String content =Utilities.stringPrompter("Please enter the new content of the note: ");

                Note note = new Note(id, title, content);
                this.manager.update(note);
                System.out.println("Note updated successfully!");
                changed=true;
            } else {
                System.out.println("Note does not exist!");
                System.out.println();
            }
        }
        else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }

    public void handleSearch(){
        searchService.handleSearch();
    }

    public void handleDelete(){
        // Implement the method to collect user delete request
        int size = this.manager.getAll().size();
        if (size > 0) {
            String id =Utilities.stringPrompter("Please enter the ID of the note: ");

            if (this.manager.checkId(id) == true) {
                this.manager.delete(id);
                System.out.println("Deleted successfully!");
                changed=true;
            } else {
                System.out.println("Note does not exist!");
                System.out.println();
            }
        }
        else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }
    public void handleViewAll(){
        // Implement the method to collect user view request
        List<Note> notes = this.manager.getAll();
        if (notes.size() > 0) {
            this.view.displayList(notes);
        }
        else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }

    public void handleOpen(){
        int size = this.manager.getAll().size();
        if (size > 0) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter note ID: ");
            String id = scanner.nextLine().toUpperCase();

            Note note = (Note) this.manager.getById(id);

            if (note == null) {
                //Display valid error message
                System.out.println("Note not found!");
                return;
            }

            this.view.displayDetails(note);
        }else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }
    public void handleSave(){
        try{
            this.manager.save();
        }
        catch(Exception e){
            this.view.displayError(e);
        }
        System.out.println("Your note is already saved!");
        changed=false;
    }
}
