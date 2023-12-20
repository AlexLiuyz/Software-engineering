package org.example.models;

import org.example.Errors.ValidationError;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class EventManagerTest {
    EventManager manager;
    LocalDateTime currentDateTime = LocalDateTime.now();
    final String NAME= "testEvents";
    @BeforeEach
    void setUp() {
        this.manager = new EventManager(this.NAME);
    }

    @AfterEach
    void tearDown() {
        // Cleaning process
        File file = new File(this.NAME+".pim");
        file.delete();
    }

    @Test
    void constructor(){
        EventManager _manager = new EventManager();
        assertEquals(_manager.name, "events");
    }
    @Test
    void event_construct(){
        String name = "Testing event2";
        String description="Test descriptions";
        Event event1= new Event("N1",name, description ,currentDateTime.plusMinutes(10));
        Assertions.assertEquals("N1",event1.getId());
        Assertions.assertEquals(name,event1.getName());
        Assertions.assertEquals(currentDateTime.plusMinutes(10),event1.getStart());
        Assertions.assertEquals(description,event1.getDescription());
    }

    @Test
    void SetName(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        String name="haha";
        event.setName(name);
        Assertions.assertEquals(name,event.getName());
    }
    @Test
    void setStart(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        LocalDateTime time=LocalDateTime.parse("2023-12-25T12:30");
        event.setStart(time);
        Assertions.assertEquals(time,event.getStart());
    }

    @Test
    void setDescriptions(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        String description="test d";
        event.setDescription(description);
        Assertions.assertEquals(description,event.getDescription());
    }

    @Test
    void create() {
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        this.manager.create(event);
        Event createdEvent = this.manager.getById(event.getId());

        Assertions.assertEquals("Testing event",createdEvent.getName());
        Assertions.assertEquals("Test descriptions", createdEvent.getDescription());
    }
    @Test
    void getByID(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        this.manager.create(event);
        Assertions.assertEquals(event,manager.getById(event.getId()));
    }
    @Test
    void update_alarm() throws ValidationError {
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        this.manager.create(event);
        manager.updateAlarm("N0", currentDateTime.plusMinutes(6));
        Assertions.assertEquals(currentDateTime.plusMinutes(6), manager.getById("N0").getAlarm());
        manager.updateAlarm("N0",null);
        Assertions.assertEquals(null,this.manager.getById("N0").getAlarm());

        assertThrows(ValidationError.class,()->{manager.updateAlarm("N0",LocalDateTime.parse("2022-01-13T01:30"));});
//        manager.updateAlarm("N0",LocalDateTime.parse("2023-11-23T01:12"));
//        Assertions.assertEquals(LocalDateTime.parse("2023-11-23T01:12"),this.manager.getById("N0").getAlarm());
    }

    @Test
    void delete(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        this.manager.create(event);
        this.manager.delete(event.getId());
        Assertions.assertEquals(null,this.manager.getById(event.getId()));
    }

    @Test
    void getALL(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        List<Event> L=new ArrayList<>();
        L.add(event);
        this.manager.create(event);
        Assertions.assertEquals(L,this.manager.getAll());

        this.manager.delete(event.getId());
        L.clear();
        Assertions.assertEquals(L,this.manager.getAll());
    }
    @Test
    void TobeUpdate(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        this.manager.create(event);
        String name = "Testing event2";
        String description="Test descriptions";
        Event event1= new Event(event.getId(),name, description, currentDateTime.plusMinutes(10));
        this.manager.update(event1);
        Assertions.assertEquals(name,this.manager.getById(event1.getId()).getName());
        Assertions.assertEquals(currentDateTime.plusMinutes(10),this.manager.getById(event1.getId()).getStart());
        Assertions.assertEquals(description,this.manager.getById(event1.getId()).getDescription());
    }
    @Test
    void ToString(){
        Event event= new Event("Testing event", "Test descriptions", LocalDateTime.parse("2023-11-23T01:12"));
        manager.create(event);
        System.out.println(event);
        Assertions.assertEquals("N0\tTesting event\tTest descriptions\t2023-11-23T01:12\tnull",event.toString());
    }
    @Test
    void fromString() throws Exception {
        Event event= new Event("Testing event", "Test descriptions", LocalDateTime.parse("2023-11-23T01:12"));
        manager.create(event);
        Event temp_event=Event.fromString("N0\tTesting event\tTest descriptions\t2023-11-23T01:12\tnull");
        assertEquals(temp_event,event);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            Event.fromString("N0\tTesting event\tTest descriptions\t2023-13-23T01:12");
        });
    }
    @Test
    void search() throws Exception {
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        event.setAlarm(currentDateTime.plusMinutes(5));
        Event event1=new Event("Testing event1","Test descriptions1", currentDateTime.plusMinutes(10));
        Event event2=new Event("Testing event2","Test descriptions2",currentDateTime.plusMinutes(15));
        event1.setAlarm(currentDateTime.plusMinutes(6));
        event2.setAlarm(currentDateTime.plusMinutes(7));
        this.manager.create(event);
        this.manager.create(event1);
        this.manager.create(event2);

        List<Filter>  filters = new LinkedList<>();
        filters.add(new Filter("description", "descriptions1", Operator.CONTAINS));
        List<Event> searchResults = this.manager.search(filters, Connector.AND);
        Assertions.assertEquals(1, searchResults.size());

    }
    @Test
    void equals(){
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        assertEquals(false,event.equals(null));
    }

    @Test
    void save() throws Exception {
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        event.setAlarm(currentDateTime.plusMinutes(5));
        Event event1=new Event("Testing event1","Test descriptions1", currentDateTime.plusMinutes(10));
        Event event2=new Event("Testing event2","Test descriptions2",currentDateTime.plusMinutes(15));
        event1.setAlarm(currentDateTime.plusMinutes(6));
        event2.setAlarm(currentDateTime.plusMinutes(7));
        this.manager.create(event);
        this.manager.create(event1);
        this.manager.create(event2);
        this.manager.save();

        File file = new File(this.manager.getName()+".pim");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Assertions.assertTrue(file.exists());

        String line = reader.readLine();
        assertEquals(3, Integer.parseInt(line));
        reader.close();

    }

    @Test
    void loadAfterSave() throws Exception {
        Event event= new Event("Testing event", "Test descriptions", currentDateTime.plusMinutes(10));
        event.setAlarm(currentDateTime.plusMinutes(5));
        Event event1=new Event("Testing event1","Test descriptions1", currentDateTime.plusMinutes(10));
        Event event2=new Event("Testing event2","Test descriptions2",currentDateTime.plusMinutes(15));
        event1.setAlarm(currentDateTime.plusMinutes(6));
        event2.setAlarm(currentDateTime.plusMinutes(7));
        this.manager.create(event);
        this.manager.create(event1);
        this.manager.create(event2);
        this.manager.save();

        EventManager _manager = new EventManager(this.NAME);
        _manager.load();
        List<Event> events = _manager.getAll();
        assertEquals(3, events.size());

    }
}
