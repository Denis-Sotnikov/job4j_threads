package ru.job4j.concurrent;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() throws InterruptedException {
        count++;
        System.out.println("count = " + count);
        if (count != total) {
            notifyAll();
        }

    }

    public synchronized void await() throws InterruptedException {
        System.out.println("нить зашла");
        while (count != total) {
                this.wait();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(3);
        Thread first = new Thread(
                () -> {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " started");
                    System.out.println("Нить начала работу");
                }
        );

        Thread second = new Thread(
                () -> {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " started");
                    System.out.println("Нить начала работу");
                }
        );

        first.start();
        second.start();
        barrier.count();
        barrier.count();
        barrier.count();
    }
}