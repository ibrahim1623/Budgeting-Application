package model;

// Represents a transaction having a title and amount;
public class Transaction {
    private int amount; // amount of the current transaction
    private String title; // title of the new transaction

    // REQUIRES: String of non-zero length, amount >= 0
    // MODIFIES: this
    // EFFECTS: creates a new transaction with this.title set to title and this.amount
    //          set to amount.
    public Transaction(String title, int amount) {
        this.amount = amount;
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
