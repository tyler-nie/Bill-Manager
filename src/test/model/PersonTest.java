package model;

import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person p1;
    private Person p2;

    @BeforeEach
    public void runBefore() {
        p1 = new Person("Tyler", 5);
        p2 = new Person("Kristelle", 20);
    }
    @Test
    public void constructorTest() {
        assertEquals("Tyler", p1.getName());
        assertEquals(5, p1.getID());
        assertEquals("Kristelle", p2.getName());
        assertEquals(20, p2.getID());
    }
}
