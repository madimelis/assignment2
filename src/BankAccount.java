import java.util.Scanner;

public class BankAccount {
    int accountNumber;
    String username;
    double balance;
    BankAccount next;

    public BankAccount(int accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
        this.next = null;
    }

    @Override
    public String toString() {return accountNumber + ". " + username + " - Balance: "  + balance;}

    public static BankAccount findAccount(BankAccount head, String name) {
        BankAccount current = head;
        while (current != null) {
            if (current.username.equalsIgnoreCase(name)) {
                return current;
            }
            current = current.next;
        }
        System.out.println("Account not found.");
        return null;
    }

    public static void  main(String[] args) {
        BankAccount head = null;
        String[] history = new String[100];
        int top = -1;
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
                    BankAccount newAcc = new BankAccount(accountNumber, username, balance);
                    if (head != null) newAcc.next = head;
                    head = newAcc;
                    System.out.println("Account added.");
                    break;

                case 2:
                    System.out.println("\nAccounts list: ");
                    BankAccount temp = head;
                    if (temp == null) System.out.println("Empty.");
                    while (temp != null) {
                        System.out.println(temp);
                        temp = temp.next;
                    }
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    BankAccount foundAcc = findAccount(head, sc.nextLine());
                    if (foundAcc != null) System.out.println(foundAcc);
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    BankAccount depAcc = findAccount(head, sc.nextLine());
                    if (depAcc != null) {
                        System.out.print("Deposit: ");
                        double deposit = sc.nextDouble();
                        depAcc.balance += deposit;
                        if (top < 99) history[++top] = "Deposit " + deposit + " to " + depAcc.username;
                        System.out.println("New balance: " + depAcc.balance);
                    }
                    break;

                case 5:
                    System.out.print("Enter username: ");
                    BankAccount wAcc = findAccount(head, sc.nextLine());
                    if (wAcc != null) {
                        System.out.println("Withdraw: ");
                        double withdraw = sc.nextDouble();
                        if (withdraw <= wAcc.balance) {
                            wAcc.balance -= withdraw;
                            if (top <99) history[++top] = "Withdraw " + withdraw + " from " + wAcc.username;
                            System.out.println("New balance: " + wAcc.balance);
                        }
                        else System.out.println("Withdraw failed.");
                    }
                    break;

                case 6:
                    if (top >= 0) System.out.println(history[top]);
                    else System.out.println("History is empty.");
                    break;

                case 7:
                    if (top >= 0) System.out.println("Undo -> " + history[top--] + " removed.");
                    else System.out.println("Nothing to undo.");
                    break;
            }
        } while(choice != 8);
    }
}
