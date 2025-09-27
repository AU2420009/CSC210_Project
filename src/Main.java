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

// Borrow Queue (queues)
class BorrowQueue {
    private static class QNode {
        String borrowerName;
        String bookTitle;
        String genre;
        QNode next;

        public QNode(String borrowerName, String bookTitle, String genre) {
            this.borrowerName = borrowerName;
            this.bookTitle = bookTitle;
            this.genre = genre;
            this.next = null;
        }
    }

    QNode front, rear;

    public boolean isEmpty() {
        return front == null;
    }

    // Enqueue
    public void enqueue(String borrower, String book, String genre) {
        QNode newNode = new QNode(borrower, book, genre);
        if (rear == null) {
            front = rear = newNode;
            System.out.println(borrower + " has borrowed \"" + book + "\" from " + genre + " genre.");
            return;
        }
        rear.next = newNode;
        rear = newNode;
        System.out.println(borrower + " has borrowed \"" + book + "\" from " + genre + " genre.");
    }

    // Dequeue (Return book)
    public void dequeue() {
        if (isEmpty()) {
            System.out.println("No borrow records found.");
            return;
        }
        System.out.println(front.borrowerName + " has returned \"" + front.bookTitle + "\" from " + front.genre + " genre.");
        front = front.next;
        if (front == null) rear = null;
    }

    // Display Queue
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No current borrow records.");
            return;
        }
        QNode temp = front;
        System.out.println("Current Borrow Queue:");
        while (temp != null) {
            System.out.println(temp.borrowerName + " -> \"" + temp.bookTitle + "\" (" + temp.genre + ")");
            temp = temp.next;
        }
    }
}


class AVLTree 
{

    public class AVLNode 
    {
     String title;
     int height;
     AVLNode left;
     AVLNode right;
     AVLNode(String title){this.title = title; this.height = 1;}
    }     

    public AVLNode root;

    public int getHeight(AVLNode node){ // just a null-safe wrapper
     if (node == null){return 0;}
     else return node.height; 
    }

    public int BF(AVLNode node){
     if (node == null){return 0;}
     else return getHeight(node.left) - getHeight(node.right); 
    }

    private AVLNode rotateLeft(AVLNode p){
     AVLNode x = p.right;
     AVLNode y = x.left;
     x.left = p;
     p.right = y;
     p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
     x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
     return x;
    }

    private AVLNode rotateRight(AVLNode p){
     AVLNode x = p.left;
     AVLNode y = x.right;
     x.right = p;
     p.left = y;
     p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
     x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
     return x;
    }

    private AVLNode rebalance(AVLNode n){
    if (n == null){return null;}
    n.height = 1 + Math.max(getHeight(n.left), getHeight(n.right));
    int BF = BF(n);
     if (BF > 1){ // left-heavy
        if (getHeight(n.left.left) >= getHeight(n.left.right)) // simple left-rotation
         {n = rotateRight(n);}
        else // left-heavy but left subtree is right-heavy: LR rotation
         {
          n.left = rotateLeft(n.left);
          n = rotateRight(n);
         }
      }
     else if (BF < -1){ // right-heavy
        if (getHeight(n.right.right) >= getHeight(n.right.left)) // simple right-rotation
         {n = rotateLeft(n);}
        else // right-heavy but right subtree is left-heavy: RL rotation
         {
          n.right = rotateRight(n.right);
          n = rotateLeft(n);
         }
     }
    return n;
    }

    public AVLNode insert(AVLNode n, String insTitle){
    if (n == null){return new AVLNode(insTitle);} // insert new node straightaway
    int cmp = n.title.compareTo(insTitle);
    if (cmp > 0){n.left = insert(n.left, insTitle);} // recurse left
    else if (cmp < 0){n.right = insert(n.right, insTitle);} // recurse right
    else {System.out.println("Error: title exists in records."); return n;}
    //System.out.println("DEBUG: insert finished, now to rebalance. current tree:");
    return(rebalance(n)); // recursive rebalance at every stage ensures balanced tree at the end :D
    }

    public AVLNode delete(AVLNode n, String delTitle){
    if (n == null){return n;}
    int cmp = n.title.compareTo(delTitle);
    if (cmp > 0){n.left = delete(n.left, delTitle);} // recurse left
    else if (cmp < 0){n.right = delete(n.right, delTitle);} // recurse right
    else { // found node to be deleted
      if (n.left == null || n.right == null) // node doesn't have two children; can replace it by its single child, or directly delete if has no children
       {
        n = (n.left == null) ? n.right : n.left;
       }
      else // tree is filled on both sides
       {
            AVLNode leftmost = n.right; // get leftmost child of right subtree
            while (leftmost.left != null) 
            {
                leftmost = leftmost.left;
            }
        n.title = leftmost.title; // set the current node's title to be that child's
        n.right = delete(n.right, n.title); // delete that child out of right subtree (here it's guaranteed to be a leaf)
       }
    }
    return(rebalance(n)); // recursive rebalance at every stage ensures balanced tree at the end :D
    }

    public AVLNode Search(String srchTitle){
    AVLNode curr = root;
    
    while (curr!=null){
     int cmp = (curr.title).compareTo(srchTitle);
     if (cmp == 0){break;}
     else if (cmp < 0){curr = curr.right;}
     else {curr = curr.left;}
     }
    return curr;    
    }

    public void PrefixSearch(String srchTitle){
    AVLNode curr = root;
    while (curr!=null){
     if (curr.title.startsWith(srchTitle)){System.out.println(curr.title);}
     int cmp = (curr.title).compareTo(srchTitle);
     if (cmp > 0){curr = curr.left;}
     else {curr = curr.right;}
     }
    }

    void preOrderTraverse(AVLNode n) 
     { 
         if (n != null) 
          { 
            System.out.print(n.title + ", "); 
            preOrderTraverse(n.left); 
            preOrderTraverse(n.right); 
          }
     }

    void alphabeticTraverse(AVLNode n) 
     { 
         if (n != null) 
          { 
            alphabeticTraverse(n.left); 
            System.out.print(n.title + ", "); 
            alphabeticTraverse(n.right); 
          }
     }


    void printTree(AVLNode n) 
     { 
         if (n != null) 
          { 
            System.out.print(n.title + ", "); 
            System.out.print("Left child of " + n.title + ":");  printTree(n.left); 
            System.out.print("Right child of "+ n.title + ":");  printTree(n.right); 
          }
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

        // Borrow queue object
        BorrowQueue borrowQueue = new BorrowQueue();

        int option;
        System.out.print("Welcome to the library management system.\nFeatures available are as follows:\n" + "1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Book purchase calculator\n7. Exit\n");

        while (true) {
            System.out.print("Select an option number: ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Options available:");
                System.out.print("1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Book purchase calculator\n7. Exit\n");
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
                    System.out.println("Borrow/Return Book & Records");
                    System.out.println("1. Borrow a book");
                    System.out.println("2. View borrow records");
                    System.out.println("3. Return a book");
                    int borrowChoice = sc.nextInt();
                    sc.nextLine();

                    if (borrowChoice == 1) {
                        System.out.println("Select a genre:");
                        for (int i = 0; i < genres.length; i++) {
                            System.out.println((i + 1) + ". " + genres[i]);
                        }
                        int borrowGenre = sc.nextInt();
                        sc.nextLine();

                        if (borrowGenre < 1 || borrowGenre > genres.length) {
                            System.out.println("Invalid genre choice.");
                            break;
                        }

                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = sc.nextLine();

                        if (genreLists[borrowGenre - 1].search(borrowTitle)) {
                            System.out.print("Enter your name: ");
                            String user = sc.nextLine();
                            borrowQueue.enqueue(user, borrowTitle, genres[borrowGenre - 1]);
                        } else {
                            System.out.println("Book not found in " + genres[borrowGenre - 1] + " genre.");
                        }

                    } else if (borrowChoice == 2) {
                        borrowQueue.displayQueue();
                    } else if (borrowChoice == 3) {
                        borrowQueue.dequeue();
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;


               case 4:
                   AVLTree tree = new AVLTree();

                   try
                   { 
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

                       System.out.println("Please enter a title to search for: ");
                       String userInput = sc.nextLine();
                       AVLTree.AVLNode result = tree.Search(userInput);
    
                       if(result != null){System.out.println("Found in library: " + result.title);} else {System.out.println("Title not found.");}

                       System.out.println("Lexicographic tree traversal:");
                       tree.alphabeticTraverse(tree.root); 
                       
/* TODO: fully implement Prefix-based search
                       System.out.println("\nPrefix Search: 'bo'");
                       tree.PrefixSearch("bo");
*/
                  }

                  catch (Exception e)
                  {
                     e.printStackTrace();
                  }
                  
                 break;


                case 5:
                    System.out.println("Book transport from branch\nSorry, this feature has not been implemented yet!");
                    break;

                case 6:
                    System.out.println("Calculate optimal purchase from budgetnSorry, this feature has not been implemented yet!");
                    break;

                case 7:
                    System.out.println("Have a nice day!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid input. Options available:");
                    System.out.print("1. Book Search\n2. Manage Books\n3. Borrow/Return book & check records\n4. Title-based search (AVL)\n5. Branch transport\n6. Book purchase calculator\n7. Exit\n");
            }
        }
    }
}
