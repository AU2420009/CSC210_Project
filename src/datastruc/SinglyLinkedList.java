package datastruc;

class Node {
    String title;
    Node next;

    public Node(String title) {
        this.title = title;
        this.next = null;
    }
}

public class SinglyLinkedList {
    public Node head;

    // Insert at end
    public void insert(String title) {
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
    public boolean search(String key) {
        Node temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(key)) return true;
            temp = temp.next;
        }
        return false;
    }

    // Print list
    public void display() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.title + " -> ");
            temp = temp.next;
        }
        System.out.println("NULL");
    }

    public boolean delete(String key) {
        if (head == null) return false;

        // If deleting the head
        if (head.title.equalsIgnoreCase(key)) {
            head = head.next;
            return true;
        }

        Node temp = head;
        while (temp.next != null) {
            if (temp.next.title.equalsIgnoreCase(key)) {
                temp.next = temp.next.next;  // unlink node
                return true;
            }
            temp = temp.next;
        }
        return false; // not found
    }


}

