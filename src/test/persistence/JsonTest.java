package persistence;

import model.Group;
import model.Bill;
import model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPerson(String name, int id, Person p) {
        assertEquals(name, p.getName());
        assertEquals(id, p.getID());
    }

    protected void checkBill(int id, int payeeId, int numOfPeople, double cost, Bill b) {
        assertEquals(id, b.getID());
        assertEquals(payeeId, b.getPersonID());
        assertEquals(numOfPeople, b.getNumberOfPeople());
        assertEquals(cost, b.getCost());
    }
}
