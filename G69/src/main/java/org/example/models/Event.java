package org.example.models;

import java.time.LocalDateTime;
public class Event extends Entity{
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime alarm;
    public String getName() {
        return name;
    }

    public Event(String id, String name,String description, LocalDateTime start ){
        this.id = id;
        this.name = name;
        this.start = start;
        this.description = description;
        this.alarm=null;
    }
    public Event(String id, String name, String description, LocalDateTime start, LocalDateTime alarm){
        this.id = id;
        this.name = name;
        this.start = start;
        this.description = description;
        this.alarm=alarm;
    }
    public Event(String name,  String description, LocalDateTime start){
        this.name = name;
        this.start = start;
        this.description = description;
        this.alarm=null;
    }
    public String getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }

    public LocalDateTime getAlarm(){
        return alarm;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    public void setAlarm(LocalDateTime alarm){
        // Parse the time string to LocalDateTime
        if(alarm==null){//the alarm have been removed
            this.alarm=null;
            return;
        }
        this.alarm=alarm;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.id + "\t").
                append(this.name + "\t").
                append(this.description + "\t").
                append(this.start + "\t").
                append(this.alarm);
        return builder.toString();
    }

    public static Event fromString(String literal) throws Exception {
        String[] tokens = literal.split("\t");
        if(tokens.length == 5){
            return new Event(tokens[0], tokens[1], tokens[2], LocalDateTime.parse(tokens[3]), tokens[4].equals("null")?null:LocalDateTime.parse(tokens[4]));
        }
        else{
            throw new Exception("Invalid format of note");
        }
    }
    @Override
    public Event getCopy(){
        return new Event(this.id, this.name,this.description, this.start, this.alarm);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Event){
            return this.id.equals(((Event) o).getId());
        }
        return false;
    }
}
