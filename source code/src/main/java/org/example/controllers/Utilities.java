package org.example.controllers;

import org.example.models.Connector;
import org.example.models.Filter;
import org.example.models.Note;
import org.example.models.Operator;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

public class Utilities {
    public static void PromptToContinue(){
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Press 'Enter' key to continue ...");
        scanner.nextLine();
    }

    public static String stringPrompter(String message){
        Scanner scanner = new Scanner(System.in);
        String ret;
        System.out.println(message);
        do{
            ret = scanner.nextLine();
            if(ret.isEmpty()){
                System.out.println("Invalid input! The input cannot be empty");
            }
        }while (ret.isEmpty());

        return ret;

    }

    public static LocalDateTime DateTimePrompter(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        System.out.println("Format: YYYY-MM-DD HH:MM");
        String input;
        LocalDateTime ret=null;
        do{
            try{
                input = scanner.nextLine();
                ret = LocalDateTime.parse(input.replace(" ", "T"));
            }
            catch(Exception exception){
                System.out.println("Invalid format! Please try again");
            }

        }
        while(ret == null);
        return ret;
    }

    public static String choicePrompter(String menu, String[] options){
        String input;
        boolean isValid = false;
        System.out.println(menu);
        do{
            input = Utilities.stringPrompter("Enter the choice");
            isValid = Arrays.asList(options).contains(input);
            if(!isValid){
                System.out.println("Invalid choice selection! Please try again!");
            }
        }
        while(!isValid);
        return input;
    }

    public static String choicePrompter(String menu, List<String> options){
        String input;
        boolean isValid = false;
        System.out.println(menu);
        do{
            input = Utilities.stringPrompter("Enter the choice");
            isValid = options.contains(input);
            if(!isValid){
                System.out.println("Invalid choice selection! Please try again!");
            }
        }
        while(!isValid);
        return input;
    }

    public static HashMap<String, String> getAttributeMap(Class schema){
        HashMap<String, String> map = new HashMap<>();
        Field[] fields = schema.getDeclaredFields();
        int index= 1;
        for(Field field: fields){
            map.put(Integer.toString(index++), field.getName());
        }
        return map;
    }

    public static String getMenuFromMap(String title, HashMap<String, String> map){
        StringBuilder builder = new StringBuilder();
        builder.append(title+"\n");
        for(String key: map.keySet()){
            builder.append(key+". "+ map.get(key) + "\n");
        }
        return builder.toString();
    }

    public static String selectAttribute(Class schema){
        HashMap<String, String> attributes = getAttributeMap(schema);
        String choice;
        final String ATTRIBUTE_MENU = getMenuFromMap("Please select the filter key", attributes);
        final List<String> ATTRIBUTE_CHOICE_ACCEPTED = attributes.keySet().stream().toList();
        choice = Utilities.choicePrompter(ATTRIBUTE_MENU, ATTRIBUTE_CHOICE_ACCEPTED);
        return attributes.get(choice);
    }

    public static Filter selectFilter(Class schema) throws NoSuchFieldException {
        String attribute = selectAttribute(schema);
        Operator op = selectOperator(schema.getDeclaredField(attribute).getType());
        return new Filter(attribute, op);
    }

    public static List<Operator> getValidOperators(Class schema){
        LinkedList<Operator> ops = new LinkedList<>();
        ops.add(Operator.EQUALS);
        if(schema.isAssignableFrom(String.class)){
            ops.add(Operator.CONTAINS);
        } else if (schema.isAssignableFrom(LocalDateTime.class)) {
            ops.add(Operator.BEFORE);
            ops.add(Operator.AFTER);
            ops.add(Operator.ONSAMEDATE);
        }
        return ops;
    }

    public static HashMap<String, String> getOperatorsMenuMap(Class schema){
        HashMap<String, String> menuMap = new HashMap<>();
        List<Operator> ops = getValidOperators(schema);
        int index = 1;
        for (Operator op: ops){
            menuMap.put(Integer.toString(index++), op.toString());
        }
        return menuMap;
    }

    public static Operator selectOperator(Class schema){
        HashMap<String, Operator> mapper = new HashMap<>();
        mapper.put(Operator.EQUALS.toString(), Operator.EQUALS);
        mapper.put(Operator.CONTAINS.toString(), Operator.CONTAINS);
        mapper.put(Operator.BEFORE.toString(), Operator.BEFORE);
        mapper.put(Operator.AFTER.toString(), Operator.AFTER);
        mapper.put(Operator.ONSAMEDATE.toString(), Operator.ONSAMEDATE);
        String choice;
        HashMap<String, String> menuMap = getOperatorsMenuMap(schema);
        final String OPERATOR_MENU = getMenuFromMap("Select the filtering operator", menuMap);
        final List<String> OPERATOR_CHOICE_ACCEPTED = menuMap.keySet().stream().toList();
        choice = Utilities.choicePrompter(OPERATOR_MENU, OPERATOR_CHOICE_ACCEPTED);
        return mapper.get(menuMap.get(choice));
    }

    public static List<Filter> getFilters(Class schema) throws NoSuchFieldException {
        LinkedList<Filter> filters = new LinkedList<>();
        do{
            String choice;
            final String ATTRIBUTE_MENU = """
                Select the filters by:
                1. Add a new Filter
                2. Save and Exit
                """;
            final String[] ATTRIBUTE_CHOICE_ACCEPTED = {"1", "2"};
            choice = Utilities.choicePrompter(ATTRIBUTE_MENU, ATTRIBUTE_CHOICE_ACCEPTED);
            if(choice.equals("1")){
                Filter filter = Utilities.selectFilter(schema);
                filters.add(filter);
            }
            else{
                break;
            }
        } while(true);
        return filters;
    }

    public static Connector getConnector(){
        HashMap<String, Connector> connectors = new HashMap<>();
        connectors.put("1", Connector.AND);
        connectors.put("2", Connector.OR);

        final String FILTERS_MENU = """
                Select the filters connector
                1. Strict (AND)
                2. Disjunction (OR)
                """;
        final String[] FILTERS_CHOICE_ACCEPTED = {"1", "2"};
        String mergerChoice = Utilities.choicePrompter(FILTERS_MENU, FILTERS_CHOICE_ACCEPTED);
        return connectors.get(mergerChoice);
    }

    public static void lineDrawer(int total){
        for(int i = 0; i < 8; i++){
            System.out.print(" ");
        }
        for(int i = 0; i < total; i++){
            System.out.print("-");
        }
        System.out.printf("\n");
    }

}
