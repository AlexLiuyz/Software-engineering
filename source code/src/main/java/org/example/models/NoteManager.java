package org.example.models;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class NoteManager extends Manager<Note>{
    public static final Set<String> AVAILABLE_FILTERS = new HashSet(Arrays.asList("title", "content"));

    public NoteManager(String name){
        super();
        this.schema = Note.class;
        this.name = name;
        this.storage = new LinkedHashMap<>(); 
        this.count = 0;
    }
    public NoteManager(){
        super();
        this.schema = Note.class;
        this.name = "notes";
        this.storage = new LinkedHashMap<>();
        this.count = 0;
    }

    public String getName(){
        return this.name;
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
            Note note = Note.fromString(line);
            this.storage.put(note.getId(), note);
        }
        bufferedReader.close();
    }

    protected String generateId(){
        return "N"+ count++;
    }
}
