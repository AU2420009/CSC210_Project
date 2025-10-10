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

