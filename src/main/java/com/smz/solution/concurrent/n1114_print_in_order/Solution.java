package main.java.com.smz.solution.concurrent.n1114_print_in_order;

import java.util.concurrent.atomic.AtomicInteger;

class Solution {
    public static void main(String[] args) {
        Foo f = new Foo();

        new Thread(() -> {
            try {
                f.first(() -> {
                    System.out.print("first");
                });
            } catch (InterruptedException e) {
                System.out.println("Exception thrown.");
            }
        }).start();
        new Thread(() -> {
            try {
                f.second(() -> {
                    System.out.print("second");
                });
            } catch (InterruptedException e) {
                System.out.println("Exception thrown.");
            }
        }).start();
        new Thread(() -> {
            try {
                f.third(() -> {
                    System.out.print("third");
                });
            } catch (InterruptedException e) {
                System.out.println("Exception thrown.");
            }
        }).start();

    }

}

// [法三]：锁 + wait/notify + 计数
class Foo {
    public volatile int opCount = 0;
    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (this) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            opCount++;
            this.notifyAll();
        }
    }

    public synchronized  void second(Runnable printSecond) throws InterruptedException {
        while (opCount != 1) {
            wait();
        }

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        opCount++;
        this.notifyAll();
    }

    public synchronized  void third(Runnable printThird) throws InterruptedException {
        while (opCount != 2) {
            wait();
        }

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        opCount++;
    }
}

// [法二]：无锁 + 原子类计数决定顺序
class Foo2 {
    public volatile AtomicInteger opCount =  new AtomicInteger(0);
    public Foo2() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            opCount.getAndIncrement();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (opCount.get() != 1) {
        }

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        opCount.getAndIncrement();
        
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (opCount.get() != 2) {
        }

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        opCount.getAndIncrement();
    }
}


// [法一]：锁 + 计数决定顺序
class Foo1 {
    public volatile int opCount = 0;
    public final Object lock = new Object();

    public Foo1() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            opCount++;
        }
        
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (opCount != 1) {
        }

        synchronized (lock) {
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            opCount++;
        }
        
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (opCount != 2) {
        }

        synchronized (lock) {
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            opCount++;
        }
    }


}