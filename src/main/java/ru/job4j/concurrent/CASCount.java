package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer i;
        do {
            i = count.get();
        } while (!count.compareAndSet(i, i + 1));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount a = new CASCount();
        final Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 200; i++) {
                        a.increment();
                    }

                    //System.out.println(a.get());
                }
        );
        final Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 508; i++) {
                        a.increment();
                    }

                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(a.get());
    }
}