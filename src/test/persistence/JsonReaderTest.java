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

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Group g = reader.read();
            fail("IOException expected");
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGroup() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGroup.json");
        try {
            Group g = reader.read();
            assertEquals("Test Group", g.getName());
            assertEquals(0, g.numberOfPeople());
            assertEquals(0, g.numberOfBills());
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInvalidBillInGroup() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidBillInGroup.json");
        try {
            Group g = reader.read();
            assertEquals("Test Group", g.getName());
            assertEquals(0, g.numberOfPeople());
            assertEquals(0, g.numberOfBills());
            fail("Expected Negative Amount Exception Not Thrown");
        } catch (NegativeAmountException e) {
            // All Good
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGroup.json");
        try {
            Group g = reader.read();
            assertEquals("Cutest Couple Ever", g.getName());
            List<Person> people = g.getPersons();
            assertEquals(2, people.size());
            checkPerson("Tyler", 0, people.get(0));
            checkPerson("Kristelle", 1, people.get(1));

            List<Bill> bills = g.getBills();
            assertEquals(2, bills.size());
            checkBill(0,0,2,25.42, bills.get(0));
            checkBill(1,1,3,20.42, bills.get(1));
        } catch (NegativeAmountException e) {
            fail("Unexpected Negative Amount Exception Thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
