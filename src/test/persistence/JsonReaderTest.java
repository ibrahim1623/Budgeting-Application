package persistence;

import model.TransactionRecord;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTransactionRecord() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTransactionRecord.json");
        try {
            TransactionRecord tr = reader.read();
            assertEquals("My Transaction Record", tr.getName());
            assertEquals(0, tr.viewAll().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTransactionRecord() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTransactionRecord.json");
        try {
            TransactionRecord tr = reader.read();
            assertEquals("My Transaction Record", tr.getName());
            List<Transaction> transactions = tr.viewAll();
            assertEquals(2, transactions.size());
            checkTransaction("grocery", 100, transactions.get(0));
            checkTransaction("clothes", 25, transactions.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}