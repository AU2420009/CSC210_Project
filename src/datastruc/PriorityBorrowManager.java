package datastruc;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;


// Borrow record for priority system
class BorrowRecord {
    String user;
    String bookTitle;
    String genre;
    int priority;       // lower number = higher priority (min-heap)
    long timestamp;     // for tie-breaking (earlier first)
    boolean active;     // true = currently borrowed; false = returned (lazy deletion)
    String key;         // unique map key, e.g., user + "|" + bookTitle

    public BorrowRecord(String user, String bookTitle, String genre, int priority) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.priority = priority;
        this.timestamp = System.currentTimeMillis();
        this.active = true;
        this.key = user + "|" + bookTitle; // if same user can borrow same title multiple times, include timestamp/UUID
    }

    public void PrintServed(){
     if (this != null){
        System.out.println("Served: " + this.user + " for \"" + this.bookTitle + "\".");
   }
}

}


// PriorityBorrowManager: PriorityQueue + HashMap with lazy deletion
public class PriorityBorrowManager {
    private PriorityQueue<BorrowRecord> pq;
    private HashMap<String, BorrowRecord> map; // active records only

    public PriorityBorrowManager() {
        // Min-heap comparator: lower priority number first; then earlier timestamp first
        pq = new PriorityQueue<>(new Comparator<BorrowRecord>() {
            @Override
            public int compare(BorrowRecord a, BorrowRecord b) {
                if (a.priority != b.priority) return Integer.compare(a.priority, b.priority); // smaller priority = higher priority
                return Long.compare(a.timestamp, b.timestamp); // older request first
            }
        });
        map = new HashMap<>();
    }

    // Borrow (enqueue)
    public boolean borrow(String user, String bookTitle, String genre, int priority) {
        String key = user + "|" + bookTitle;
        if (map.containsKey(key)) {
            System.out.println("You already have an active borrow request for this book.");
            return false;
        }
        BorrowRecord r = new BorrowRecord(user, bookTitle, genre, priority);
        // Overwrite key to guarantee uniqueness per (user,book). If needed, change key to include timestamp/UUID.
        r.key = key;
        pq.add(r);
        map.put(key, r);
        System.out.println(user + " queued to borrow \"" + bookTitle + "\" (priority " + priority + ").");
        rebuildPQIfNeeded(); // optional maintenance
        return true;
    }

    // Return specific book by user (lazy deletion)
    public boolean returnByUser(String user, String bookTitle, SinglyLinkedList[] genreLists, String[] genres) {
        String key = user + "|" + bookTitle;
        BorrowRecord r = map.remove(key);
        if (r == null) {
            System.out.println("No active borrow record found for " + user + " and \"" + bookTitle + "\".");
            return false;
        }
        r.active = false; // mark inactive; will be skipped when polled
        // find genre index to reinsert book
        int gi = findGenreIndex(genres, r.genre);
        if (gi == -1) gi = 0; // fallback to first genre if something weird
        genreLists[gi].insert(bookTitle);
        System.out.println(user + " has returned \"" + bookTitle + "\" to " + r.genre + ".");
        rebuildPQIfNeeded();
        return true;
    }

public String pollNextActiveMessage() {
    while (true) {
        BorrowRecord r = pq.poll();
        if (r == null) {
            return "No pending borrow requests.";
        }
        if (r.active) {
            map.remove(r.key);
            r.active = false;
            return "Next active request: " + r.user + " -> \"" + r.bookTitle + "\" (" + r.genre + "), priority:" + r.priority;
        }
    }
}
/*
    // Poll next active borrow (serve next request)
    public BorrowRecord pollNextActive() {
        while (true) {
            BorrowRecord r = pq.poll(); // O(log m)
            if (r == null) {
                System.out.println("No pending borrow requests.");
                return null;
            }
            if (r.active) {
                // cleanup map if still present
                map.remove(r.key);
                System.out.println("Next active request: " + r.user + " -> \"" + r.bookTitle + "\" (" + r.genre + "), priority:" + r.priority);
                return r;
            }
            // else inactive (returned) — skip and continue
        }
    }
*/
    // Display active records ordered by priority (makes a sorted copy)
    public void displayActiveOrdered() {
        if (map.isEmpty()) {
            System.out.println("No active borrow records.");
            return;
        }
        List<BorrowRecord> list = new ArrayList<>(map.values());
        list.sort(new Comparator<BorrowRecord>() {
            @Override
            public int compare(BorrowRecord a, BorrowRecord b) {
                if (a.priority != b.priority) return Integer.compare(a.priority, b.priority);
                return Long.compare(a.timestamp, b.timestamp);
            }
        });
        System.out.println("Active borrow records (ordered):");
        for (BorrowRecord r : list) {
            System.out.println("- " + r.user + " -> \"" + r.bookTitle + "\" (" + r.genre + ") priority:" + r.priority);
        }
    }

    // Display active records unordered (fast)
    public void displayActiveUnordered() {
        if (map.isEmpty()) {
            System.out.println("No active borrow records.");
            return;
        }
        System.out.println("Active borrow records (unordered):");
        for (BorrowRecord r : map.values()) {
            System.out.println("- " + r.user + " -> \"" + r.bookTitle + "\" (" + r.genre + ") priority:" + r.priority);
        }
    }

    // Rebuild PQ if inactive nodes grow too large compared to active map
    private void rebuildPQIfNeeded() {
        if (pq.size() > 2 * map.size() + 50) { // threshold: heuristic
            PriorityQueue<BorrowRecord> newPQ = new PriorityQueue<>(pq.comparator());
            for (BorrowRecord r : map.values()) {
                newPQ.add(r);
            }
            pq = newPQ;
            System.out.println("[Maintenance] Rebuilt priority queue to remove inactive nodes.");
        }
    }

    // helper to find genre index by name
    private int findGenreIndex(String[] genres, String genreName) {
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equalsIgnoreCase(genreName)) return i;
        }
        return -1;
    }

public static int computePriority(String role) {

        if (role == null) return 3;

        role = role.trim().toLowerCase();

        switch (role) {

            case "faculty":

            case "professor":

                return 1;

            case "staff":

                return 2;

            case "student":

            default:

                return 3;

        }

}


}


/* OLD IMPLEMENTATION
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
*/
