package model;


import model.Bill;

import java.lang.reflect.Array;
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
    // Effects: Adds a person to the group
    public void addPerson(String name) {
        Person p = new Person(name, personID);
        persons.add(p);
        personID++;
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

//    // Effects: Returns the value of the nextId for the next person added to the group
//    public int idAssignmentPerson() {
//        int nextId = 0;
//
//        for (Person person : persons) {
//            int highest = 0;
//            if (person.getId() > highest) {
//                highest = person.getId();
//            }
//            nextId = highest++;
//        }
//        return nextId;
//    }

    // Modifies: This
    // Effects: Adds a bill to the list of bills for the group
    public void addBill(Person p, double cost, int num) {
        Bill b = new Bill(billID, p.getID(), num, cost);
        bills.add(b);
        billID++;
    }

    // Effects: Returns the value of the nextId for the next person added to the group
//    public int idAssignmentBill() {
//        int nextId = 0;
//
//        for (Bill bill : bills) {
//            int highest = 0;
//            if (bill.getId() > highest) {
//                highest = bill.getId();
//            }
//            nextId = highest++;
//        }
//        return nextId;
//    }

    // Requires: id is in List of bills
    // Modifies: This
    // Effects: Adds a bill to the list of bills for the group
    // Assumes: only people who took part in said bill has the ID of said bill
    public double billSplit(int id) {
        double share = 0;
        for (Bill bill: bills) {
            if (bill.getID() == id) {
                share = bill.getCost() / bill.getNumberOfPeople();
            }
        }
        return share;
    }
}

