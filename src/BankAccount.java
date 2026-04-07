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

    public static void  main(String[] args) {
        LinkedList<BankAccount> accounts = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Add a new account \n2. Display all accounts " +
                    "\n3. Search account by username \n4. Exit");
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
                    System.out.print("Enter username to search: ");
                    String search = sc.nextLine();
                    boolean found = false;
                    for (BankAccount acc : accounts) {
                        if (acc.username.equalsIgnoreCase(search)) {
                            System.out.println(acc);
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Account not found.");
                    break;
            }
        } while(choice != 4);
    }
}
