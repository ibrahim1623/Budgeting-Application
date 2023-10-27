package persistence;

import model.Transaction;
import model.TransactionRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Transaction Record from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TransactionRecord read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private TransactionRecord parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TransactionRecord tr = new TransactionRecord(name);
        addTransactions(tr, jsonObject);
        return tr;
    }

    // MODIFIES: tr
    // EFFECTS: parses transactions from JSON object and adds them to Transaction Record
    private void addTransactions(TransactionRecord tr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addTransaction(tr, nextThingy);
        }
    }

    // MODIFIES: tr
    // EFFECTS: parses thingy from JSON object and adds it to Transaction Record
    private void addTransaction(TransactionRecord wr, JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        int amount = jsonObject.getInt("amount");
        Transaction transaction = new Transaction(name, amount);
        wr.addTransaction(transaction);
    }
}
