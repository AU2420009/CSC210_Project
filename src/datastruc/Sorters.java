package datastruc;
public class Sorters{
public static Node Sort(Node head) {
    head = mergeSort(head);
    return head;
}

// Recursive merge sort
private static Node mergeSort(Node h) {
    if (h == null || h.next == null)
        return h;

    // Split the list into two halves
    Node middle = getMiddle(h);
    Node nextOfMiddle = middle.next;

    middle.next = null;  // break the list

    // Apply mergeSort on both halves
    Node left = mergeSort(h);
    Node right = mergeSort(nextOfMiddle);

    // Merge sorted halves
    Node sortedList = sortedMerge(left, right);
    return sortedList;
}

// Merge two sorted lists
private static Node sortedMerge(Node a, Node b) {
    if (a == null)
        return b;
    if (b == null)
        return a;

    Node result;

    // Compare titles (case-insensitive alphabetical order)
    if (a.title.compareToIgnoreCase(b.title) <= 0) {
        result = a;
        result.next = sortedMerge(a.next, b);
    } else {
        result = b;
        result.next = sortedMerge(a, b.next);
    }
    return result;
}

// Find middle node using slow/fast pointer method
private static Node getMiddle(Node head) {
    if (head == null)
        return head;

    Node slow = head, fast = head.next;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
}

