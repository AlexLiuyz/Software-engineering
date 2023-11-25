package org.example.models;

import java.time.LocalDateTime;

public class Task extends Entity {
    private String title;
    private String description;
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String id, String title, String description, LocalDateTime deadline){
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String title, String description, LocalDateTime deadline){
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.id+"\t").
                append(this.title+"\t").
                append(this.description+"\t").
                append(this.deadline+"\t");
        return builder.toString();
    }

    public Task getCopy(){
        return new Task(this.id, this.title, this.description, this.deadline);
    }

    public static Task fromString(String literal) throws Exception {
        String[] tokens = literal.split("\t");
        if(tokens.length == 4){
            return new Task(tokens[0], tokens[1], tokens[2], LocalDateTime.parse(tokens[3]));
        }
        else{
            throw new Exception("Invalid format of note");
        }
    }
}
