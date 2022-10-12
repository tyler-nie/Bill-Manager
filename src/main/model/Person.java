package model;

import model.Bill;

public class Person {
    private String name;
    private int id;

    // Effects: creates a new person with name and an ID
    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

}
