package coding.solutions;

import coding.LRUCache.Node;
import java.util.Map;
import java.util.HashMap;

public class LRUCacheSolution<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    public LRUCacheSolution(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            Node<K, V> newNode = new Node<>(key, value);
            map.put(key, newNode);
            addNode(newNode);
            if (map.size() > capacity) {
                Node<K, V> tailNode = popTail();
                map.remove(tailNode.key);
            }
        }
    }

    /**
     * Always add the new node right after head.
     */
    private void addNode(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * Remove an existing node from the linked list.
     */
    private void removeNode(Node<K, V> node) {
        Node<K, V> prev = node.prev;
        Node<K, V> next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    /**
     * Move a node to the head of the list.
     */
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addNode(node);
    }

    /**
     * Pop the current tail node.
     */
    private Node<K, V> popTail() {
        Node<K, V> res = tail.prev;
        removeNode(res);
        return res;
    }
}
