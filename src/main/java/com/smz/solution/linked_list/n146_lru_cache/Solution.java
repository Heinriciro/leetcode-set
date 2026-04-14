package com.smz.solution.linked_list.n146_lru_cache;

import java.util.HashMap;
import java.util.Map;

// [法一]：哈希表 + 双向链表
class LRUCache {
    int capacity;
    int count;
    Map<Integer, LRUNode> cache; 
    LRUNode head;
    LRUNode tail;

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        head = new LRUNode();
        tail = new LRUNode();
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity; 
        this.count = 0;
    }
    
    public int get(int key) {
       if (!cache.containsKey(key)) return -1;

       LRUNode cur = cache.get(key);
       change2Top(cur); 

       return cur.val;
    }

    private void change2Top(LRUNode target) {
        if (target == null || capacity == 0) return;
        removeNode(target);
        addNode(target);
    }

    private void removeNode(LRUNode target) {
        if (target == null || capacity == 0) return;
        target.prev.next = target.next;
        target.next.prev = target.prev;
        target.prev = null;
        target.next = null;
    }

    private void addNode(LRUNode target) {
        if (target == null || capacity == 0) return;
        target.next = head.next;
        head.next.prev = target;
        head.next = target;
        target.prev = head;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        LRUNode cur;
        if (cache.containsKey(key)) {
            cur = cache.get(key);
            cur.val = value;
            change2Top(cur); 
        } else {
            cur = new LRUNode(key, value);
            if (count == capacity) {
                LRUNode target = tail.prev;
                removeNode(target);
                cache.remove(target.key);
                count--;
            }
            cache.put(key, cur);
            addNode(cur);
            count++;
        }
        
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // return 1
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // returns -1 (not found)
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // return -1 (not found)
        System.out.println(lRUCache.get(3));    // return 3
        System.out.println(lRUCache.get(4));    // return 4 
    }
}

class LRUNode {
    public int key;
    public int val;
    public LRUNode next;
    public LRUNode prev;

    public LRUNode() {}
    public LRUNode(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */