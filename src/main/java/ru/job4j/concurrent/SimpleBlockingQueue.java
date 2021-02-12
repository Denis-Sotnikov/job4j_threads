package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.add(value)) {
            notifyAll();
            if (this.queue.size() == 5) {
                System.out.println("Producer заснул");
                wait();
            }
        }
    }

    public synchronized T peek() {
        return queue.peek();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.peek() == null) {
            System.out.println("Нить погрузилась в сон here");
            this.wait();
        }
        notifyAll();
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + " started");
                        System.out.println("Нить начала работу");
                        try {
                            System.out.println(blockingQueue.poll() + " " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Thread second = new Thread(
                () -> {
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + " started");
                        System.out.println("Нить начала работу");
                        try {
                            System.out.println(blockingQueue.poll() + " " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Thread three = new Thread(
                () -> {
                    while (true) {
                        System.out.println("Producer начал работу");
                        try {
                            blockingQueue.offer((int) (Math.random() * 10));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        three.start();
    }
}