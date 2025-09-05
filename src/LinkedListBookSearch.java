import java.util.Scanner;

/* Algorithms for features:
- Hashtable for search
- AVL tree for add/remove?
- Queues for borrow 
- Branch: graph/tree with A*
- Budget: ????
*/
class Node {
    String title;
    Node next;

    public Node(String title) {
        this.title = title;
        this.next = null;
    }
}

class SinglyLinkedList {
    Node head;

    // Insert at end
    void insert(String title) {
        Node newNode = new Node(title);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = newNode;
    }

    // Delete a node by value
    void delete(String key) {
        if (head == null) return;

        if (head.title.equals(key)) {
            head = head.next;
            return;
        }

        Node temp = head;
        while (temp.next != null && !temp.next.title.equals(key))
            temp = temp.next;

        if (temp.next == null) return;
        temp.next = temp.next.next;
    }

    // Search a node
    boolean search(String key) {
        Node temp = head;
        while (temp != null) {
            if (temp.title.equals(key)) return true;
            temp = temp.next;
        }
        return false;
    }

    // Print list
    void display() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.title + " -> ");
            temp = temp.next;
        }
        System.out.println("NULL");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create book list once, not inside loop
        SinglyLinkedList list = new SinglyLinkedList();
        list.insert("Pride and Prejudice");
        list.insert("1984");
        list.insert("Animal Farm");
        list.display();

        int option = 6;
        System.out.print("Welcome to the library management system.\nFeatures available are as follows:\n1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");

        while (true) {
            System.out.print("Select an option number: ");

            // handle invalid (non-int) input
            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Options available:");
                System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
                sc.nextLine(); // consume invalid input
                continue;
            }

            sc.nextLine(); // consume leftover newline

            switch (option) {
                case 1:
                    System.out.println("Book Search");
                    System.out.print("Enter book title to search: ");
                    String searchTitle = sc.nextLine();
                    if (list.search(searchTitle)) {
                        System.out.println("Book found!");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 2:
                    System.out.println("Manage Books");
                    System.out.print("Enter book title to add: ");
                    String newBook = sc.nextLine();
                    list.insert(newBook);
                    System.out.println("Book added!");
                    System.out.println("Current Books:");
                    list.display();
                    break;

                case 3:
                    System.out.println("Borrow Book/Check Borrow Records");
                    break;

                case 4:
                    System.out.println("Book transport from branch");
                    break;

                case 5:
                    System.out.println("Calculate optimal purchase from budget");
                    break;

                case 6:
                    System.out.println("Have a nice day!");
                    sc.close();
                    System.exit(0);

                default: // any other int
                    System.out.println("Invalid input. Options available:");
                    System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
            }
        }
    }
}
