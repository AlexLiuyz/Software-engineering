package org.example.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerTest {
    TaskManager manager;
    final String NAME="testTasks";
    @BeforeEach
    void setUp() {
        this.manager = new TaskManager(this.NAME);
    }

    @AfterEach
    void tearDown() {
        File file = new File(this.NAME+".pim");
        file.delete();
    }
    @Test
    void Constructor(){
        TaskManager _manager=new TaskManager();
        assertEquals(1,_manager.getCount());
        assertEquals(Task.class, _manager.getSchema());
        assertEquals("tasks",_manager.getName());
    }
    @Test
    void create() {
        String title = "3211_project";
        String content = "The task testing";
        LocalDateTime deadline = LocalDateTime.parse("2023-11-25T10:15:30");

        Task task = new Task(title, content, deadline);
        this.manager.create(task);
        Task createdTask = this.manager.getById(task.getId());

        Assertions.assertEquals(title,createdTask.getTitle());
        Assertions.assertEquals(content, createdTask.getDescription());
        Assertions.assertEquals(deadline, createdTask.getDeadline());
    }
    @Test
    void setDescription(){
        String title = "3211_project";
        String content = "The task testing";
        LocalDateTime deadline = LocalDateTime.parse("2023-11-25T10:15:30");

        Task task = new Task(title, content, deadline);
        this.manager.create(task);
        task.setDescription("haha");
        assertEquals("haha",task.getDescription());
    }
    @Test
    void setDeadline(){
        String title = "3211_project";
        String content = "The task testing";
        LocalDateTime deadline = LocalDateTime.parse("2023-11-25T10:15:30");
        LocalDateTime new_deadline = LocalDateTime.parse("2023-11-25T10:15:33");
        Task task = new Task(title, content, deadline);
        this.manager.create(task);
        task.setDeadline(new_deadline);
        assertEquals(new_deadline,task.getDeadline());
    }
    @Test
    void setTitle(){
        String title = "3211_project";
        String content = "The task testing";
        LocalDateTime deadline = LocalDateTime.parse("2023-11-25T10:15:30");
        Task task = new Task(title, content, deadline);
        this.manager.create(task);
        task.setTitle("haha");
        assertEquals("haha",task.getTitle());
    }
    @Test
    void update() {
        String title = "3211_big_project";
        String content = "The tasks are now testing";
        LocalDateTime deadline = LocalDateTime.parse("2023-11-26T10:15:30");

        Task task = new Task(title, content, deadline);
        this.manager.create(task);

        String newTitle = "3211_the_project";
        String newContent = "The tasks are now in the test file";
        LocalDateTime newDeadline = LocalDateTime.parse("2023-11-27T10:15:30");

        Task newTask = new Task(task.getId(), newTitle, newContent, newDeadline);
        this.manager.update(newTask);

        Task updatedTask = this.manager.getById(task.getId());

        Assertions.assertEquals(newTitle, updatedTask.getTitle());
        Assertions.assertEquals(newContent, updatedTask.getDescription());
        Assertions.assertEquals(newDeadline, updatedTask.getDeadline());
    }

    @Test
    void delete() {
        String title = "3211_big_project";
        String content = "The tasks are now testing";
        LocalDateTime end = LocalDateTime.parse("2023-11-26T10:15:30");

        Task task = new Task(title, content, end);
        this.manager.create(task);
        this.manager.delete(task.getId());

        Task deletedTask = this.manager.getById(task.getId());
        Assertions.assertNull(deletedTask);
    }
    @Test
    void fromString() throws Exception {
        String title = "3211_big_project";
        String content = "The tasks are now testing";
        LocalDateTime end = LocalDateTime.parse("2023-11-26T10:15:30");

        Task task = new Task(title, content, end);
        this.manager.create(task);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            Task.fromString("N0\tTest descriptions\t2023-13-23T01:12");
        });
    }
    @Test
    void getAll() {
        LocalDateTime start = LocalDateTime.parse("2023-11-15T10:15:30");
        LocalDateTime end = LocalDateTime.parse("2023-11-26T10:15:30");
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));

        List<Task> contacts = this.manager.getAll();

        Assertions.assertEquals(2, contacts.size());
    }

    @Test
    void searchByTitle(){
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title","big", Operator.CONTAINS));
        List<Task> results = null;
        try{
            results = this.manager.search(filters, Connector.AND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(1, results.size());
        assertEquals("3211_big_project", results.get(0).getTitle());
    }

    @Test
    void searchByTitleNotFound() throws Exception{
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "research", Operator.CONTAINS));
        List<Task>  results = this.manager.search(filters, Connector.AND);

        assertEquals(0, results.size());
    }

    @Test
    void searchByContent() throws Exception{
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("description", "file", Operator.CONTAINS));
        List<Task>  results = this.manager.search(filters, Connector.AND);

        assertEquals(1, results.size());
    }

    @Test
    void searchByTitleAndContent() throws Exception{
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));


        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "3211", Operator.CONTAINS));
        filters.add(new Filter("description", "file", Operator.CONTAINS));

        List<Task>  results = this.manager.search(filters, Connector.AND);

        assertEquals(1, results.size());
        assertEquals("3211_the_project", results.get(0).getTitle());
    }

    @Test
    void searchByTitleOrContent() throws Exception{
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));


        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "big", Operator.CONTAINS));
        filters.add(new Filter("description", "file", Operator.CONTAINS));

        List<Task>  results = this.manager.search(filters, Connector.OR);

        assertEquals(2, results.size());
    }

    @Test
    void saveAfterCreate() throws Exception {
        this.manager.create(new Task("3211_project", "The task testing", LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));

        this.manager.save();

        File file = new File(this.manager.name + ".pim");
        Assertions.assertTrue(file.exists());
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        assertEquals(4, Integer.parseInt(line));
        reader.close();
    }

    @Test
    void loadEmptyFile() throws Exception {
        this.manager.load();
        List<Task> notes = this.manager.getAll();
        assertTrue(notes.isEmpty());
    }

    @Test
    void loadAfterSave() throws Exception {
        this.manager.create(new Task("3211_project", "The task testing",LocalDateTime.parse("2023-11-26T10:15:30")));
        this.manager.create(new Task("3211_big_project", "The tasks are now testing", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.create(new Task("3211_the_project", "The tasks are now in the test file", LocalDateTime.parse("2023-11-27T10:15:30")));
        this.manager.save();

        TaskManager _manager = new TaskManager(this.NAME);
        _manager.load();
        List<Task> tasks = _manager.getAll();
        assertEquals(3, tasks.size());
    }
}