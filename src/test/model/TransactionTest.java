package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private Transaction testtransaction;

    @BeforeEach
    void runBefore() {testtransaction = new Transaction("grocery", 82);}

    @Test
    void testConstructor() {
        assertEquals("grocery", testtransaction.getTitle());
        assertEquals(82, testtransaction.getAmount());
    }
}