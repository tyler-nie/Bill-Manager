package model;


import java.util.ArrayList;

public class Group {
    private ArrayList<Person> persons;
    private ArrayList<Bill> bills;
    private int personID = 0;
    private int billID = 0;

    // Effects: creates a group with empty list of people and bills
    public Group() {
        persons = new ArrayList<>();
        bills = new ArrayList<>();
    }

    // Modifies: This
    // Effects: Adds a person to persons for the group
    public void addPerson(String name) {
        Person p = new Person(name, personID);
        persons.add(p);
        personID++;
    }

    // Modifies: This
    // Effects: Adds a bill to the list of bills for the group
    public void addBill(Person p, double cost, int num) {
        Bill b = new Bill(billID, p.getID(), num, cost);
        bills.add(b);
        billID++;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public int numberOfPeople() {
        return persons.size();
    }

    public int numberOfBills() {
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
    public boolean isBillInGroup(int id) {
        for (Bill bill: bills) {
            if (bill.getID() == id) {
                return true;
            }
        }
        return false;
    }

    // Requires: id is an id in the list of Bills
    // Effects: Returns the share cost for one person in group who was part of the bill
    // Assumes: only people who took part in said bill uses this function for a specific Bill
    public double billSplit(int id) {
        return bills.get(id).splitBill();
    }
}

