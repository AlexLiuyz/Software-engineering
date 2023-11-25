package org.example.controllers;

import org.example.models.*;
import org.example.views.ContactView;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ContactController {
    private ContactManager manager;
    private ContactView view;
    private boolean changed=true;
    private Class schema;
    private SearchService searchService;
    public ContactController(){
        this.view = new ContactView();
        this.manager = new ContactManager();
        this.schema = Contact.class;
        this.searchService = new SearchService(this.schema, this.view, this.manager);
    }

    public void init() {
        try {
            manager.load();
        }
        catch (Exception e){}
        // print menu for handling different functions
        final String MENU = """
                ------------------------
                Please select an option:
                1.  Create a new contact
                2.  Search for a contact
                3.  Update a contact
                4.  Delete a contact
                5.  View all contacts
                6.  Open contact
                7.  Save contact
                99. Quit
                """;
        System.out.println("""
                ------------------------
                    CONTACT MANAGER
                ------------------------
                """);
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
                    System.out.println("Closing contact manager...");
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

        boolean isCorrect = false;
        // implement the method to collect user create request
        String name =Utilities.stringPrompter("Please enter the name of the new contact: ");
        String address =Utilities.stringPrompter("Please enter the address of the new contact: ");

        while (isCorrect == false) {
            String number =Utilities.stringPrompter("Please enter the number of the new contact (with country code): ");

            char plusSign = number.charAt(0);
            boolean isNumber = true;

            try {
                double num = Double.parseDouble(number.substring(1));
            } catch (NumberFormatException nfe) {
                isNumber = false;
            }

            if (plusSign == '+' && isNumber == true) {
                Contact contact = new Contact(name,address,number);
                this.manager.create(contact);
                System.out.println("Contact created successfully with ID: " + contact.getId());
                isCorrect = true;
                changed=true;
            }
            else{
                System.out.println();
                System.out.println("Invalid phone number! Please try again!");
                System.out.println();
            }
        }
        Utilities.PromptToContinue();
    }

    public void handleUpdate(){
        // implement the method to collect user update request
        int size = this.manager.getAll().size();
        if (size > 0) {

            String id =(Utilities.stringPrompter("Please enter the ID of the contact: ")).toUpperCase();

            if (this.manager.checkId(id) == true) {

                String name =Utilities.stringPrompter("Please enter the new name of the contact: ");
                boolean isCorrect = false;
                String address =Utilities.stringPrompter("Please enter the new address of the contact: ");

                while (isCorrect == false) {
                    String number = Utilities.stringPrompter("Please enter the new number of the contact (with country code): ");
                    char plusSign = number.charAt(0);
                    boolean isNumber = true;

                    try {
                        double num = Double.parseDouble(number.substring(1));
                    } catch (NumberFormatException nfe) {
                        isNumber = false;
                    }

                    if (plusSign == '+' && isNumber == true) {
                        Contact contact = new Contact(id, name,address, number);
                        this.manager.update(contact);
                        System.out.println("Contact updated successfully!");
                        isCorrect = true;
                        changed=true;
                    } else {
                        System.out.println();
                        System.out.println("Invalid phone number! Please try again!");
                        System.out.println();
                    }
                }

            } else {
                System.out.println("Contact does not exist!");
                System.out.println();
            }
        }
        else{
            System.out.println();
            System.out.println("There is no existing record to search, please add new record first!");
        }
        Utilities.PromptToContinue();
    }

    public void handleDelete(){
        int size = this.manager.getAll().size();
        if (size > 0) {
            // Implement the method to collect user delete request
            String id =(Utilities.stringPrompter("Please enter the contact's UID you would like to delete: ")).toUpperCase();


            if (this.manager.checkId(id) == true) {
                this.manager.delete(id);
                System.out.println("Delete successfully!");
                changed=true;

            } else {
                System.out.println("Contact does not exist!");
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
        this.searchService.handleSearch();
    }

    public void handleViewAll(){
        // Implement the method to collect user view request
        List<Contact> contacts = this.manager.getAll();
        if (contacts.size() > 0) {
            this.view.displayList(contacts);
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

            String id = (Utilities.stringPrompter("Enter contact ID: ")).toUpperCase();

            Contact contact = this.manager.getById(id);

            if (contact == null) {
                //Display valid error message
                System.out.println("Contact not found!");
            }
            else {
                this.view.displayDetails(contact);
            }
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
        System.out.println("Your contact is already saved!");
        changed=false;
    }
}