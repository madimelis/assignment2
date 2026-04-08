import java.util.*;

public class BankAccount {
    int accountNumber;
    String username;
    double balance;

    public BankAccount(int accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
    }

    @Override
    public String toString() {return accountNumber + ". " + username + " - Balance: "  + balance;}

    public static BankAccount findAccount(LinkedList<BankAccount> accounts, String name) {
        for (BankAccount acc : accounts) {
            if (acc.username.equalsIgnoreCase(name)) return acc;
        }
        System.out.println("Account not found.");
        return null;
    }

    public static void  main(String[] args) {
        LinkedList<BankAccount> accounts = new LinkedList<>();
        Stack<String> transactionHistory = new Stack<>();
        Scanner sc = new Scanner(System.in);
        int choice;



        do {
            System.out.println("\n1. Add a new account \n2. Display all accounts " +
                    "\n3. Search account by username  \n4. Deposit money \n5. Withdraw money" +
                    "\n6. Show last transaction \n7. Undo last transaction" +
                    "\n8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = sc.nextDouble();
                    accounts.add(new BankAccount(accountNumber, username, balance));
                    System.out.println("Account added successfully!");
                    break;

                case 2:
                    System.out.println("\nAccounts list: ");
                    if (accounts.isEmpty()) System.out.println("No accounts found.");
                    else {
                        for (BankAccount acc : accounts) {
                            System.out.println(acc);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    BankAccount foundAcc = findAccount(accounts, sc.nextLine());
                    if (foundAcc != null) System.out.println(foundAcc);
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    BankAccount depAcc = findAccount(accounts, sc.nextLine());
                    if (depAcc != null) {
                        System.out.print("Deposit: ");
                        double deposit = sc.nextDouble();
                        depAcc.balance += deposit;
                        transactionHistory.push("Deposit " + deposit + " to " + depAcc.username);
                        System.out.println("New balance: " + depAcc.balance);
                    }
                    break;

                case 5:
                    System.out.print("Enter username: ");
                    BankAccount wAcc = findAccount(accounts, sc.nextLine());
                    if (wAcc != null) {
                        System.out.print("Withdraw: ");
                        double withdraw = sc.nextDouble();
                        if (withdraw <= wAcc.balance) {
                            wAcc.balance -= withdraw;
                            transactionHistory.push("Withdraw " + withdraw + " from " + wAcc.username);
                            System.out.println("New balance: " + wAcc.balance);
                        }
                        else System.out.println("Withdraw failed.");
                    }
                    break;

                case 6:
                    if (!transactionHistory.isEmpty()) System.out.println("Last transaction: " + transactionHistory.peek());
                    else System.out.println("History is empty.");
                    break;

                case 7:
                    if (!transactionHistory.isEmpty()) {
                        String removed = transactionHistory.pop();
                        System.out.println("Undo -> " + removed + " removed from history.");
                    } else {
                        System.out.println("Nothing to undo.");
                    }
                    break;
            }
        } while(choice != 8);
    }
}
