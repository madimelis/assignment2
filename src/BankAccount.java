import java.util.Scanner;

class Node {
    Object data;
    Node next;
    Node(Object data) { this.data = data; this.next = null; }
}

class MyQueue {
    private Node front, rear;
    public void enqueue(Object data) {
        Node newNode = new Node(data);
        if (rear == null) { front = rear = newNode; return; }
        rear.next = newNode; rear = newNode;
    }
    public Object dequeue() {
        if (front == null) return null;
        Object data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }
    public boolean isEmpty() { return front == null; }

    public Object peek() {
        if (front == null) return null;
        return front.data;
    }
}

class MyStack {
    private String[] arr;
    private int top;
    public MyStack(int cap) { arr = new String[cap]; top = -1; }
    public void push(String x) { if (top < arr.length - 1) arr[++top] = x; }
    public String pop() { return (top == -1) ? null : arr[top--]; }
    public String peek() { return (top == -1) ? null : arr[top]; }
}

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
    public String toString() { return accountNumber + ". " + username + " - Balance: " + balance; }

    public static BankAccount findAccount(BankAccount head, String name) {
        BankAccount current = head;
        while (current != null) {
            if (current.username.equalsIgnoreCase(name)) return current;
            current = current.next;
        }
        System.out.println("Account not found.");
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount head = null;

        MyStack history = new MyStack(100);
        MyQueue accountRequests = new MyQueue();
        MyQueue billQueue = new MyQueue();

        BankAccount[] predefined = {
                new BankAccount(1, "Ali", 150000.0),
                new BankAccount(2, "Sara", 220000.0),
                new BankAccount(3, "Alan", 50000.0)
        };
        System.out.println("Predefined Accounts:");
        for (BankAccount b : predefined) System.out.println(b);

        int mainChoice;
        do {
            System.out.println("\nMain Menu\n1. Enter Bank\n2. Enter ATM\n3. Admin Area\n4. Exit");
            System.out.print("Select area: ");
            mainChoice = sc.nextInt(); sc.nextLine();

            switch (mainChoice) {
                case 1:
                    int bankChoice;
                    do {
                        System.out.println("\nBank Services\n1. Submit Request\n2. Deposit\n3. Withdraw\n4.Pay a Bill\n5. Back");
                        bankChoice = sc.nextInt(); sc.nextLine();
                        if (bankChoice == 1) {
                            System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                            System.out.print("Name: "); String name = sc.nextLine();
                            System.out.print("Balance: "); double bal = sc.nextDouble();
                            accountRequests.enqueue(new BankAccount(id, name, bal));
                            System.out.println("Request submitted.");
                        } else if (bankChoice == 2 || bankChoice == 3) {
                            System.out.print("User: ");
                            BankAccount acc = findAccount(head, sc.nextLine());
                            if (acc != null) {
                                System.out.print("Amount: "); double amt = sc.nextDouble();
                                if (bankChoice == 2) {
                                    acc.balance += amt;
                                    history.push("Deposit " + amt + " to " + acc.username);
                                    System.out.println("Success.");
                                } else if (amt <= acc.balance) {
                                    acc.balance -= amt;
                                    history.push("Withdraw " + amt + " from " + acc.username);
                                    System.out.println("Success.");
                                } else System.out.println("Failed.");
                            }
                        }
                        else if (bankChoice == 4) {
                            System.out.print("Enter Bill Name: ");
                            String bill = sc.nextLine();
                            billQueue.enqueue(bill);
                            System.out.println("Bill added to processing queue.");
                        }
                    } while (bankChoice != 5);
                    break;

                case 2:
                    int atmChoice;
                    do {
                        System.out.println("\nATM Interface\n1. Balance\n2. Quick Withdraw\n3. Back");
                        atmChoice = sc.nextInt(); sc.nextLine();
                        if (atmChoice == 1 || atmChoice == 2) {
                            System.out.print("User: ");
                            BankAccount acc = findAccount(head, sc.nextLine());
                            if (acc != null) {
                                if (atmChoice == 1) System.out.println("Balance: " + acc.balance);
                                else {
                                    System.out.print("Amount: "); double amt = sc.nextDouble();
                                    if (amt <= acc.balance) {
                                        acc.balance -= amt;
                                        history.push("ATM Withdraw " + amt + " - " + acc.username);
                                        System.out.println("Take your cash.");
                                    } else System.out.println("No funds.");
                                }
                            }
                        }
                    } while (atmChoice != 3);
                    break;

                case 3:
                    int adminChoice;
                    do {
                        System.out.println("\nAdmin Panel\n1. Process Requests\n2. View Active\n3. Last Action\n4. Undo\n5. View and Process Bill Payments\n6. Back");
                        adminChoice = sc.nextInt(); sc.nextLine();
                        if (adminChoice == 1) {
                            BankAccount approved = (BankAccount) accountRequests.dequeue();
                            if (approved != null) {
                                approved.next = head; head = approved;
                                System.out.println("Activated: " + approved.username);
                            } else System.out.println("No requests.");
                        } else if (adminChoice == 2) {
                            BankAccount temp = head;
                            if (temp == null) System.out.println("Empty.");
                            while (temp != null) { System.out.println(temp); temp = temp.next; }
                        } else if (adminChoice == 3) {
                            String last = history.peek();
                            System.out.println(last != null ? "Last Action: " + last : "No history.");
                        } else if (adminChoice == 4) {
                            String undone = history.pop();
                            System.out.println(undone != null ? "Undo Done: " + undone : "Nothing to undo.");
                        }else if (adminChoice == 5) {
                                if (billQueue.isEmpty()) {
                                    System.out.println("No pending bills.");
                                } else {
                                    System.out.println("Next bill to process: " + billQueue.peek());
                                    String processedBill = (String) billQueue.dequeue();
                                    System.out.println("Processed: " + processedBill);}

                        }
                    } while (adminChoice != 6);
                    break;
            }
        } while (mainChoice != 4);
        System.out.println("Goodbye!");
    }
}