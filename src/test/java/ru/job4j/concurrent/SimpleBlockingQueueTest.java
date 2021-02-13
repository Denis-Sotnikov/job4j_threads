package ru.job4j.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    @Test
    public void whentest() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
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
        Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName() + " started");
                        System.out.println("Нить начала работу");
                        try {
                            buffer.add(blockingQueue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        if (producer.getState() == Thread.State.TERMINATED && consumer.getState() == Thread.State.WAITING) {
            consumer.interrupt();
        }
        consumer.join();
        assertThat(buffer, is(Arrays.asList(5, 10, 20)));
    }
}