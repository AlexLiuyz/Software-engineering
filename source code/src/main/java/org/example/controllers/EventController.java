package org.example.controllers;

import org.example.Errors.ValidationError;
import org.example.models.Event;
import org.example.models.EventManager;

import org.example.views.EventView;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Scanner;

public class EventController {
    private EventManager manager;
    private EventView view;
    private SearchService searchService;
    private Class schema;
    private boolean changed=false;
    public EventController() {
        this.schema = Event.class;
        this.view = new EventView();
        this.manager = new EventManager();
        this.searchService = new SearchService(this.schema, this.view, this.manager);
    }

    public void init() {
        try {
            manager.load();
        }
        catch (Exception e){}
        final String MENU = """
                ------------------------
                please select an option:
                1.  Create a new event
                2.  Search for a event
                3.  Update a event
                4.  Delete a event
                5.  View all events
                6.  Open event
                7.  Save event
                99. Quit
                """;
        System.out.println("""
                ------------------------
                      EVENT MANAGER
                ------------------------
                """);
        boolean shouldStop = false;
        Scanner scanner = new Scanner(System.in);
        while(!shouldStop) {
            System.out.println(MENU);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> this.handleCreate();
                case "2" -> this.handleSearch();
                case "3" -> this.handleUpdate();
                case "4" -> this.handleDelete();
                case "5" -> this.handleViewAll();
                case "6" -> this.handleOpen();
                case "7" -> this.handleSave();
                case "99" -> {
                    System.out.println("Closing event manager...");
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
                }
                default -> System.out.println("Invalid input! Please try again");
            }
        }

    }
    public void handleCreate(){
        // implement the method to collect user create request
        String name = Utilities.stringPrompter("Enter a name of a event");
        LocalDateTime start = Utilities.DateTimePrompter("Enter start time for a event");

        String description = Utilities.stringPrompter("Enter description for the event");
        Event event= new Event(name,description,start);
        this.manager.create(event);
        System.out.println("Event created successfully with ID "+event.getId());
        String choice=Utilities.stringPrompter("Do you want to set an alarm? (type 'y' or press any key to quit)");
        while(true) {
            if (choice.equals("y")){
                LocalDateTime alarm=Utilities.DateTimePrompter("Enter the alarm time");
                try {
                    if(alarm.isAfter(LocalDateTime.now())){
                        event.setAlarm(alarm);
                    }
                    else{
                        throw new ValidationError("Alarm cannot be set before current time");
                    }
                }
                catch (ValidationError e){view.displayError(e);}
                break;
            }
            else{
                break;
            }
        }
        Utilities.PromptToContinue();
        changed=true;
    }
    public void handleUpdate() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter event's ID:");
            String id = scanner.nextLine().toUpperCase();
            if(manager.checkId(id)==false){
                System.out.println("Event does not exist.");
                Utilities.PromptToContinue();
                return;
            }
            final String submenu= """
                Please enter ur choice:
                1. Change the event content
                2. Reset alarm
                3. Remove the alarm
                99. Quit
                """;
            boolean shouldStop = false;
            while(!shouldStop){
                System.out.println(submenu);
                String choice = scanner.nextLine();
                switch(choice) {
                    case "1":
                        System.out.println("Enter event name:");
                        String name = scanner.nextLine();
                        System.out.println();
                        LocalDateTime start_time = Utilities.DateTimePrompter("Enter new start time:");
                        System.out.println("Enter new description:");
                        String description= scanner.nextLine();
                        Event event = new Event(id,name,description,start_time);
                        this.manager.update(event);
                        changed=true;
                        Utilities.PromptToContinue();
                        return;
                    case "2":
                        LocalDateTime alarm = Utilities.DateTimePrompter("Please set the alarm:");
                        manager.updateAlarm(id,alarm);
                        Utilities.PromptToContinue();
                        changed=true;
                        return;
                    case "3":
                        manager.updateAlarm(id,null);
                        Utilities.PromptToContinue();
                        System.out.println("Alarm have been removed");
                        changed=true;
                        return;
                    case "99":
                        return;
                    default:
                        System.out.println("Invalid input, please try again!");
                        break;
                }
            }
        }
        catch (Exception e){
            this.view.displayError(e);
        }

    }
    public void handleDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the event id: ");
        String id=scanner.nextLine().toUpperCase();
        if(manager.checkId(id)==false){
            System.out.println("Not exist id...");
            return;
        }
        this.manager.delete(id);
        System.out.println("Deleted successfully");
        Utilities.PromptToContinue();
        changed=true;
    }
    public void handleViewAll(){
        // Implement the method to collect user view request
        List<Event> events = this.manager.getAll();
        if (events.size() > 0) {
            this.view.displayList(events);
        }
        else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }
    public void handleOpen() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Event ID");
        String id = scanner.nextLine().toUpperCase();

        Event event = this.manager.getById(id);

        if(event == null){
            //Display valid error message
            System.out.println("Event not found");
            return;
        }

        this.view.displayDetails(event);
        Utilities.PromptToContinue();
    }

    private void handleSearch() {
        this.searchService.handleSearch();
    }

    public void handleSave(){
        try{
            this.manager.save();
        }
        catch(Exception e){
            this.view.displayError(e);
        }
        System.out.println("Your event is already saved!");
        changed=false;
    }
}