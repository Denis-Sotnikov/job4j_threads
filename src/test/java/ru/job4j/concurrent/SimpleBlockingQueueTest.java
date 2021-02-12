package ru.job4j.concurrent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest extends TestCase {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
        AtomicInteger value = new AtomicInteger();
        Set<Integer> rsl = new TreeSet<>();
        Thread three = new Thread(
                () -> {
                        try {
                            blockingQueue.offer(5);
                            blockingQueue.offer(10);
                            blockingQueue.offer(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
        );
        Thread first = new Thread(
                () -> {
                    try {
                        three.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (blockingQueue.peek() != null) {
                        System.out.println(Thread.currentThread().getName() + " started");
                        System.out.println("Нить начала работу");
                        try {
                            rsl.add(blockingQueue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        three.start();
        first.start();
        three.join();
        first.join();
        Assert.assertThat(rsl, is(Set.of(5, 10, 20)));
    }
}