package model;

import model.Group;
import model.Person;
import model.Bill;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

class GroupTest {
    private Group g1;
    private Group g2;
    private Group g3;
    private Group g4;

    @BeforeEach
    public void runBefore() {
        g1 = new Group();
        g2 = new Group();
        g3 = new Group();
        g4 = new Group();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, g1.numberOfPeople());
        assertEquals(0, g1.numberOfBills());
        assertEquals(0, g2.numberOfPeople());
        assertEquals(0, g2.numberOfBills());
        assertEquals(0, g3.numberOfPeople());
        assertEquals(0, g3.numberOfBills());
        assertEquals(0, g4.numberOfPeople());
        assertEquals(0, g4.numberOfBills());
    }

    @Test
    public void addPersonTestOnePerson() {

        //Check for 1 person
        g1.addPerson("Jaime");
        assertEquals(1, g1.numberOfPeople());
        ArrayList<Person> persons1 = g1.getPersons();
        assertEquals("Jaime", persons1.get(0).getName());
        assertEquals(0, persons1.get(0).getID());

    }
    @Test
    public void addPersonTestTwoPerson() {

        // Check for entire list of 2
        g2.addPerson("Tyler");
        g2.addPerson("Kristelle");
        assertEquals(2, g2.numberOfPeople());

        ArrayList<Person> persons2 = g2.getPersons();

        assertEquals("Tyler", persons2.get(0).getName());
        assertEquals("Kristelle", persons2.get(1).getName());
        assertEquals(0, persons2.get(0).getID());
        assertEquals(1, persons2.get(1).getID());

    }
    @Test
    public void addPersonTestNumerous() {
        // Checking all for Persons > 2
        g3.addPerson("Jacob");
        g3.addPerson("Jeremy");
        g3.addPerson("Dennis");
        assertEquals(3, g3.numberOfPeople());

        ArrayList<Person> persons3 = g3.getPersons();
        assertEquals("Jacob", persons3.get(0).getName());
        assertEquals("Jeremy", persons3.get(1).getName());
        assertEquals("Dennis", persons3.get(2).getName());

        // Checks if ID's are assigned correctly
        for (int i = 0; i < g3.numberOfPeople(); i++) {
            assertTrue(g3.isPersonInGroup(i));
        }

        // For larger groups
        for (int i = 0; i < 100; i++) {
            g4.addPerson(Integer.toString(i));
        }
        assertEquals(100, g4.numberOfPeople());

        // Checks if ID's are assigned correctly
        for (int j = 0; j < g4.numberOfPeople(); j++) {
            assertTrue(g4.isPersonInGroup(j));
        }
    }

    @Test
    public void addBillTestOneBill() {
        Person p = new Person("Scott", 30);
        g1.addBill(p, 23.44, 2);

        assertEquals(1, g1.numberOfBills());

        ArrayList<Bill> bills = g1.getBills();

        assertEquals(0, bills.get(0).getID());
        assertEquals(30, bills.get(0).getPersonID());
        assertEquals(2, bills.get(0).getNumberOfPeople());
        assertEquals(23.44, bills.get(0).getCost());
    }

    @Test
    public void addBillTestTwoBills() {

        Person p1 = new Person("Tyler", 5);
        Person p2 = new Person("Kristelle", 20);
        g2.addBill(p1, 104.95, 2);
        g2.addBill(p2, 77.18, 2);

        assertEquals(2, g2.numberOfBills());

        ArrayList<Bill> bills = g2.getBills();

        assertEquals(0, bills.get(0).getID());
        assertEquals(5, bills.get(0).getPersonID());
        assertEquals(2, bills.get(0).getNumberOfPeople());
        assertEquals(104.95, bills.get(0).getCost());

        assertEquals(1, bills.get(01).getID());
        assertEquals(20, bills.get(1).getPersonID());
        assertEquals(2, bills.get(1).getNumberOfPeople());
        assertEquals(77.18, bills.get(1).getCost());

    }

    @Test
    public void addBillTestNumerousBills() {

        // For larger groups
        for (int i = 0; i < 100; i++) {
            Person p = new Person(Integer.toString(i), i);
            g3.addBill(p, i+10, 4);
        }
        assertEquals(100, g3.numberOfBills());

        // Checks if ID's are assigned correctly
        for (int j = 0; j < g3.numberOfBills(); j++) {
            assertTrue(g3.isBillInGroup(j));
        }

    }

    @Test
    public void isPersonInGroupTest() {

        for (int i = 0; i < 10; i++) {
            g4.addPerson(Integer.toString(i));
        }

        for (int j = 0; j < g4.numberOfPeople(); j++) {
            assertTrue(g4.isPersonInGroup(j));
        }
        assertFalse(g4.isPersonInGroup(10));
        assertFalse(g4.isPersonInGroup(100));

    }

    @Test
    public void isBillInGroupTest() {

        for (int i = 0; i < 10; i++) {
            Person p = new Person(Integer.toString(i), i);
            g4.addBill(p, i+10, 4);
        }

        for (int j = 0; j < g4.numberOfBills(); j++) {
            assertTrue(g4.isBillInGroup(j));
        }
        assertFalse(g4.isBillInGroup(10));
        assertFalse(g4.isBillInGroup(100));

    }

    @Test
    public void billSplitTest() {
        Person p1 = new Person("Tyler", 5);
        Person p2 = new Person("Kristelle", 20);
        g2.addBill(p1, 104.96, 2);
        g2.addBill(p2, 77.18, 2);
        g2.addBill(p1, 20.14, 2);
        g2.addBill(p1, 99.99, 9);

        assertEquals(52.48, g2.billSplit(0));
        assertEquals(38.59, g2.billSplit(1));
        assertEquals(10.07, g2.billSplit(2));
        assertEquals(11.11, g2.billSplit(3));
    }
}