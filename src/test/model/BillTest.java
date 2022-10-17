package model;

import model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BillTest {

    private Bill b1;
    private Bill b2;
    private Bill b3;

    @BeforeEach
    public void runBefore() {
        b1 = new Bill(1, 5, 4, 20);
        b2 = new Bill(3, 20, 2, 24.2);
        b3 = new Bill(101, 21, 13, 169.13);
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
        b1 = new Bill(5, 20, 2, 123.44);
        assertEquals(61.72, b1.splitBill());

        b2 = new Bill(20, 5, 3, 69.69);
        assertEquals(23.23, b2.splitBill());

        b3 = new Bill(3, 14, 100, 100);
        assertEquals(1, b3.splitBill());
    }
}
