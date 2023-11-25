package org.example.views;

import org.example.controllers.Utilities;
import org.example.models.Contact;
import org.example.models.Filter;
import org.example.models.Note;

import java.util.List;

public class NoteView extends View<Note>{
    public void displayDetails(Note note){
        // Implementation
        System.out.println("Title:    "+note.getTitle());
        System.out.println();
        System.out.println("Content:  "+note.getContent());
    }

    public void displayList(List<Note> notes){

        if(notes == null || notes.isEmpty()){
            System.out.println("No notes available in the system");
            return;
        }
        int maxTitleLength = 0;
        int maxContentLength = 0;
        for (Note note : notes) {
            maxTitleLength = Math.max(maxTitleLength, note.getTitle().length());
            maxContentLength = Math.max(maxContentLength, note.getContent().length());
        }

        int total = maxTitleLength + maxContentLength + 12 + 2;

        //implementation
        System.out.printf("Search Result: \n");
        Utilities.lineDrawer(total);
        System.out.printf("   %8s  |  %-" + maxTitleLength + "s  |  %-" + maxContentLength + "s%n",
                "ID", "Title", "Content");

        Utilities.lineDrawer(total);
        for (Note note : notes) {
            System.out.printf("   %8s  |  %-" + maxTitleLength + "s  |  %-" + maxContentLength + "s%n",
                    note.getId(), note.getTitle(), note.getContent());
        }
        Utilities.lineDrawer(total);

    }
    public void displayError(Exception e){
        System.out.println("Error" + e.getMessage());
    }

    public void displayFilters(List<Filter> filters){
        for(Filter filter: filters){
            System.out.println(filter.getAttribute() + "\t" + filter.getOperator().toString() + "\t" + filter.getTarget());
        }
    }
}
