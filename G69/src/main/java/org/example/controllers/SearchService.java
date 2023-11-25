package org.example.controllers;

import org.example.models.Connector;
import org.example.models.Filter;
import org.example.models.Manager;
import org.example.models.Note;
import org.example.views.View;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchService {
    protected List<Filter> filters;
    protected Class schema;
    protected Connector connector;
    protected View view;
    protected Manager manager;

    public SearchService(Class schema, View view, Manager manager) {
        this.schema = schema;
        this.view = view;
        this.manager = manager;
    }

    public void handleSearch(){
        try {
            if(connector == null){
                this.connector = Utilities.getConnector();
            }

            if(filters == null){
                this.filters = Utilities.getFilters(this.schema);
            }

            HashMap<String, String> menuMap = new LinkedHashMap<>();
            menuMap.put("1", "Query");
            menuMap.put("2", "Reset filters");
            menuMap.put("3", "Reset connector");
            menuMap.put("99", "Back");


            do{
                final String MENU = Utilities.getMenuFromMap("Select one of the choices below:", menuMap);
                String choice = Utilities.choicePrompter(MENU, menuMap.keySet().stream().toList());

                if(choice.equals("1")){
                    for(Filter filter: filters){
                        this.view.displayFilters(filters);
                        if(schema.getDeclaredField(filter.getAttribute()).getType().isAssignableFrom(String.class)){
                            String value = Utilities.stringPrompter("Please enter " + filter.getAttribute());
                            filter.setTarget(value);
                        }
                        else if(schema.getDeclaredField(filter.getAttribute()).getType().isAssignableFrom(LocalDateTime.class)){
                            LocalDateTime value = Utilities.DateTimePrompter("Please enter " + filter.getAttribute());
                            filter.setTarget(value);
                        }
                    }
                    List<Note> notes = this.manager.search(filters, connector);
                    this.view.displayList(notes);
                }
                else if(choice.equals("2")){
                    this.filters = Utilities.getFilters(this.schema);
                }
                else if(choice.equals("3")){
                    this.connector = Utilities.getConnector();
                } else if (choice.equals("99")) {
                    break;
                }
            }while(true);


        } catch (Exception e) {
            System.err.println(e);
            this.view.displayError(e);
        }
        Utilities.PromptToContinue();
    }
}
