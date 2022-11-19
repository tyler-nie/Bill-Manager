package model;


import exceptions.InvalidIdException;
import exceptions.NegativeAmountException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Group implements Writable {
    private String name;
    private ArrayList<Person> persons;
    private ArrayList<Bill> bills;
    private int personID = 0;
    private int billID = 0;

    // Effects: creates a group with empty list of people and bills and No name
    public Group() {
        name = "No name";
        persons = new ArrayList<>();
        bills = new ArrayList<>();
    }

    //Effects: creates a group with empty list of people and bills with given Name
    public Group(String name) {
        this.name = name;
        persons = new ArrayList<>();
        bills = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Modifies: This
    // Effects: Renames the group
    public void rename(String name) {
        this.name = name;
    }

    // Modifies: This
    // Effects: Adds a person to persons for the group
    public void addPerson(String name) {
        Person p = new Person(name, personID);
        EventLog.getInstance().logEvent(new Event("Added person " + p.getName() + " to group."));
        persons.add(p);
        personID++;
    }

    // Modifies: This
    // Effects: Adds a bill to the list of bills for the group
    public void addBill(Person p, double cost, int num) throws NegativeAmountException {
        if (!validCost(cost)) {
            throw new NegativeAmountException();
        }
        Bill b = new Bill(billID, p.getID(), cost, num);
        EventLog.getInstance().logEvent(new Event("Added bill with id" + b.getID() + " to group."));
        bills.add(b);
        billID++;
    }

    public boolean validCost(double cost) throws NegativeAmountException {
        if (cost >= 0) {
            return true;
        } else {
            throw new NegativeAmountException();
        }
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public Bill getBill(int id) throws InvalidIdException {
        if (!isBillInGroup(id)) {
            throw new InvalidIdException();
        }
        return bills.get(id);
    }

    public int numberOfPeople() {
        EventLog.getInstance().logEvent(new Event("Counted " + persons.size() + " people in group"));
        return persons.size();
    }

    public int numberOfBills() {
        EventLog.getInstance().logEvent(new Event("Counted " + bills.size() + " bills in group"));
        return bills.size();
    }

    // Effects: Checks if a person in the group has the certain ID
    public boolean isPersonInGroup(int id) {
        for (Person person: persons) {
            if (person.getID() == id) {
                return true;
            }
        }
        return false;
    }

    // Effects: Checks if a person in the group has the certain ID
    public boolean isBillInGroup(int id) throws InvalidIdException {
        for (Bill bill: bills) {
            if (bill.getID() == id) {
                return true;
            }
        }
        throw new InvalidIdException();
    }

    // Requires: id is an id in the list of Bills
    // Effects: Returns the share cost for one person in group who was part of the bill
    // Assumes: only people who took part in said bill uses this function for a specific Bill
    public double billSplit(int id) {
        return bills.get(id).splitBill();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("people", personsInGroupToJson());
        json.put("bills", billsInGroupToJson());
        return json;
    }

    // EFFECTS: returns persons in this group as a JSON array
    private JSONArray personsInGroupToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Person p : persons) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns bills in this group as a JSON array
    private JSONArray billsInGroupToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bill b : bills) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}



