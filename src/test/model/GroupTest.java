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
    public void addPersonTest() {
        //Check for empty
        assertEquals(0, g1.numberOfPeople());

        g1.addPerson("Jaime");
        assertEquals(1, g1.numberOfPeople());
        ArrayList<Person> persons1 = g1.getPersons();
        assertEquals("Jaime", persons1.get(0).getName());
        assertEquals(0, persons1.get(0).getID());

        g2.addPerson("Tyler");
        g2.addPerson("Kristelle");
        assertEquals(2, g2.numberOfPeople());

        ArrayList<Person> persons2 = g2.getPersons();

        // Check all for 2
        assertEquals("Tyler", persons2.get(0).getName());
        assertEquals("Kristelle", persons2.get(1).getName());
        assertEquals(0, persons2.get(0).getID());
        assertEquals(1, persons2.get(1).getID());

        g3.addPerson("Jacob");
        g3.addPerson("Jeremy");
        g3.addPerson("Dennis");
        assertEquals(3, g3.numberOfPeople());

        // Checking all for Persons > 2
        ArrayList<Person> persons3 = g3.getPersons();
        assertEquals("Jacob", persons3.get(0).getName());
        assertEquals("Jeremy", persons3.get(1).getName());
        assertEquals("Dennis", persons3.get(2).getName());
        assertEquals(0, persons3.get(0).getID());
        assertEquals(1, persons3.get(1).getID());
        assertEquals(2, persons3.get(2).getID());

        g4.addPerson("Dummy");
        g4.addPerson("Stupid");
        g4.addPerson("Love");
        g4.addPerson("Honey");
        g4.addPerson("Sweetheart");
        g4.addPerson("Honeybuns");
        g4.addPerson("My Love");
        g4.addPerson("Mi Amor");
        assertEquals(8, g4.numberOfPeople());

        ArrayList<Person> persons4 = g4.getPersons();

        //Check for large lists (Only need Edges and 1 Middle cause previous test satisfies all in a list
        assertEquals("Dummy", persons4.get(0).getName());
        assertEquals("Honey", persons4.get(3).getName());
        assertEquals("Mi Amor", persons4.get(7).getName());
        assertEquals(0, persons4.get(0).getID());
        assertEquals(3, persons4.get(3).getID());
        assertEquals(7, persons4.get(7).getID());

    }

    @Test
    public void addBillTest() {

    }

    @Test
    public void billSplitTest() {

    }
}