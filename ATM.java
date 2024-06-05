import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

// User class to represent ATM users
class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}

// ATM class to represent the ATM system
public class ATM {
    private HashMap<String, User> users;
    private Scanner scanner;

    public ATM() {
        users = new HashMap<>();
        // Add some sample users
        users.put("12345", new User("12345", "1234", 1000.0));
        users.put("54321", new User("54321", "4321", 2000.0));
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (authenticate(userId, pin)) {
            System.out.println("Authentication successful!");
            showOptions(users.get(userId));
        } else {
            System.out.println("Invalid User ID or PIN");
        }
    }

    private boolean authenticate(String userId, String pin) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            return user.getPin().equals(pin);
        }
        return false;
    }

    private void showOptions(User user) {
        int choice;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    viewTransactions(user);
                    break;
                case 2:
                    withdraw(user);
                    break;
                case 3:
                    deposit(user);
                    break;
                case 4:
                    // Transfer functionality not implemented in this example
                    System.out.println("Transfer functionality not implemented");
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }

    private void viewTransactions(User user) {
        System.out.println("\nTransactions History:");
        for (Transaction transaction : user.getTransactions()) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }

    private void withdraw(User user) {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    private void deposit(User user) {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        user.deposit(amount);
        System.out.println("Deposit successful");
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}