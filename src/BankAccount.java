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
        String[] stackHistory = new String[100];
        int top = -1;
        Scanner sc = new Scanner(System.in);
        int mainChoice;
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
            System.out.println("\nMain Menu");
            System.out.println("1. Enter Bank");
            System.out.println("2. Enter ATM");
            System.out.println("3. Admin Area");
            System.out.println("4. Exit");
            System.out.print("Select area: ");
            mainChoice = sc.nextInt();
            sc.nextLine();

            switch (mainChoice) {
                case 1:
                    int bankChoice;
                    do {
                        System.out.println("\nBank Services");
                        System.out.println("1. Submit Account Request\n2. Deposit Money\n3. Withdraw Money\n4. Back");
                        System.out.print("Choice: ");
                        bankChoice = sc.nextInt(); sc.nextLine();

                        if (bankChoice == 1) {
                            System.out.print("Enter ID: "); int id = sc.nextInt(); sc.nextLine();
                            System.out.print("Enter Name: "); String name = sc.nextLine();
                            System.out.print("Initial Balance: "); double bal = sc.nextDouble();
                            BankAccount newReq = new BankAccount(id, name, bal);
                            if (reqRear == null) { reqFront = reqRear = newReq; }
                            else { reqRear.next = newReq; reqRear = newReq; }
                            System.out.println("Request submitted. Wait for Admin approval.");
                        }
                        else if (bankChoice == 2 || bankChoice == 3) {
                            System.out.print("Enter username: ");
                            BankAccount acc = findAccount(head, sc.nextLine());
                            if (acc != null) {
                                System.out.print("Amount: "); double amt = sc.nextDouble();
                                if (bankChoice == 2) {
                                    acc.balance += amt;
                                    if (top < 99) stackHistory[++top] = "Deposit " + amt + " to " + acc.username;
                                    System.out.println("Success. New balance: " + acc.balance);
                                } else {
                                    if (amt <= acc.balance) {
                                        acc.balance -= amt;
                                        if (top < 99) stackHistory[++top] = "Withdraw " + amt + " from " + acc.username;
                                        System.out.println("Success. New balance: " + acc.balance);
                                    } else System.out.println("Withdraw failed.");
                                }
                            }
                        }
                    } while (bankChoice != 4);
                    break;

                case 2:
                    int atmChoice;
                    do {
                        System.out.println("\nATM Interface");
                        System.out.println("1. Balance Enquiry\n2. Quick Withdraw\n3. Back");
                        System.out.print("Choice: ");
                        atmChoice = sc.nextInt(); sc.nextLine();

                        if (atmChoice == 1 || atmChoice == 2) {
                            System.out.print("Enter username: ");
                            BankAccount acc = findAccount(head, sc.nextLine());
                            if (acc != null) {
                                if (atmChoice == 1) System.out.println("Current Balance: " + acc.balance);
                                else {
                                    System.out.print("Amount to withdraw: "); double amt = sc.nextDouble();
                                    if (amt <= acc.balance) {
                                        acc.balance -= amt;
                                        if (top < 99) stackHistory[++top] = "ATM Withdraw " + amt + " - " + acc.username;
                                        System.out.println("Please take your cash.");
                                    } else System.out.println("Insufficient funds!");
                                }
                            }
                        }
                    } while (atmChoice != 3);
                    break;

                case 3:
                    int adminChoice;
                    do {
                        System.out.println("\nAdmin Panel");
                        System.out.println("1. Process Account Requests");
                        System.out.println("2. View All Active Accounts");
                        System.out.println("3. View Last Transaction");
                        System.out.println("4. Undo Last Transaction");
                        System.out.println("5. Back");
                        System.out.print("Choice: ");
                        adminChoice = sc.nextInt(); sc.nextLine();

                        if (adminChoice == 1) {
                            if (reqFront != null) {
                                BankAccount approved = reqFront;
                                reqFront = reqFront.next;
                                if (reqFront == null) reqRear = null;
                                approved.next = head;
                                head = approved;
                                System.out.println("Approved and Activated: " + approved.username);
                            } else System.out.println("No pending requests.");
                        }
                        else if (adminChoice == 2) {
                            BankAccount temp = head;
                            if (temp == null) System.out.println("No accounts in system.");
                            while (temp != null) { System.out.println(temp); temp = temp.next; }
                        }
                        else if (adminChoice == 3) {
                            if (top >= 0) System.out.println("Last Action: " + stackHistory[top]);
                            else System.out.println("No history.");
                        }
                        else if (adminChoice == 4) {
                            if (top >= 0) System.out.println("Undo Done: " + stackHistory[top--] + " removed.");
                            else System.out.println("Nothing to undo.");
                        }
                    } while (adminChoice != 5);
                    break;
            }
        }
        while(mainChoice != 4);
        System.out.println("System closed. Goodbye!");

    }
}
