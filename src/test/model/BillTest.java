package model;

import model.Bill;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BillTest {

    private Bill b1;
    private Bill b2;
    private Bill b3;

    @BeforeEach
    public void runBefore() {
        b1 = new Bill(1, 5, 20, 4);
        b2 = new Bill(3, 20, 24.2, 2);
        b3 = new Bill(101, 21, 169.13, 13);
    }
    @Test
    public void testConstructor() {
        assertEquals(1, b1.getID());
        assertEquals(5, b1.getPersonID());
        assertEquals(4, b1.getNumberOfPeople());
        assertEquals(20, b1.getCost());

        assertEquals(3, b2.getID());
        assertEquals(20, b2.getPersonID());
        assertEquals(2, b2.getNumberOfPeople());
        assertEquals(24.2, b2.getCost());

        assertEquals(101, b3.getID());
        assertEquals(21, b3.getPersonID());
        assertEquals(13, b3.getNumberOfPeople());
        assertEquals(169.13, b3.getCost());
    }

    @Test
    public void testSplitBill() {
        b1 = new Bill(5, 20, 123.44, 2);
        assertEquals(61.72, b1.splitBill());

        b2 = new Bill(20, 5, 69.69, 3);
        assertEquals(23.23, b2.splitBill());

        b3 = new Bill(3, 14, 100, 100);
        assertEquals(1, b3.splitBill());
    }

    @Test
    public void testToJson() {
        JSONObject jsonTest1 = b1.toJson();
        assertEquals(1, jsonTest1.getInt("id"));
        assertEquals(5, jsonTest1.getInt("payee id"));
        assertEquals(4, jsonTest1.getInt("number of people"));
        assertEquals(20, jsonTest1.getDouble("cost"));
        JSONObject jsonTest2 = b2.toJson();
        assertEquals(3, jsonTest2.getInt("id"));
        assertEquals(20, jsonTest2.getInt("payee id"));
        assertEquals(2, jsonTest2.getInt("number of people"));
        assertEquals(24.2, jsonTest2.getDouble("cost"));
        JSONObject jsonTest3 = b3.toJson();
        assertEquals(101, jsonTest3.getInt("id"));
        assertEquals(21, jsonTest3.getInt("payee id"));
        assertEquals(13, jsonTest3.getInt("number of people"));
        assertEquals(169.13, jsonTest3.getDouble("cost"));
    }
}
