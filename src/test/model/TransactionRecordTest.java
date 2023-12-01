package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class TransactionRecordTest {
    private Transaction t1;
    private Transaction t2;
    private TransactionRecord record;

    @BeforeEach
    void runBefore() {
        t1 = new Transaction("grocery", 82);
        t2 = new Transaction("clothing", 90);
        record = new TransactionRecord("transaction record");
    }

    @Test
    void testConstructor() {
        assertEquals(0, record.viewAll().size());
    }

    @Test
    void testAddTransactionOne() {
        ArrayList<Transaction> ts = new ArrayList<>();
        ts.add(t1);
        record.addTransaction(t1);
        assertEquals(1, record.viewAll().size());
        assertEquals(ts, record.viewAll());
        assertEquals(t1, record.viewTransaction(0));
    }

    @Test
    void testAddMultipleTransactions() {
        record.addTransaction(t1);
        record.addTransaction(t2);
        assertEquals(2, record.viewAll().size());
        ArrayList<Transaction> ts = new ArrayList<>();
        ts.add(t1);
        ts.add(t2);
        assertEquals(ts, record.viewAll());
    }

    @Test
    void testViewOneTransaction() {
        record.addTransaction(t1);
        assertEquals(1, record.viewAll().size());
        assertEquals(t1, record.viewTransaction(0));
    }

    @Test
    void testViewMultipleTransaction() {
        record.addTransaction(t1);
        record.addTransaction(t2);
        assertEquals(2, record.viewAll().size());
        assertEquals(t1, record.viewTransaction(0));
        assertEquals(t2, record.viewTransaction(1));
    }

    @Test
    void testEditTransactionOnEmptyList() {
        assertFalse(record.editTransaction(0, "Coffee", 2));
    }

    @Test
    void testEditTransactionSuccessful() {
        record.addTransaction(t1);
        record.addTransaction(t2);
        assertEquals(t1, record.viewTransaction(0));
        assertTrue(record.editTransaction(0, "games", 25));
        assertEquals("games", record.viewTransaction(0).getTitle());
        assertEquals(25, record.viewTransaction(0).getAmount());
    }

    @Test
    void testCalculateAverage() {
        record.addTransaction(t1);
        record.addTransaction(t2);
        assertEquals(2, record.viewAll().size());
        assertEquals(t1, record.viewTransaction(0));
        assertEquals(t2, record.viewTransaction(1));
        assertEquals((82+90)/2, record.calculateAverage());
    }
}
