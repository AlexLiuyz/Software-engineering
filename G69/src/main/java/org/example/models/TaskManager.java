package org.example.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;

public class TaskManager extends Manager<Task>{
    public TaskManager(){
        super();
        this.schema = Task.class;
        this.name = "tasks";
        this.storage = new LinkedHashMap<>();
        this.count = 1;
    }
    public TaskManager(String name){
        super();
        this.schema = Task.class;
        this.name = name;
        this.storage = new LinkedHashMap<>();
        this.count = 1;
    }
    public String getName(){
        return this.name;
    }
    public Class getSchema(){
        return this.schema;
    }
    public int getCount(){
        return this.count;
    }
    public void load() throws Exception {
        final String FILENAME = this.name+".pim";
        String line;
        File file = new File(FILENAME);
        if(!file.exists()){
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        line = bufferedReader.readLine();
        this.count = Integer.parseInt(line);
        while((line=bufferedReader.readLine()) != null){
            Task task = Task.fromString(line);
            this.storage.put(task.getId(), task);
        }
        bufferedReader.close();
    }

    protected String generateId(){
        return "T" + count++;
    }

}
