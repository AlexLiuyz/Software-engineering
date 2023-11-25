package org.example.views;

import org.example.controllers.Utilities;
import org.example.models.Contact;
import org.example.models.Event;
import org.example.models.Note;
import org.example.models.Task;

import java.util.List;

public class TaskView extends View<Task> {
    public void displayDetails(Task task){
        // Implementation
        System.out.println(task.getTitle());
        System.out.println("-----------------------------");
        System.out.println(task.getDescription());
    }

    public void displayList(List<Task> tasks){
        //implementation

        if(tasks == null || tasks.isEmpty()){
            System.out.println("No tasks available in the system");
            return;
        }

        int maxTitleLength = 0;
        int maxContentLength = 0;
        int maxDeadlineLength = 0;

        for (Task task : tasks) {
            maxTitleLength = Math.max(maxTitleLength, task.getTitle().length());
            maxContentLength = Math.max(maxContentLength, task.getDescription().length());
            maxDeadlineLength = Math.max(maxDeadlineLength, task.getDeadline().toString().length());
        }

        int total = maxTitleLength + maxContentLength + maxDeadlineLength + 16 + 3;

        System.out.printf("Search Result: \n");
        Utilities.lineDrawer(total);

        System.out.printf("   %8s  |  %-" + maxTitleLength + "s  |  %-" + maxContentLength + "s  |  %-" + maxDeadlineLength + "s%n",
                "ID", "Title", "Content", "Deadline");
        Utilities.lineDrawer(total);

        for (Task task : tasks) {
            System.out.printf("   %8s  |  %-" + maxTitleLength + "s  |  %-" + maxContentLength + "s  |  %-" + maxDeadlineLength + "s%n",
                    task.getId(), task.getTitle(),task.getDescription(), task.getDeadline());
        }
        Utilities.lineDrawer(total);
    }

    public void printError(Exception e){
        System.out.println("Error" + e.getMessage());
    }
}

