package org.example.models;

public class Note extends Entity{
    private String title;
    private String content;

    public Note(String id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Note( String title, String content){
        this.title = title;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public boolean equals(Object note){
        if(note instanceof Note){
            return this.id.equals(((Note) note).getId());
        }
        return false;
    }

    @Override
    public String toString(){
        return this.id + "\t"+ this.title + "\t" + this.content;
    }

    public static Note fromString(String literal) throws Exception{
        String[] tokens = literal.split("\t");
        Note note;
        if(tokens.length == 3){
            return new Note(tokens[0], tokens[1], tokens[2]);
        }
        else{
            throw new Exception("Invalid format of note");
        }
    }

    @Override
    public Note getCopy() {
        return new Note(this.id, this.title, this.content);
    }

}

