package org.example.models;

import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NoteManagerTest {
    NoteManager manager;
    final String NAME = "testNotes";
    @BeforeEach
    void setUp() {
        this.manager = new NoteManager(this.NAME);
    }

    @AfterEach
    void tearDown() {
        // Cleaning process
        File file = new File(this.NAME+".pim");
        file.delete();
    }

    @Test
    void constructor(){
        NoteManager _manager = new NoteManager();
        assertEquals(_manager.name, "notes");
    }
    @Test
    void create() {
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        this.manager.create(note);

        Note createdNote = this.manager.getById(note.getId());

        Assertions.assertEquals(title,createdNote.getTitle());
        Assertions.assertEquals(content, createdNote.getContent());
        Assertions.assertTrue(this.manager.checkId("N0"));
    }
    @Test
    void setTitle(){
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        note.setTitle("haha");
        assertEquals("haha",note.getTitle());
    }
    @Test
    void setContent(){
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        note.setContent("haha");
        assertEquals("haha",note.getContent());
    }
    @Test
    void edit() {
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        this.manager.create(note);

        String newTitle = "Updated title";
        String newContent = "Updated content";

        Note newNote = new Note(note.getId(), newTitle, newContent);
        this.manager.update(newNote);

        Note updatedNote = this.manager.getById(note.getId());

        Assertions.assertEquals(newTitle, updatedNote.getTitle());
        Assertions.assertEquals(newContent, updatedNote.getContent());
    }

    @Test
    void delete() {
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        this.manager.create(note);
        this.manager.delete(note.getId());

        Note deletedNote = this.manager.getById(note.getId());
        Assertions.assertNull(deletedNote);
        Assertions.assertFalse(this.manager.checkId("N0"));
    }

    @Test
    void getAll() {
        this.manager.create(new Note("title 1", "Content 1"));
        this.manager.create(new Note("title 2", "Content 2"));

        List<Note> notes = this.manager.getAll();

        Assertions.assertEquals(2, notes.size());
    }

    @Test
    void searchByTitle() throws Exception{
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "Research", Operator.CONTAINS));
        List<Note>  results = this.manager.search(filters, Connector.AND);

        assertEquals(1, results.size());
        assertEquals("Research Findings", results.get(0).getTitle());
    }

    @Test
    void noSuchField() {
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));

        LinkedList<Filter> filters = new LinkedList();
        Exception exception = assertThrows(NoSuchFieldException.class, ()->{
            List<Note>  results = this.manager.findByAttribute("filters", "Research",Operator.CONTAINS);
        });

    }

    @Test
    void searchByContent() throws Exception{
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("content", "the", Operator.CONTAINS));
        List<Note>  results = this.manager.search(filters, Connector.AND);

        assertEquals(2, results.size());
    }
    @Test
    void searchByTitleNotFound() throws Exception{
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "research", Operator.CONTAINS));
        List<Note>  results = this.manager.search(filters, Connector.AND);

        assertEquals(0, results.size());
    }


    @Test
    void searchByTitleAndContent() throws Exception{
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));


        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "Research", Operator.CONTAINS));
        filters.add(new Filter("content", "user", Operator.CONTAINS));

        List<Note>  results = this.manager.search(filters, Connector.AND);

        assertEquals(1, results.size());
        assertEquals("Research Findings", results.get(0).getTitle());
    }

    @Test
    void searchByTitleOrContent() throws Exception{
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("title", "Agenda", Operator.CONTAINS));
        filters.add(new Filter("content", "team members", Operator.CONTAINS));

        List<Note>  results = this.manager.search(filters, Connector.OR);

        assertEquals(2, results.size());
    }

    @Test
    void saveAfterCreate() throws Exception {
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));
        this.manager.save();

        File file = new File(this.manager.getName()+".pim");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Assertions.assertTrue(file.exists());

        String line = reader.readLine();
        assertEquals(3, Integer.parseInt(line));
        reader.close();
    }
    @Test
    void equals(){
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        assertEquals(false,note.equals(null));
    }
    @Test
    void loadEmptyFile() throws Exception {
        this.manager.load();
        List<Note> notes = this.manager.getAll();
        System.out.println(notes.size());
//        System.out.println(notes.get(1));
        assertTrue(notes.isEmpty());

    }

    @Test
    void loadAfterSave() throws Exception {
        this.manager.create(new Note("Meeting Agenda", "Discuss project requirements and timeline with the team."));
        this.manager.create(new Note("Research Findings", "Summarize key findings from the user research conducted."));
        this.manager.create(new Note("Action Items", "List tasks assigned to team members for completion."));
        this.manager.save();

        NoteManager _manager = new NoteManager(this.NAME);
        _manager.load();
        List<Note> notes = _manager.getAll();
        assertEquals(3, notes.size());
    }
    @Test
    void fromString() throws Exception {
        String title = "Testing note";
        String content = "Testing content";

        Note note = new Note(title, content);
        this.manager.create(note);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            Note.fromString("N0\tTesting event\tTest descriptions\t2023-13-23T01:12");
        });
    }
}