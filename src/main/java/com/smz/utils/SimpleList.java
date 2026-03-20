package com.smz.utils;

public abstract class SimpleList<T> {
    public SimpleListNode<T> head;
    
    public static abstract class SimpleListNode<T> {
        public T val;
        public SimpleListNode<T> next;

        public SimpleListNode(T x) {
            val = x;
        }
    }
}