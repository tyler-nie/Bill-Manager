package persistence;

import exceptions.NegativeAmountException;
import model.Group;
import model.Bill;
import model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            Group g = new Group("Group 1");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGroup() {
        try {
            Group g = new Group("Test Group");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            g = reader.read();
            assertEquals("Test Group", g.getName());
            assertEquals(0, g.numberOfPeople());
            assertEquals(0, g.numberOfBills());
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Group g = new Group("Cute Couple");
            g.addPerson("Tyler");
            g.addPerson("Kristelle");

            try {
                g.addBill(g.getPersons().get(0), 20.42, 2);
            } catch (NegativeAmountException e) {
                fail("Unexpected Negative Amount Exception Thrown");
            }

            try {
                g.addBill(g.getPersons().get(1), 20.50, 3);
            } catch (NegativeAmountException e) {
                fail("Unexpected Negative Amount Exception Thrown");
            }

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGroup.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGroup.json");
            g = reader.read();
            assertEquals("Cute Couple", g.getName());
            List<Person> people = g.getPersons();
            assertEquals(2, people.size());
            checkPerson("Tyler", 0, people.get(0));
            checkPerson("Kristelle", 1, people.get(1));

            List<Bill> bills = g.getBills();
            assertEquals(2, bills.size());
            checkBill(0,0,2,20.42, bills.get(0));
            checkBill(1,1,3,20.50, bills.get(1));
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown\"");
        }
    }


}
