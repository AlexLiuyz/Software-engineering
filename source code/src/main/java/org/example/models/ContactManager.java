package org.example.models;

import java.io.*;
import java.util.LinkedHashMap;


public class ContactManager extends Manager<Contact> {
    public ContactManager(){
        super();
        this.schema = Contact.class;
        this.name="contacts";
        this.storage = new LinkedHashMap<>();
        this.count = 0;
    }

    public ContactManager(String name){
        super();
        this.schema = Contact.class;
        this.name=name;
        this.storage = new LinkedHashMap<>();
        this.count = 0;
    }
    protected String generateId(){
        return "C"+ count++;
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
            Contact contact = Contact.fromString(line);
            this.storage.put(contact.getId(), contact);
        }
        bufferedReader.close();

    }

}
