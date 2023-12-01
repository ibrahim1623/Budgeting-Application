package ui;

import model.Event;
import model.EventLog;
import model.Transaction;
import model.TransactionRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

// Budgeting Application GUI
public class BudgetingGUI extends JFrame {
    private TransactionRecord transactionRecord;
    private JLabel imageLabel;

    public BudgetingGUI() {
        super("Budgeting Application");
        transactionRecord = new TransactionRecord("Transaction Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        initializeComponents();
        addShutdownHook();
    }

    // EFFECTS: initializes the components of the budgeting application GUI
    public void initializeComponents() {
        setLayout(new BorderLayout());

        String[] buttonLabels = {"Add Transaction", "View All Transactions", "Save", "Load", "Calculate Average",
                "Show Image"};
        ActionListener[] buttonActions = {
                e -> showAddTransactionDialog(),
                e -> showViewAllTransactions(),
                e -> saveTransactions(),
                e -> loadTransactions(),
                e -> calculateAverage(),
                e -> showImage()
        };

        JPanel buttonPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.addActionListener(buttonActions[i]);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        imageLabel = new JLabel(new ImageIcon("image.jpg"));
        imageLabel.setVisible(false);
        add(imageLabel, BorderLayout.CENTER);
    }

    // EFFECTS: shows the specified image on the GUI
    private void showImage() {
        String imagePath = "image.jpg";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        imageLabel.setIcon(imageIcon);
        imageLabel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: shows a dialog for adding a new transaction and adds it to the transaction record
    private void showAddTransactionDialog() {
        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Transaction", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            transactionRecord.addTransaction(new Transaction(name, (int) amount));
        }
    }

    // EFFECTS: displays a dialog showing all transactions in a table
    private void showViewAllTransactions() {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Amount"}, 0);
        for (Transaction transaction : transactionRecord.viewAll()) {
            tableModel.addRow(new Object[]{transaction.getTitle(), transaction.getAmount()});
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(this, scrollPane, "View All Transactions", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: saves the current transaction record to a file
    private void saveTransactions() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
                oos.writeObject(transactionRecord);
                JOptionPane.showMessageDialog(this, "Data saved successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads transactions from a file and updates the transaction record
    private void loadTransactions() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
                transactionRecord = (TransactionRecord) ois.readObject();
                JOptionPane.showMessageDialog(this, "Data loaded successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: calculates and displays the average amount of transactions
    private void calculateAverage() {
        ArrayList<Transaction> transactions = transactionRecord.viewAll();
        if (!transactions.isEmpty()) {
            double average = transactionRecord.calculateAverage();

            JOptionPane.showMessageDialog(this, "Average Amount: $" + average,
                    "Average Calculation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No transactions to calculate average.",
                    "Average Calculation", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: adds a shutdown hook to print event logs when the application quits
    private void addShutdownHook() {
        // Code to be executed when the application is shutting down
        Runtime.getRuntime().addShutdownHook(new Thread(this::printEventLogsToConsole));
    }

    // EFFECTS: prints event logs to the console
    private void printEventLogsToConsole() {
        EventLog eventLog = EventLog.getInstance();  // Access the EventLog instance
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BudgetingGUI().setVisible(true);
            }
        });
    }
}
