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

