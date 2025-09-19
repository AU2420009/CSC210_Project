import java.util.Scanner;

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

    // Search a node
    boolean search(String key) {
        Node temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(key)) return true;
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

        // Create linked lists for different genres
        SinglyLinkedList fiction = new SinglyLinkedList();
        fiction.insert("Pride and Prejudice");
        fiction.insert("To Kill a Mockingbird");
        fiction.insert("The Great Gatsby");
        fiction.insert("Jane Eyre");
        fiction.insert("Wuthering Heights");
        fiction.insert("1984");

        SinglyLinkedList mystery = new SinglyLinkedList();
        mystery.insert("The Hound of the Baskervilles");
        mystery.insert("Gone Girl");
        mystery.insert("The Girl with the Dragon Tattoo");
        mystery.insert("Murder on the Orient Express");
        mystery.insert("The Da Vinci Code");
        mystery.insert("In the Woods");

        SinglyLinkedList fantasy = new SinglyLinkedList();
        fantasy.insert("Harry Potter and the Sorcerer's Stone");
        fantasy.insert("The Hobbit");
        fantasy.insert("The Lord of the Rings");
        fantasy.insert("A Game of Thrones");
        fantasy.insert("The Name of the Wind");
        fantasy.insert("Percy Jackson: The Lightning Thief");

        SinglyLinkedList science = new SinglyLinkedList();
        science.insert("A Brief History of Time");
        science.insert("The Selfish Gene");
        science.insert("Cosmos");
        science.insert("The Origin of Species");
        science.insert("Sapiens");
        science.insert("The Gene: An Intimate History");

        SinglyLinkedList history = new SinglyLinkedList();
        history.insert("Guns, Germs, and Steel");
        history.insert("The Rise and Fall of the Third Reich");
        history.insert("Team of Rivals");
        history.insert("1776");
        history.insert("The Wright Brothers");
        history.insert("Alexander Hamilton");

        SinglyLinkedList nonFiction = new SinglyLinkedList();
        nonFiction.insert("Educated");
        nonFiction.insert("Becoming");
        nonFiction.insert("The Power of Habit");
        nonFiction.insert("Thinking, Fast and Slow");
        nonFiction.insert("Atomic Habits");
        nonFiction.insert("The Subtle Art of Not Giving a F*ck");

        // Store genres in an array for easy access
        String[] genres = {"Fiction", "Mystery", "Fantasy", "Science", "History", "Non-Fiction"};
        SinglyLinkedList[] genreLists = {fiction, mystery, fantasy, science, history, nonFiction};

        int option;
        System.out.print("Welcome to the library management system.\nFeatures available are as follows:\n" +
                "1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n" +
                "4. Branch transport\n5. Book purchase calculator\n6. Exit\n");

        while (true) {
            System.out.print("Select an option number: ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Options available:");
                System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n" +
                        "4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
                sc.nextLine(); // consume invalid input
                continue;
            }
            sc.nextLine(); // consume leftover newline

            switch (option) {
                case 1:
                    System.out.println("Book Search");
                    System.out.println("Select a genre:");
                    for (int i = 0; i < genres.length; i++) {
                        System.out.println((i + 1) + ". " + genres[i]);
                    }
                    int genreChoice = sc.nextInt();
                    sc.nextLine();

                    if (genreChoice < 1 || genreChoice > genres.length) {
                        System.out.println("Invalid genre choice.");
                        break;
                    }

                    System.out.print("Enter book title to search: ");
                    String searchTitle = sc.nextLine();
                    if (genreLists[genreChoice - 1].search(searchTitle)) {
                        System.out.println("Book found in " + genres[genreChoice - 1] + " genre!");
                    } else {
                        System.out.println("Book not found in " + genres[genreChoice - 1] + " genre.");
                    }
                    break;

                case 2:
                    System.out.println("Manage Books");
                    System.out.println("Select a genre to add a book:");
                    for (int i = 0; i < genres.length; i++) {
                        System.out.println((i + 1) + ". " + genres[i]);
                    }
                    genreChoice = sc.nextInt();
                    sc.nextLine();

                    if (genreChoice < 1 || genreChoice > genres.length) {
                        System.out.println("Invalid genre choice.");
                        break;
                    }
                    genreLists[genreChoice - 1].display();
                    System.out.print("Enter book title to add: ");
                    String newBook = sc.nextLine();
                    genreLists[genreChoice - 1].insert(newBook);
                    System.out.println("Book added to " + genres[genreChoice - 1] + " genre!");
                    System.out.println("Current books in " + genres[genreChoice - 1] + ":");
                    genreLists[genreChoice - 1].display();
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

                default:
                    System.out.println("Invalid input. Options available:");
                    System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n" +
                            "4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
            }
        }
    }
}
