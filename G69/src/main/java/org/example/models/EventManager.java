package org.example.models;

import org.example.Errors.ValidationError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;


public class EventManager extends Manager<Event>{
    public EventManager(){
        super();
        this.name = "events";
        this.schema = Event.class;
        this.storage= new LinkedHashMap<>();
        this.count=0;
    }
    public EventManager(String name){
        super();
        this.name = name;
        this.schema = Event.class;
        this.storage= new LinkedHashMap<>();
        this.count=0;
    }

    public void updateAlarm(String id, LocalDateTime alarm) throws ValidationError {
        Event event = storage.get(id);
        if(alarm==null){
            event.setAlarm(null);
            return;
        }
        if(alarm.isBefore(LocalDateTime.now())){
            throw new ValidationError("Alarm cannot be set before current time");
        }
        event.setAlarm(alarm);
    }

    protected String generateId(){
        return "N"+count++;
    }
    public String getName(){
        return this.name;
    }

    public void load() throws Exception {
        final String FILENAME = this.name+".pim";
        String line;
        File file = new File(FILENAME);
        if (!file.exists()) {
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        line = bufferedReader.readLine();
        this.count = Integer.parseInt(line);
        while ((line = bufferedReader.readLine()) != null) {
            Event event = Event.fromString(line);
            this.storage.put(event.getId(), event);
        }
        bufferedReader.close();

    }
}
