package model;

import exceptions.InvalidIdException;
import exceptions.NegativeAmountException;
import model.Group;
import model.Person;
import model.Bill;

import org.json.JSONArray;
import org.json.JSONObject;
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
        g2 = new Group("Cute couple");
        g3 = new Group("Blackpink");
        g4 = new Group("CLC");
    }

    @Test
    public void testConstructor() {
        assertEquals("No name", g1.getName());
        assertEquals(0, g1.numberOfPeople());
        assertEquals(0, g1.numberOfBills());
        assertEquals("Cute couple", g2.getName());
        assertEquals(0, g2.numberOfPeople());
        assertEquals(0, g2.numberOfBills());
        assertEquals("Blackpink", g3.getName());
        assertEquals(0, g3.numberOfPeople());
        assertEquals(0, g3.numberOfBills());
        assertEquals("CLC", g4.getName());
        assertEquals(0, g4.numberOfPeople());
        assertEquals(0, g4.numberOfBills());
    }

    @Test
    public void testRename() {
        g1.rename("Cute couple");
        String rename = g1.getName();
        assertEquals("Cute couple",  rename);
    }

    @Test
    public void testAddPersonOnePerson() {

        //Check for 1 person
        g1.addPerson("Jaime");
        assertEquals(1, g1.numberOfPeople());
        ArrayList<Person> persons1 = g1.getPersons();
        assertEquals("Jaime", persons1.get(0).getName());
        assertEquals(0, persons1.get(0).getID());

    }
    @Test
    public void testAddPersonTwoPerson() {

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
    public void testAddPersonNumerous() {
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
    public void testAddBillOneBill() {
        Person p = new Person("Scott", 30);
        try {
            g1.addBill(p, 23.44, 2);
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        }

        assertEquals(1, g1.numberOfBills());

        ArrayList<Bill> bills = g1.getBills();

        try {
            assertEquals(0, g1.getBill(0).getID());
            assertEquals(30, g1.getBill(0).getPersonID());
            assertEquals(2, g1.getBill(0).getNumberOfPeople());
            assertEquals(23.44, g1.getBill(0).getCost());
        } catch (InvalidIdException e) {
            fail("Unexpected Invalid Id Exception Thrown");
        }
    }

    @Test
    public void testAddNegativeValueBill() {
        Person p1 = new Person("Tyler", 5);
        Person p2 = new Person("Kristelle", 20);

        try {
            g1.addBill(p1, -320.514, 2);
            fail("Expected negative amount exception not thrown");
        } catch (NegativeAmountException e) {
            // Success
            System.out.println("Negative value entered");
        }
    }

    @Test
    public void testAddBillTwoBills() {

        Person p1 = new Person("Tyler", 5);
        Person p2 = new Person("Kristelle", 20);
        try {
            g2.addBill(p1, 104.95, 2);
            g2.addBill(p2, 77.18, 2);
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        }

        assertEquals(2, g2.numberOfBills());

        ArrayList<Bill> bills = g2.getBills();
        try {
            assertEquals(0, g2.getBill(0).getID());
            assertEquals(5, g2.getBill(0).getPersonID());
            assertEquals(2, g2.getBill(0).getNumberOfPeople());
            assertEquals(104.95, g2.getBill(0).getCost());

            assertEquals(1, g2.getBill(1).getID());
            assertEquals(20, g2.getBill(1).getPersonID());
            assertEquals(2, g2.getBill(1).getNumberOfPeople());
            assertEquals(77.18, g2.getBill(1).getCost());
        } catch (InvalidIdException e) {
            fail("Unexpected Invalid Id exception thrown");
        }
    }

    @Test
    public void testAddBillNumerousBills() {

        // For larger groups
        for (int i = 0; i < 100; i++) {
            Person p = new Person(Integer.toString(i), i);
            try {
                g3.addBill(p, i+10, 4);
            } catch (NegativeAmountException e) {
                fail("Unexpected Negative Amount Exception Thrown");
            }
        }
        assertEquals(100, g3.numberOfBills());

        // Checks if ID's are assigned correctly
        for (int j = 0; j < g3.numberOfBills(); j++) {
            try {
                assertTrue(g3.isBillInGroup(j));
            } catch (InvalidIdException e) {
                fail("Unexpected Invalid Id exception thrown");
            }
        }
    }

    @Test
    public void testGetBillInvalidId() {
        try {
            Bill b = g1.getBill(3);
            fail("Expected Exception not thrown");
        } catch (InvalidIdException e) {
            // All good!
        }
    }

    @Test
    public void testIsPersonInGroup() {

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
    public void testIsBillInGroupValid() {

        for (int i = 0; i < 10; i++) {
            Person p = new Person(Integer.toString(i), i);
            try {
                g4.addBill(p, i+10, 4);
            } catch (NegativeAmountException e) {
                fail("Unexpected Negative Amount Exception Thrown");
            }
        }

        for (int j = 0; j < g4.numberOfBills(); j++) {
            try {
                g4.isBillInGroup(j);
            } catch (InvalidIdException e) {
                fail("Unexpected Invalid Id exception thrown");
            }
        }
    }

    @Test
    public void testIsBillInGroupValidInvalidID() {
        try {
            g4.isBillInGroup(20);
            fail("expected Invalid Id Exception not thrown");
        } catch (InvalidIdException e) {
            // All good
        }
    }

    @Test
    public void testBillSplit() {
        Person p1 = new Person("Tyler", 5);
        Person p2 = new Person("Kristelle", 20);
        try {
            g2.addBill(p1, 104.96, 2);
            g2.addBill(p2, 77.18, 2);
            g2.addBill(p1, 20.14, 2);
            g2.addBill(p1, 99.99, 9);
            g2.addBill(p2, 50.43, 0);
            g2.addBill(p2, 0.00, 4);
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        }
        assertEquals(52.48, g2.billSplit(0));
        assertEquals(38.59, g2.billSplit(1));
        assertEquals(10.07, g2.billSplit(2));
        assertEquals(11.11, g2.billSplit(3));
    }

    @Test
    public void testToJson() {
        JSONObject jsonTest1 = g1.toJson();
        JSONArray people1 = jsonTest1.getJSONArray("people");
        JSONArray bills1 = jsonTest1.getJSONArray("bills");
        assertEquals("No name", jsonTest1.getString("name"));
        assertEquals(0, people1.length());
        assertEquals(0, bills1.length());

        JSONObject jsonTest2 = g2.toJson();
        JSONArray people2 = jsonTest2.getJSONArray("people");
        JSONArray bills2 = jsonTest2.getJSONArray("bills");
        assertEquals("Cute couple", jsonTest2.getString("name"));
        assertEquals(0, people1.length());
        assertEquals(0, bills1.length());

        JSONObject jsonTest3 = g3.toJson();
        JSONArray people3 = jsonTest3.getJSONArray("people");
        JSONArray bills3 = jsonTest3.getJSONArray("bills");
        assertEquals("Blackpink", jsonTest3.getString("name"));
        assertEquals(0, people1.length());
        assertEquals(0, bills1.length());

        JSONObject jsonTest4 = g4.toJson();
        JSONArray people4 = jsonTest4.getJSONArray("people");
        JSONArray bills4 = jsonTest4.getJSONArray("bills");
        assertEquals("CLC", jsonTest4.getString("name"));
        assertEquals(0, people1.length());
        assertEquals(0, bills1.length());
    }
}
