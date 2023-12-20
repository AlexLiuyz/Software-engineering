package org.example.views;
import org.example.controllers.Utilities;
import org.example.models.Contact;
import org.example.models.ContactManager;
import org.example.models.Note;

import java.util.List;


public class ContactView extends View<Contact> {
    public void displayDetails(Contact contact){
        // Implementation
        System.out.println("Name:    "+contact.getName());
        System.out.println();
        System.out.println("Address: "+contact.getAddress());
        System.out.println();
        System.out.println("Number:  "+contact.getNumber());

    }

    public void displayList(List<Contact> contacts) {

        if(contacts == null || contacts.isEmpty()){
            System.out.println("No contacts available in the system");
            return;
        }

        int maxNameLength = 0;
        int maxAddressLength = 0;
        int maxNumberLength = 0;
        for (Contact contact : contacts) {
            maxNameLength = Math.max(maxNameLength, contact.getName().length());
            maxAddressLength = Math.max(maxAddressLength, contact.getAddress().length());
            maxNumberLength = Math.max(maxNumberLength, contact.getNumber().length());
        }

        int total = maxNameLength + maxAddressLength + maxNumberLength + 16 + 3;

        System.out.printf("Search Result: \n");
        Utilities.lineDrawer(total);

        System.out.printf("   %8s  |  %-" + maxNameLength + "s  |  %-" + maxAddressLength + "s  |  %-" + maxNumberLength + "s%n",
                "ID", "Name", "Address", "Number");

        Utilities.lineDrawer(total);

        for (Contact contact : contacts) {
            System.out.printf("   %8s  |  %-" + maxNameLength + "s  |  %-" + maxAddressLength + "s  |  %-" + maxNumberLength + "s%n",
                    contact.getId(), contact.getName(),contact.getAddress(), contact.getNumber());
        }
        Utilities.lineDrawer(total);
    }

    public void displayError(Exception e){
        System.out.println("Error:"+ e.getMessage());
    }

}
