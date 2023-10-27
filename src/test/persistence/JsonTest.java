package persistence;

import model.Transaction;


import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkThingy(String name, int amount, Transaction thingy) {
        assertEquals(name, thingy.getTitle());
        assertEquals(amount, thingy.getAmount());
    }
}
