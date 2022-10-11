package model;


import model.Bill;

import java.util.ArrayList;

public class Group {
    private ArrayList<Person> persons;
    private ArrayList<Bill> bills;

    public Group() {
        persons = new ArrayList<>();
        bills = new ArrayList<>();
    }

    // Modifies: This
    // Effects: Adds a person to the group
    public void addPerson(String name) {
        Person p = new Person(name, idAssignment());
        persons.add(p);
    }

    // Effects: Returns the value of the nextId for the next person added to the group
    public int idAssignment() {
        int nextId = 0;

        for (Person person : persons) {
            int highest = 0;
            if (person.getId() > highest) {
                highest = person.getId();
            }
            nextId = highest++;
        }
        return nextId;
    }


    // Modifies: This
    // Effects: Adds a person to the group
    public void addBill(Person p, int cost) {
        Bill b = new Bill(p.getId(), cost);
    }
}

