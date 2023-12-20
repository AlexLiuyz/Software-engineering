package org.example.models;

public class Contact extends Entity {
    private String name;
    private String address;
    private String number;

    public Contact (String id, String name,String address, String number){
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public Contact (String name, String address, String number){
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return this.id + "\t"+ this.name + "\t" +this.address + "\t" + this.number;
    }

    public static Contact fromString(String literal) throws Exception{
        String[] tokens = literal.split("\t");
        if(tokens.length == 4){
            return new Contact(tokens[0], tokens[1],tokens[2],tokens[3]);
        }
        else{
            throw new Exception("Invalid format of contact");
        }
    }

    public Contact getCopy(){
        // Implement
        return new Contact(this.id, this.name, this.address,this.number);
    }
}
