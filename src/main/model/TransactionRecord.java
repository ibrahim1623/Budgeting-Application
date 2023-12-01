package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.io.Serializable;
import java.util.ArrayList;

// maintains a record of all transactions
public class TransactionRecord implements Writable, Serializable {
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
        EventLog.getInstance().logEvent(new Event("Added a transaction to transaction record."));
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

    // REQUIRES: !transactions.isEmpty()
    // EFFECTS: computes the average and returns it as a double
    public double calculateAverage() {
        double totalAmount = 0;

        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }

        double average = totalAmount / transactions.size();
        EventLog.getInstance().logEvent(new Event("Average calculated is " + average));
        return average;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this transaction record as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
