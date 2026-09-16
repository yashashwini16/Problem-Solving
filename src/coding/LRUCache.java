package coding;

/**
 * PROBLEM 5: Custom LRU (Least Recently Used) Cache
 * 
 * Implement an LRU Cache to optimize API response memory footprints.
 * The cache should support get and put operations in O(1) time complexity.
 * 
 * Data Structures:
 * - HashMap: For O(1) lookup of nodes.
 * - Doubly Linked List: For O(1) updating of nodes' order (least recently used at tail, most recently used at head).
 */
public class LRUCache<K, V> {

    // TODO: Define the Doubly Linked List Node structure
    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> prev;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        // TODO: Initialize map and dummy head/tail nodes for doubly linked list
    }

    /**
     * Retrieves the value of the key if the key exists in the cache, otherwise returns null.
     * Moves the accessed node to the head of the doubly linked list (marking it as most recently used).
     */
    public V get(K key) {
        // TODO: Implement get operation in O(1)
        return null;
    }

    /**
     * Inserts or updates the value of the key.
     * If the cache reaches capacity, evict the least recently used item (at the tail of the list) 
     * before inserting the new item.
     */
    public void put(K key, V value) {
        // TODO: Implement put operation in O(1)
    }

    // Helper methods to implement:
    // private void addToHead(Node<K, V> node)
    // private void removeNode(Node<K, V> node)
    // private void moveToHead(Node<K, V> node)
    // private Node<K, V> popTail()
}
