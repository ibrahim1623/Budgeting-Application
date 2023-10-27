package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// maintains a record of all transactions
public class TransactionRecord implements Writable {
    private final ArrayList<Transaction> transactions; // List of all transactions
    private String name; // Name of transaction


    // MODIFIES: this
    // EFFECTS: creates a record of transactions with an empty list of transactions
    public TransactionRecord(String name) {
        this.transactions = new ArrayList<>();
        this.name = name;
    }

    // EFFECT: get method for transaction record
    public String getName() {
        return name;
    }

    // MODIFIES: transaction
    // EFFECTS: adds a new transaction to array list of transactions
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    // EFFECTS: returns a list of all transactions
    public ArrayList<Transaction> viewAll() {
        return transactions;
    }

    // REQUIRES: 0 <= index < size of array list
    // EFFECTS: returns the transaction at the specified index
    public Transaction viewTransaction(int index) {
        return transactions.get(index);
    }

    // REQUIRES: 0 <= index < size of array list, title must be a string of non-zero
    //           length, and amount >= 0.
    // MODIFIES: transaction at index i
    // EFFECTS: sets the title of the transaction to title and amount to amount, produces true if edit is made
    //          and false if it fails to make the edit.
    public boolean editTransaction(int i, String title, int amount) {
        if (!transactions.isEmpty()) {
            Transaction toEdit = transactions.get(i);
            toEdit.setTitle(title);
            toEdit.setAmount(amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("transactions", thingiesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
