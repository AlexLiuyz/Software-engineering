package org.example.models;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {
    ContactManager manager;
    final String NAME = "testContact";
    @BeforeEach
    void setUp() {
        this.manager = new ContactManager(this.NAME);
    }

    @AfterEach
    void tearDown() {
        File file = new File(this.NAME+".pim");
        file.delete();
    }

    @Test
    void constructor(){
        ContactManager _manager = new ContactManager();
        assertEquals(_manager.name, "contacts");
    }
    @Test
    void create() {
        String name = "ABC";
        String address = "+8527940284";
        String number = "+8527940284";

        Contact contact = new Contact(name, address, number);
        this.manager.create(contact);

        Contact createdContact = this.manager.getById(contact.getId());

        Assertions.assertEquals(name,createdContact.getName());
        Assertions.assertEquals(number, createdContact.getNumber());
    }

    @Test
    void update() {
        String name = "ABCOKDW";
        String address = "fewoweokfewf";
        String number = "+62389429348";

        Contact contact = new Contact(name, address,number);
        this.manager.create(contact);

        String newName = "ANODWLFW";
        String newAddress = "ANODWwefwefLFWfewwef";
        String newNumber = "+62389429342347890234";

        Contact newContact = new Contact(contact.getId(), newName, newAddress, newNumber);
        this.manager.update(newContact);

        Contact updatedContact = this.manager.getById(contact.getId());

        Assertions.assertEquals(newName, updatedContact.getName());
        Assertions.assertEquals(newNumber, updatedContact.getNumber());
    }

    @Test
    void delete() {
        String name = "ABCOKDW";
        String address = "ABCOKDW";
        String number = "+62389429348";

        Contact contact = new Contact(name,address, number);
        this.manager.create(contact);
        this.manager.delete(contact.getId());

        Contact deletedContact = this.manager.getById(contact.getId());
        Assertions.assertNull(deletedContact);
    }

    @Test
    void getAll() {
        this.manager.create(new Contact("ABC","efwef", "+62389429348"));
        this.manager.create(new Contact("ABCOKDW", "weefwefewf","+62389429342347890234"));

        List<Contact> contacts = this.manager.getAll();

        Assertions.assertEquals(2, contacts.size());
    }

    @Test
    void handleSearch(){
        this.manager.create(new Contact("ABC", "ewfwefwef","+62389429348"));
        this.manager.create(new Contact("ABCOKDW", "ewfwefefwe","+62389429342347890234"));

        LinkedList<Filter> filters = new LinkedList();
        filters.add(new Filter("number","62", Operator.CONTAINS));
        List<Contact> results = null;
        try{
            results = this.manager.search(filters, Connector.AND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(2, results.size());

    }

    @Test
    void getById() {
    }
    @Test
    void fromString() throws Exception {
        String name = "ABCOKDW";
        String number = "+62389429348";
        String address = "Hung Hom";


        Contact contact = new Contact(name, address, number);
        this.manager.create(contact);

        Contact temp_contact=Contact.fromString("C0\tABCOKDW\tHung Hom\t+62389429348");
        assertEquals(contact.toString(),temp_contact.toString());
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            Contact.fromString("C0\tTesting event\t2023-13-23T01:12");
        });
    }
    @Test
    void setName(){
        String name = "ABCOKDW";
        String number = "+62389429348";
        String address = "Hung Hom";

        Contact contact = new Contact(name, address, number);
        this.manager.create(contact);
        contact.setName("haha");
        assertEquals("haha",contact.getName());
    }
    @Test
    void setNumber(){
        String name = "ABCOKDW";
        String number = "+62389429348";
        String address = "Hung Hom";


        Contact contact = new Contact(name, address, number);
        this.manager.create(contact);
        contact.setNumber("+62389429960");
        assertEquals("+62389429960",contact.getNumber());
    }
    @Test
    void saveAfterCreate() throws Exception {
        this.manager.create(new Contact("ABC","ewfwefewf", "+62389429348"));
        this.manager.create(new Contact("ABCOKDW", "wefwefwef","+62389429342347890234"));

        this.manager.save();

        File file = new File(this.manager.name + ".pim");
        Assertions.assertTrue(file.exists());
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        assertEquals(2, Integer.parseInt(line));
        reader.close();
    }

    @Test
    void loadAfterSave() throws Exception {
        this.manager.create(new Contact("ABC", "Hung Hom", "+62389429348"));
        this.manager.create(new Contact("ABCOKDW", "Hung Hom", "+62389429342347890234"));

        this.manager.save();

        ContactManager _manager = new ContactManager(this.NAME);
        _manager.load();
        List<Contact> events = _manager.getAll();
        assertEquals(2, events.size());
    }
}