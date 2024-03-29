package ui;

import model.Transaction;
import model.TransactionRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;


// Budgeting application
public class BudgetingApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private TransactionRecord transactions;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the budgeting application
    public BudgetingApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBudgetingApp();

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBudgetingApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                doAdd();
                break;
            case "v_all":
                doViewAll();
                break;
            case "v_one":
                doViewOne();
                break;
            case "e":
                doEdit();
                break;
            case "s":
                doSave();
                break;
            case "l":
                doLoad();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes transaction record
    private void init() {
        transactions = new TransactionRecord("transaction record");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new transaction");
        System.out.println("\tv_all -> view all transactions");
        System.out.println("\tv_one -> view one transaction");
        System.out.println("\te -> edit a transaction");
        System.out.println("\ts -> save");
        System.out.println("\tl -> load");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts the add transaction method
    private void doAdd() {
        TransactionRecord selected = transactions;
        System.out.print("Enter title for transaction: ");
        String title = input.next();
        System.out.print("Enter cost of transaction: $");
        int amount = input.nextInt();
        Transaction transaction = new Transaction(title, amount);

        if (title.length() > 0 && amount > 0) {
            selected.addTransaction(transaction);
            printT(selected);
        } else {
            System.out.println("Cannot add such transaction.\n");
        }


    }

    // MODIFIES: this
    // EFFECTS: conducts viewAll transactions method
    private void doViewAll() {
        TransactionRecord selected = transactions;
        printT(selected);
    }

    // MODIFIES: this
    // EFFECTS: conducts viewOne transaction method
    private void doViewOne() {
        TransactionRecord selected = transactions;
        System.out.print("Enter index for transaction: ");
        int index = input.nextInt();
        ArrayList<Transaction> list = selected.viewAll();
        Transaction transactionToPrint;
        if (index + 1 <= list.size()) {
            transactionToPrint = selected.viewTransaction(index);
            System.out.print("Title: " + transactionToPrint.getTitle() + "     " + "Amount: "
                    + transactionToPrint.getAmount() + "\n");
        } else {
            System.out.println("Out of Index Range\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts an edit transaction method
    private void doEdit() {
        doViewAll();
        TransactionRecord selected = transactions;
        System.out.print("Enter index for transaction to edit: ");
        int index = input.nextInt();
        ArrayList<Transaction> list = selected.viewAll();
        if (index + 1 <= list.size()) {
            System.out.print("Enter new title for transaction: ");
            String title = input.next();
            System.out.print("Enter new amount transaction : $");
            int amount = input.nextInt();
            selected.editTransaction(index, title, amount);
            printT(selected);
        } else {
            System.out.println("Out of Index Range\n");
        }


    }


    // EFFECTS: prints list of transactions in transaction record
    private void printT(TransactionRecord selected) {
        ArrayList<Transaction> list = selected.viewAll();
        for (Transaction t : list) {
            System.out.print("Title: " + t.getTitle() + "     " + "Amount: " + t.getAmount() + "\n");
        }
    }

    // EFFECTS: saves the workroom to file
    private void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(transactions);
            jsonWriter.close();
            System.out.println("Saved " + transactions.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void doLoad() {
        try {
            transactions = jsonReader.read();
            System.out.println("Loaded " + transactions.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
