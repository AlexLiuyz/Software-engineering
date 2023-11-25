package org.example.models;

import org.example.Errors.UnsupportedOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Manager<T extends Entity> {
    protected HashMap<String, T> storage;
    protected String name;
    protected Class schema;
    protected int count;

    protected abstract String generateId();
    public void create(T entity){
        entity.setId(this.generateId());
        storage.put(entity.getId(), (T) entity.getCopy());
    }
    public void update(T entity){
        T entityToBeUpdated = this.storage.get(entity.getId());
        if(entityToBeUpdated == null){
            // Throw exception
            return;
        }
        this.storage.put(entity.getId(), (T) entity.getCopy());
    }
    public void delete(String id){
        this.storage.remove(id);
    }
    public List<T> getAll(){
        return this.storage.values().stream().toList();
    }
    public T getById(String id){
        return this.storage.get(id);
    }
    public List<T> findByAttribute(String attribute, Object target, Operator op) throws NoSuchFieldException, IllegalAccessException, UnsupportedOperation {
        LinkedList<T> results = new LinkedList();
        Verifier verifier = VerifierCreator.createVerifier(op);
        try {
            Field field = this.schema.getDeclaredField(attribute);
            field.setAccessible(true);
            for (T note : this.storage.values()) {
                Object value = field.get(note);
                if (verifier.verify(value, target)) {
                    results.add(note);
                }
            }
        }
        catch (NoSuchFieldException noSuchFieldException){
            throw new NoSuchFieldException(noSuchFieldException.getMessage() + " does not exist as an attribute");
        }
        return results;
    }

    public List<T> search(List<Filter> filters, Connector connector) throws NoSuchFieldException, IllegalAccessException, UnsupportedOperation {
        List<T> results = null;
        Merger merger = MergerCreator.createMerger(connector);
        for(Filter filter: filters){
            List<T> searchResults = findByAttribute(filter.getAttribute(), filter.getTarget(), filter.getOperator());
            if( results == null){
                results = new LinkedList<>(searchResults);
            }
            else{
                results = merger.merge(results, searchResults);
            }
        }
        return results;
    }

    public boolean checkId(String id){
        if (storage.containsKey(id) == false){
            return false;
        }
        return true;
    }

    public abstract void load() throws Exception;
    public void save() throws IOException {
        final String FILENAME = this.name+".pim";
        File file = new File(FILENAME);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(this.count + "\n");
        for(T entity: this.storage.values()){
            writer.write(entity.toString() + "\n");
        }
        writer.close();
    }
}
