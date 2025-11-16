import java.util.Scanner;

import datastruc.AVLTree;
import datastruc.PriorityBorrowManager;
import datastruc.SinglyLinkedList;
import datastruc.Sorters;
import datastruc.BookHashTable;
import datastruc.WtGraph;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

     // init our data structures
        PriorityBorrowManager pbm = new PriorityBorrowManager();
        BookHashTable bookTable = new BookHashTable();
        bookTable.initBHT(); // populate w initial data
                    WtGraph grf = new WtGraph();
                  //init graph data
                  grf.addVertex("Bopal");
                  grf.addVertex("Ambawadi");
                  grf.addVertex("Gulbai Tekra");
                  grf.addVertex("Astodia");
                  grf.addVertex("Panjrapole");

                  grf.addEdge("Bopal", "Ambawadi", 2);
                  grf.addEdge("Astodia", "Panjrapole", 3);
                  grf.addEdge("Gulbai Tekra", "Panjrapole", 2);
                  grf.addEdge("Bopal", "Gulbai Tekra", 7);
                  grf.addEdge("Astodia", "Ambawadi", 6);
                  grf.addEdge("Ambawadi", "Panjrapole", 4);

     //   BorrowQueue borrowQueue = new BorrowQueue();

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

     // main ui loop logic
        int option;
        System.out.print("Welcome to the library management system.\nFeatures available are as follows:\n" + "1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Exit\n");

        while (true) {
            System.out.print("Select an option number: ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Options available:");
                System.out.print("1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Exit\n");
                sc.nextLine(); // consume invalid input
                continue;
            }
            sc.nextLine(); // consume leftover newline

            switch (option) {

                case 1: // unified metadata book search
                    System.out.println("\nEnter Book Details (press Enter to skip a field):");

                    System.out.print("Title: ");
                    String title = sc.nextLine().trim();
                    if (title.isEmpty()) title = null;

                    System.out.print("Genre: ");
                    String genre = sc.nextLine().trim();
                    if (genre.isEmpty()) genre = null;

                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine().trim();
                    if (isbn.isEmpty()) isbn = null;

                    System.out.print("Publication Year: ");
                    String yearStr = sc.nextLine().trim();
                    Integer year = null;
                    if (!yearStr.isEmpty()) {
                        try {
                            year = Integer.parseInt(yearStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid year. Ignoring year filter.");
                        }
                    }

                    BookHashTable.Book matched =
                            bookTable.fullSearch(title, genre, isbn, year);

                    if (matched != null)
                        System.out.println("\nFound -> " + matched);
                    else
                        System.out.println("\nNo book matched the given details!");
                    break;

                case 2:
                    System.out.println("Manage Books");
                    System.out.println("Select a genre to add a book:");
                    for (int i = 0; i < genres.length; i++) {
                        System.out.println((i + 1) + ". " + genres[i]);
                    }
                    int genreChoice = sc.nextInt();
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
                    genreLists[genreChoice - 1].head = Sorters.Sort(genreLists[genreChoice - 1].head);
                    System.out.println("Sorted alphabetically books in " + genres[genreChoice - 1] + ":");
                    genreLists[genreChoice - 1].display();
                    break;

                case 3:
                    System.out.println("\nBorrow/Return (Priority) & Records");
                    System.out.println("1. Borrow a book (priority-aware)");
                    System.out.println("2. View active borrow records (ordered)");
                    System.out.println("3. Return a specific book (by user)");
                    System.out.println("4. Serve next pending request (highest priority)");
                    System.out.print("Choose: ");
                    int borrowChoice;
                    try {
                        borrowChoice = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        sc.nextLine();
                        break;
                    }
                    sc.nextLine();

                    if (borrowChoice == 1) {
                        // Borrow flow
                        System.out.println("Select a genre:");
                        for (int i = 0; i < genres.length; i++) {
                            System.out.println((i + 1) + ". " + genres[i]);
                        }
                        int borrowGenre;
                        try {
                            borrowGenre = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input for genre.");
                            sc.nextLine();
                            break;
                        }
                        sc.nextLine();

                        if (borrowGenre < 1 || borrowGenre > genres.length) {
                            System.out.println("Invalid genre choice.");
                            break;
                        }

                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = sc.nextLine();

                        // Ask for user details
                        System.out.print("Enter your name: ");
                        String user = sc.nextLine();
                        System.out.print("Enter your role (faculty/staff/student): ");
                        String role = sc.nextLine();
                        int priority = pbm.computePriority(role);

                        // Check if book exists in selected genre (available in inventory)
                        if (genreLists[borrowGenre - 1].search(borrowTitle)) {
                            // Reserve by deleting from inventory and enqueue as borrowed (per your manager)
                            boolean removed = genreLists[borrowGenre - 1].delete(borrowTitle);
                            if (!removed) {
                                System.out.println("Failed to reserve book. Try again.");
                                break;
                            }
                            // Add to priority borrow manager (this will add to heap & map as per your class)
                            pbm.borrow(user, borrowTitle, genres[borrowGenre - 1], priority);
                        } else {
                            // Not in inventory => either borrowed or not present
                            // If it's currently borrowed (map may contain active), we still enqueue as waiting
                            // We will just add to queue (user will wait)
                            System.out.println("Book not found in inventory for that genre.");
                            System.out.print("Do you want to add a waiting request for this title? (y/n): ");
                            String ans = sc.nextLine().trim().toLowerCase();
                            if (ans.equals("y") || ans.equals("yes")) {
                                // enqueue as waiting request (we still call borrow to add to heap & map)
                                // Note: your PriorityBorrowManager.borrow puts record into heap & map.
                                // If you intended map to only store active borrows, adjust manager logic accordingly.
                                pbm.borrow(user, borrowTitle, genres[borrowGenre - 1], priority);
                            } else {
                                System.out.println("Request cancelled.");
                            }
                        }

                    } else if (borrowChoice == 2) {
                        // View active borrow records ordered by priority (manager handles display)
                        pbm.displayActiveOrdered();

                    } else if (borrowChoice == 3) {
                        // Return a specific book by user
                        System.out.print("Enter your name: ");
                        String retUser = sc.nextLine();
                        System.out.print("Enter the book title to return: ");
                        String retBook = sc.nextLine();
                        boolean ok = pbm.returnByUser(retUser, retBook, genreLists, genres);
                        if (!ok) {
                            System.out.println("Return failed or no such active record.");
                        }
                    } else if (borrowChoice == 4) {
                        // Serve next pending request (highest priority)
                        String nextReqMsg = pbm.pollNextActiveMessage();
                        System.out.println("Served: " + nextReqMsg);
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;
                                

               case 4:
                   AVLTree tree = new AVLTree();

                       tree.root = tree.insert(tree.root, "Pride and Prejudice");
                       tree.root = tree.insert(tree.root, "To Kill a Mockingbird");
                       tree.root = tree.insert(tree.root, "The Great Gatsby");
                       tree.root = tree.insert(tree.root, "Jane Eyre");
                       tree.root = tree.insert(tree.root, "Wuthering Heights");
                       tree.root = tree.insert(tree.root, "1984");
                       tree.root = tree.insert(tree.root, "The Hound of the Baskervilles");
                       tree.root = tree.insert(tree.root, "Gone Girl");
                       tree.root = tree.insert(tree.root, "The Girl with the Dragon Tattoo");
                       tree.root = tree.insert(tree.root, "Murder on the Orient Express");
                       tree.root = tree.insert(tree.root, "The Da Vinci Code");
                       tree.root = tree.insert(tree.root, "In the Woods");
                       tree.root = tree.insert(tree.root, "Harry Potter and the Sorcerer's Stone");
                       tree.root = tree.insert(tree.root, "The Hobbit");
                       tree.root = tree.insert(tree.root, "The Lord of the Rings");
                       tree.root = tree.insert(tree.root, "A Game of Thrones");
                       tree.root = tree.insert(tree.root, "The Name of the Wind");
                       tree.root = tree.insert(tree.root, "Percy Jackson: The Lightning Thief");
                       tree.root = tree.insert(tree.root, "A Brief History of Time");
                       tree.root = tree.insert(tree.root, "The Selfish Gene");
                       tree.root = tree.insert(tree.root, "Cosmos");
                       tree.root = tree.insert(tree.root, "The Origin of Species");
                       tree.root = tree.insert(tree.root, "Sapiens");
                       tree.root = tree.insert(tree.root, "The Gene: An Intimate History");
                       tree.root = tree.insert(tree.root, "Guns, Germs, and Steel");
                       tree.root = tree.insert(tree.root, "The Rise and Fall of the Third Reich");
                       tree.root = tree.insert(tree.root, "Team of Rivals");
                       tree.root = tree.insert(tree.root, "1776");
                       tree.root = tree.insert(tree.root, "The Wright Brothers");
                       tree.root = tree.insert(tree.root, "Alexander Hamilton");
                       tree.root = tree.insert(tree.root, "Educated");
                       tree.root = tree.insert(tree.root, "Becoming");
                       tree.root = tree.insert(tree.root, "The Power of Habit");
                       tree.root = tree.insert(tree.root, "Thinking, Fast and Slow");
                       tree.root = tree.insert(tree.root, "Atomic Habits");
                       tree.root = tree.insert(tree.root, "The Subtle Art of Not Giving a F*ck");

                       System.out.println("Please enter a title to search for: \n");
                       String userInput = sc.nextLine();
                       AVLTree.AVLNode result = tree.Search(userInput);
    
                       if(result != null){System.out.println("Found in library: " + result.title);} else {System.out.println("Title not found.");}

                       System.out.println("Lexicographic tree traversal:\n");
                       tree.alphabeticTraverse(tree.root);
                        System.out.println("");
                       
                        /* Out of Scope: fully implement Prefix-based search
                            System.out.println("\nPrefix Search: 'bo'");
                            tree.PrefixSearch("bo"); 
                        */
                 break;

                case 5:
                    System.out.println("Book transport from branch");

                System.out.print("Network of branches (graph representation): ");
                grf.printGraph();
                System.out.print("Enter the branch to start from: ");
                String startPl = sc.nextLine();
                System.out.print("Enter the branch to get to: ");
                String endPl = sc.nextLine();
                List<String> path = grf.dijkstra(startPl, endPl);
                if (path != null){
                System.out.println("Shortest path: " + path);
                System.out.println("Distance = " + grf.shortestDistance(startPl, endPl) + "km.");
                }
                else System.out.println("No route between these branches!");

                System.out.println("P.S. Do you want to add or remove a branch? Type A for add, R for remove. Type anything else for neither.");
                String addremchoice = sc.nextLine();
                if (addremchoice.equals("A")){
                System.out.print("Enter the branch to add: ");
                String addBranch = sc.nextLine();
                grf.addVertex(addBranch);
                }
                else if (addremchoice.equals("R")){
                System.out.print("Enter the branch to remove: ");
                String removeBranch = sc.nextLine();
                grf.removeVertex(removeBranch);

}
                break;

                case 6:
                    System.out.println("Have a nice day!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid input. Options available:");
                    System.out.print("1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Exit\n");
            }
        }
    }
}
