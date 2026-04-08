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
        BillNode queueFront = null;
        BillNode queueRear = null;
        BankAccount reqFront = null;
        BankAccount reqRear = null;

        BankAccount[] predefinedAccounts = new BankAccount[3];
        predefinedAccounts[0] = new BankAccount(1, "Ali", 150000.0);
        predefinedAccounts[1] = new BankAccount(2, "Sara", 220000.0);
        predefinedAccounts[2] = new BankAccount(3, "Alan", 50000.0);

        System.out.println("Predefined Accounts");
        for (int i = 0; i < predefinedAccounts.length; i++) {System.out.println(predefinedAccounts[i]);}

        do {
            System.out.println("\n1. Add a new account \n2. Display all accounts" +
                    "\n3. Search account by username  \n4. Deposit money" +
                    "\n5. Withdraw money \n6. Show last transaction"  +
                    " \n7. Undo last transaction \n8. Add bill" +
                    "\n9. Process bill \n10. Display queue" +
                    "\n11. Process request" +
                    "\n12. Exit");
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
                    BankAccount newReq = new BankAccount(accountNumber, username, balance);
                    if (reqRear == null) reqFront = reqRear = newReq;
                    else {
                        reqRear.next = newReq;
                        reqRear = newReq;
                    }
                    System.out.println("Request sent to admin.");
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
                        System.out.print("Withdraw: ");
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

                case 8:
                    System.out.print("Enter bill name: ");
                    String bName = sc.nextLine();
                    BillNode newBill = new BillNode(bName);
                    if (queueRear == null) {
                        queueFront = queueRear = newBill;
                    } else {
                        queueRear.next = newBill;
                        queueRear = newBill;
                    }
                    System.out.println("Added: " + bName);
                    break;

                case 9:
                    if (queueFront != null) {
                        System.out.println("Processing: " + queueFront.billName);
                        queueFront = queueFront.next;
                        if (queueFront == null) queueRear = null;

                        if (top < 99) history[++top] = "Bill processed";
                    }
                    else System.out.println("Queue is empty.");
                    break;

                case 10:
                    System.out.println("\nRemaining Bills:");
                    BillNode tempB = queueFront;
                    if (tempB == null) System.out.println("No pending bills.");
                    while (tempB != null) {
                        System.out.println("- " + tempB.billName);
                        tempB = tempB.next;
                    }
                    break;

                case 11:
                    if (reqFront != null) {
                        BankAccount approved = reqFront;
                        reqFront = reqFront.next;
                        if (reqFront == null) reqRear = null;

                        approved.next = head;
                        head = approved;
                        System.out.println("Account approved: " + approved.username);
                    } else System.out.println("No pending requests.");
                    break;
            }
        } while(choice != 12);


    }
}
