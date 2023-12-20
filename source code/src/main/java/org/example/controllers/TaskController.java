package org.example.controllers;

import org.example.models.Task;
import org.example.models.TaskManager;
import org.example.views.TaskView;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Scanner;

public class TaskController {
    private TaskManager manager;
    private boolean changed=false;
    private TaskView view;
    private Class schema;
    private SearchService searchService;

    public TaskController(){
        this.view = new TaskView();
        this.manager = new TaskManager();
        this.schema = Task.class;
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
                1.  Create a new task
                2.  Search for a task
                3.  Update a task
                4.  Delete a task
                5.  View all tasks
                6.  Open task
                7.  Save task
                99. Quit
                """;
        System.out.println("""
                ------------------------
                      TASK MANAGER
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
                    System.out.println("Closing task manager...");
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a title for a task:");
        String title = scanner.nextLine();

        System.out.println("Enter content for a task:");
        String content = scanner.nextLine();

        LocalDateTime deadline = Utilities.DateTimePrompter("Enter a deadline for a task");
        Task task = new Task(title, content, deadline);
        this.manager.create(task);
        System.out.println("Task created successfully with ID "+task.getId());
        Utilities.PromptToContinue();
        changed=true;
    }

    public void handleSearch(){
        this.searchService.handleSearch();

    }

    public void handleUpdate(){
        Scanner scanner = new Scanner(System.in);

        String id = (Utilities.stringPrompter("Enter task's ID:")).toUpperCase();
        if(manager.checkId(id)==false){
            System.out.println("The task does not exist.");
            Utilities.PromptToContinue();
            return;
        }
        System.out.println("Enter new title:");
        String title = scanner.nextLine();

        System.out.println("Enter new content:");
        String content = scanner.nextLine();

        System.out.println();
        LocalDateTime deadline = Utilities.DateTimePrompter("Enter a new deadline for a task");

        Task task = new Task(id, title, content, deadline);
        this.manager.update(task);
        Utilities.PromptToContinue();
        changed=true;
    }

    public void handleDelete(){
        int size = this.manager.getAll().size();
        if (size > 0) {
            // Implement the method to collect user delete request
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the task ID:");
            String id = (scanner.nextLine().toUpperCase()).toUpperCase();

            if (this.manager.checkId(id) == true) {
                System.out.println("Deleted successfully!");
                this.manager.delete(id);
                changed=true;
            } else {
                System.out.println("Task does not exist!");
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
        List<Task> tasks = this.manager.getAll();
        if (tasks.size()>0){
        this.view.displayList(tasks);}
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

            System.out.println("Enter note ID");
            String id = (scanner.nextLine().toUpperCase()).toUpperCase();

            Task task = this.manager.getById(id);

            if (task == null) {
                //Display valid error message
                System.out.println("Task not found");
                return;
            }

            this.view.displayDetails(task);
        }else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }

    private void handleSave(){
        try{
            this.manager.save();
            System.out.println("Your tasks are saved!");

        }
        catch(Exception e){
            this.view.printError(e);
        }

    }
}
