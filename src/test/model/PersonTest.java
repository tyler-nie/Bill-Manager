package model;

import model.Person;
import org.json.JSONObject;
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
    public void testConstructor() {
        assertEquals("Tyler", p1.getName());
        assertEquals(5, p1.getID());
        assertEquals("Kristelle", p2.getName());
        assertEquals(20, p2.getID());
    }

    @Test
    public void testToJson() {
        JSONObject jsonTest1 = p1.toJson();
        assertEquals("Tyler", jsonTest1.getString("name"));
        JSONObject jsonTest2 = p2.toJson();
        assertEquals("Kristelle", jsonTest2.getString("name"));
    }
}
