package org.example.views;

import org.example.controllers.Utilities;
import org.example.models.Event;
import org.example.models.Note;

import java.util.List;

public class EventView extends View<Event>{
    public void displayDetails(Event event){
        // Implementation
        System.out.println(event.getName());
        System.out.println("-----------------------------");
        System.out.println(event.getDescription());
        System.out.println("-----------------------------");
        System.out.println(event.getAlarm());
    }
    public void displayList(List<Event> events){

        if(events == null || events.isEmpty()){
            System.out.println("No events available in the system");
            return;
        }

        int maxEventLength = 0;
        int maxSTLength = 0;
        for(Event event : events){
            maxEventLength = Math.max(maxEventLength, event.getName().length());
            maxSTLength = Math.max(maxSTLength, event.getStart().toString().length());
        }

        int total = maxEventLength + maxSTLength + 12 + 2;

        System.out.printf("Search Result: \n");
        Utilities.lineDrawer(total);

        System.out.printf("   %8s  |  %-" + maxEventLength + "s  |  %-" + maxSTLength + "s%n",
                "ID", "Title", "Start Time");
        Utilities.lineDrawer(total);

        for (Event event : events) {
            System.out.printf("   %8s  |  %-" + maxEventLength + "s  |  %-" + maxSTLength + "s%n",
                    event.getId(), event.getName(), event.getStart());
        }
        Utilities.lineDrawer(total);
    }
    public void displayAlarm(List<Event> events){
        // Get the current date and time in Hong Kong time
//        ZoneId hongKongZone = ZoneId.of("Asia/Hong_Kong");
//        LocalDateTime currentDateTime = LocalDateTime.now(hongKongZone);
//
//        // Format the current date and time
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String formattedDateTime = currentDateTime.format(formatter);
//        String alarm;
//        for(Event event: events){
//            if((alarm=event.getAlarm())!=null) {
//                if(ModelUtilities.compareTimes(event.getEvent_name(),alarm)){
//                    event.setAlarm(null);
//                }
//                else if (ModelUtilities.compareTimes(alarm, formattedDateTime)) {
//                    System.out.println(event.getId() + " | " + event.getEvent_name() + " | " + event.getStart_time());
//                    System.out.println("------------------------------------------------");
//                }
//            }
//            System.out.println();
//        }
    }

    public void displayError(Exception e){
        System.out.println("Error: "+ e.getMessage());
    }
}
