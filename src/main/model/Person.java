package model;

import model.Bill;
import org.json.JSONObject;
import persistence.Writable;

public class Person implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }

}
