package com.smz.solution.concurrent.n1116_print_zero_even_odd;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class Solution {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        
        int[] testNs = {
            2,
            5,
        };

        IntConsumer printer = (i) -> System.out.print(i);
        CyclicBarrier barrier = new CyclicBarrier(4);

        for (int i = 0; i < testNs.length; i++) {
            ZeroEvenOdd z = new ZeroEvenOdd(testNs[i]);
            barrier.reset();
            System.out.println("No."+i+" test case: ");
            new Thread(
                () -> {
                    try {
                        z.zero(printer);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        System.getLogger(Solution.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            ).start();
            new Thread(
                () -> {
                    try {
                        z.odd(printer);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        System.getLogger(Solution.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            ).start();
            new Thread(
                () -> {
                    try {
                        z.even(printer);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        System.getLogger(Solution.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            ).start();
            barrier.await();
            System.out.println();
        }
    }
}

// [法一]: 三个Semaphore，由输出0的线程决定 奇数还是偶数线程被唤醒
class ZeroEvenOdd {
    private int n;
    private Semaphore zero = new Semaphore(1);
    private Semaphore odd = new Semaphore(0);
    private Semaphore even = new Semaphore(0);
    
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            zero.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) odd.release();
            else even.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
        } 
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }
}