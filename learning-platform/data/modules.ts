import { Module } from "@/types";

export const modules: Module[] = [
  {
    id: "linked-lists",
    title: "Singly Linked Lists",
    description: "Learn about linear data structures and how to implement singly linked lists",
    icon: "🔗",
    estimatedTime: "45 min",
    lessons: [
      {
        id: "ll-intro",
        title: "Introduction to Linked Lists",
        description: "Understanding the basics of linked lists and their advantages",
        content: `# Introduction to Linked Lists

A **Singly Linked List** is a linear data structure where each element (node) contains:
- **Data**: The value stored in the node
- **Next pointer**: A reference to the next node in the sequence

## Why Use Linked Lists?

Unlike arrays, linked lists offer:
- **Dynamic size**: No need to specify size upfront
- **Efficient insertions/deletions**: O(1) time at the beginning
- **Memory efficiency**: Only allocate memory as needed

## Basic Structure

Each node in a singly linked list contains two parts:
1. The data field
2. A pointer to the next node

The last node points to NULL, indicating the end of the list.`,
        codeExample: `class Node {
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
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }
}`,
        quiz: [
          {
            id: "q1",
            question: "What is the time complexity of inserting at the beginning of a singly linked list?",
            options: ["O(1)", "O(n)", "O(log n)", "O(n²)"],
            correctAnswer: 0,
            explanation: "Inserting at the beginning is O(1) because we only need to update the head pointer."
          },
          {
            id: "q2",
            question: "What does the 'next' pointer of the last node point to?",
            options: ["The first node", "Itself", "NULL", "The previous node"],
            correctAnswer: 2,
            explanation: "The last node's next pointer is NULL, indicating the end of the list."
          }
        ]
      },
      {
        id: "ll-operations",
        title: "Linked List Operations",
        description: "Learn how to perform common operations on linked lists",
        content: `# Linked List Operations

## Common Operations

### 1. Insertion
- **At beginning**: O(1) - Update head pointer
- **At end**: O(n) - Traverse to last node
- **At position**: O(n) - Traverse to position

### 2. Deletion
- **From beginning**: O(1) - Update head pointer
- **From end**: O(n) - Traverse to second-last node
- **By value**: O(n) - Search and remove

### 3. Search
- **Linear search**: O(n) - Check each node sequentially

### 4. Display
- **Traverse**: O(n) - Visit each node once

## Best Practices

- Always check if the list is empty before operations
- Handle edge cases (single node, empty list)
- Update pointers carefully to avoid losing references`,
        codeExample: `// Search operation
boolean search(String key) {
    Node temp = head;
    while (temp != null) {
        if (temp.title.equalsIgnoreCase(key)) {
            return true;
        }
        temp = temp.next;
    }
    return false;
}

// Display operation
void display() {
    Node temp = head;
    while (temp != null) {
        System.out.print(temp.title + " -> ");
        temp = temp.next;
    }
    System.out.println("NULL");
}`,
        quiz: [
          {
            id: "q3",
            question: "What is the time complexity of searching for an element in a singly linked list?",
            options: ["O(1)", "O(log n)", "O(n)", "O(n log n)"],
            correctAnswer: 2,
            explanation: "Searching requires traversing the list sequentially, which takes O(n) time in the worst case."
          }
        ]
      }
    ]
  },
  {
    id: "queues",
    title: "Queue Data Structure",
    description: "Master the FIFO (First In First Out) data structure",
    icon: "📋",
    estimatedTime: "40 min",
    lessons: [
      {
        id: "queue-intro",
        title: "Introduction to Queues",
        description: "Understanding queue operations and applications",
        content: `# Introduction to Queues

A **Queue** is a linear data structure that follows the **FIFO** (First In First Out) principle.

## Key Characteristics

- **Enqueue**: Add element at the rear
- **Dequeue**: Remove element from the front
- **Front**: Points to the first element
- **Rear**: Points to the last element

## Real-World Applications

1. **Task scheduling**: Operating systems use queues for process management
2. **Print spooling**: Documents wait in queue to be printed
3. **Breadth-First Search**: Graph traversal algorithm
4. **Library systems**: Managing book borrowing requests

## Queue Operations

- **enqueue(item)**: Add item to rear - O(1)
- **dequeue()**: Remove item from front - O(1)
- **peek()**: View front item without removing - O(1)
- **isEmpty()**: Check if queue is empty - O(1)`,
        codeExample: `class BorrowQueue {
    private static class QNode {
        String borrowerName;
        String bookTitle;
        String genre;
        QNode next;

        public QNode(String borrower, String book, String genre) {
            this.borrowerName = borrower;
            this.bookTitle = book;
            this.genre = genre;
            this.next = null;
        }
    }

    QNode front, rear;

    public boolean isEmpty() {
        return front == null;
    }

    // Enqueue operation
    public void enqueue(String borrower, String book, String genre) {
        QNode newNode = new QNode(borrower, book, genre);
        if (rear == null) {
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }
}`,
        quiz: [
          {
            id: "q4",
            question: "What principle does a queue follow?",
            options: ["LIFO", "FIFO", "LILO", "Random Access"],
            correctAnswer: 1,
            explanation: "Queue follows FIFO (First In First Out) - the first element added is the first to be removed."
          },
          {
            id: "q5",
            question: "What is the time complexity of enqueue operation?",
            options: ["O(n)", "O(log n)", "O(1)", "O(n²)"],
            correctAnswer: 2,
            explanation: "Enqueue is O(1) as we simply add to the rear without traversing the queue."
          }
        ]
      }
    ]
  },
  {
    id: "avl-trees",
    title: "AVL Trees",
    description: "Explore self-balancing binary search trees",
    icon: "🌳",
    estimatedTime: "60 min",
    lessons: [
      {
        id: "avl-intro",
        title: "Introduction to AVL Trees",
        description: "Understanding self-balancing binary search trees",
        content: `# Introduction to AVL Trees

An **AVL Tree** is a self-balancing Binary Search Tree where the difference between heights of left and right subtrees cannot be more than one for all nodes.

## Why AVL Trees?

Regular BSTs can become unbalanced, leading to O(n) operations. AVL trees maintain balance to ensure O(log n) operations.

## Balance Factor

**Balance Factor** = Height(Left Subtree) - Height(Right Subtree)

For an AVL tree, balance factor must be -1, 0, or 1.

## Key Operations

1. **Insertion**: O(log n) - Insert and rebalance
2. **Deletion**: O(log n) - Delete and rebalance
3. **Search**: O(log n) - Binary search
4. **Rotation**: Used to maintain balance

## Types of Rotations

- **Left Rotation**: When right subtree is heavy
- **Right Rotation**: When left subtree is heavy
- **Left-Right Rotation**: Double rotation
- **Right-Left Rotation**: Double rotation`,
        codeExample: `class AVLTree {
    public class AVLNode {
        String title;
        int height;
        AVLNode left;
        AVLNode right;
        
        AVLNode(String title) {
            this.title = title;
            this.height = 1;
        }
    }

    public AVLNode root;

    public int getHeight(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    public int getBalanceFactor(AVLNode node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode rotateRight(AVLNode p) {
        AVLNode x = p.left;
        AVLNode y = x.right;
        x.right = p;
        p.left = y;
        p.height = 1 + Math.max(getHeight(p.left), getHeight(p.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        return x;
    }
}`,
        quiz: [
          {
            id: "q6",
            question: "What is the maximum allowed balance factor in an AVL tree?",
            options: ["0", "1", "2", "3"],
            correctAnswer: 1,
            explanation: "AVL trees maintain a balance factor of -1, 0, or 1 to ensure the tree remains balanced."
          },
          {
            id: "q7",
            question: "What is the time complexity of search in an AVL tree?",
            options: ["O(1)", "O(log n)", "O(n)", "O(n log n)"],
            correctAnswer: 1,
            explanation: "AVL trees maintain balance, ensuring search operations are O(log n)."
          }
        ]
      }
    ]
  }
];
