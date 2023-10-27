package persistence;

import model.TransactionRecord;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            TransactionRecord tr = new TransactionRecord("My Transaction Record");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTransactionRecord() {
        try {
            TransactionRecord tr = new TransactionRecord("My Transaction Record");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(tr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            tr = reader.read();
            assertEquals("My Transaction Record", tr.getName());
            assertEquals(0, tr.viewAll().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTransactionRecord() {
        try {
            TransactionRecord tr = new TransactionRecord("My Transaction Record");
            tr.addTransaction(new Transaction("grocery", 100));
            tr.addTransaction(new Transaction("clothes", 25));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTransactionRecord.json");
            writer.open();
            writer.write(tr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTransactionRecord.json");
            tr = reader.read();
            assertEquals("My Transaction Record", tr.getName());
            List<Transaction> transactions = tr.viewAll();
            assertEquals(2, transactions.size());
            checkTransaction("grocery", 100, transactions.get(0));
            checkTransaction("clothes", 25, transactions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}