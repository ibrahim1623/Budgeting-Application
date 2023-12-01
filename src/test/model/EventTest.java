package model;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventConstructor() {
        Event event = new Event("Test Event");
        assertNotNull(event.getDate());
        assertEquals("Test Event", event.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 1"); // Same description but different object

        assertTrue(event1.equals(event2));
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void testNotEquals() {
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 2");

        assertFalse(event1.equals(event2));
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void testToString() {
        Event event = new Event("Test Event");
        String expectedString = event.getDate().toString() + "\n" + "Test Event";
        assertEquals(expectedString, event.toString());
    }
}