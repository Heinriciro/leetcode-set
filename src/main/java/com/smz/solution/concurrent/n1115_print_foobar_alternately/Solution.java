package main.java.com.smz.solution.concurrent.n1115_print_foobar_alternately;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Solution {
    public static void main(String[] args) throws InterruptedException {
        int[] testNs = {
            5,
            1,
            2,
        };

        Runnable printFoo = () -> System.out.print("foo");
        Runnable printBar = () -> System.out.print("bar");


        for (int i = 0; i < testNs.length; i++) {
            // Semaphore done = new Semaphore(-1);
            CountDownLatch done = new CountDownLatch(2);
            System.out.println("No."+i+" test case: ");
            FooBar f = new FooBar(testNs[i]);
            new Thread(() -> {
                try {
                    f.foo(printFoo);
                } catch (InterruptedException e) {
                    System.out.println("Exception raised.");
                } finally {
                    // done.release();
                    done.countDown();
                }
            }).start();
            new Thread(() -> {
                try {
                    f.bar(printBar);
                } catch (InterruptedException e) {
                    System.out.println("Exception raised.");
                } finally {
                    done.countDown();
                }
            }).start();

            done.await();
            System.out.println();
        }
    }
}

// [法五]：ReentrantLock， 与synchronized本质一样，不过使用两个condition减少了线程重复等待的开销
class FooBar {
    private int n;
    private ReentrantLock lock = new ReentrantLock();
    private Condition first = lock.newCondition();
    private Condition second = lock.newCondition();
    private volatile boolean done = false;


    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                while (done) {
                    first.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                done = true;
                second.signal();
            } finally {
                lock.unlock();
            }
        }
    }
    
    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            try {
                lock.lock();
                while (!done) {
                    second.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printBar.run();
                done = false;
                first.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

// [法四]：两个BlockingQueue，和两个semaphore本质一样
class FooBar4 {
    private int n;

    private BlockingQueue<Boolean> firstDone = new ArrayBlockingQueue<>(1);
    private BlockingQueue<Boolean> secondDone = new ArrayBlockingQueue<>(1);


    public FooBar4(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            firstDone.put(true);
            printFoo.run();
            secondDone.put(true);
        }
    }
    
    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            secondDone.take();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printBar.run();
            firstDone.take();
        }
    }
}

// [法三]：两个Semaphore分别控制两个线程的执行顺序
class FooBar3 {
    private int n;
    Semaphore firstQ = new Semaphore(1);
    Semaphore secondQ = new Semaphore(0);

    public FooBar3(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            firstQ.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            secondQ.release();
        }
    }
    
    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            secondQ.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printBar.run();
            firstQ.release();
        }
    }
}

// [法二]：synchronized + volatile flag + wait/notifyAll
class FooBar2 {
    private int n;
    private volatile boolean firstDone = false;

    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (firstDone) {
                    this.wait();
                }

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                firstDone = true;
                this.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
           
            synchronized (this) {
                while (!firstDone) {
                    this.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                firstDone = false;
                this.notifyAll();
            }
        }
    }
}

// [法一]：自旋 + Atomic Flag
class FooBar1 {
    private int n;
    private volatile AtomicBoolean firstDone = new AtomicBoolean(false);

    public FooBar1(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            
            while(firstDone.get()) {
                Thread.yield();
            }
        	// printFoo.run() outputs "foo". Do not change or remove this line.
        	printFoo.run();
            firstDone.set(true);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
           
            while (!firstDone.get()) {
                Thread.yield();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
        	printBar.run();
            firstDone.set(false);
        }
    }
}
