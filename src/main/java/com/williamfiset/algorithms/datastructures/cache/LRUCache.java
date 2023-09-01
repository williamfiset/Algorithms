package com.williamfiset.algorithms.datastructures.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K,V>{

    private final Map<K, Node<K,V>> map = new HashMap<>();
    private final Node<K,V> head;
    private final Node<K,V> last;
    private final int capacity;

    public LRUCache(int capacity){
        this.capacity = capacity;
        head = new Node<>(null,null);
        last = new Node<>(null,null);
        head.next = last;
        last.prev = head;
    }

    public V get(K key){
        Node<K,V> node = map.get(key);
        if (node == null){
            return null;
        }
        remove(node);
        insert(node);
        return node.value;
    }


    public void put(K key, V value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(last.prev);
        }
        insert(new Node<>(key, value));
    }

    private void insert(Node<K,V> node) {
        map.put(node.key, node);
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    private void remove(Node<K,V> node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }


    private static class Node<K,V>{
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "LRUCache{" +
                 map.keySet() +
                '}';
    }

    public static void main(String[] args) {
        LRUCache<Integer,Integer> lruCache = new LRUCache<>(3);
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);

        lruCache.put(4,4);

        //should print 2,3,4
        System.out.println(lruCache);

    }

}
