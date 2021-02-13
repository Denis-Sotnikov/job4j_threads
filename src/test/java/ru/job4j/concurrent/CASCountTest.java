package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CASCountTest {

    @Test
    public void whenIncrement708timeUsingTwoStream() throws InterruptedException {
        CASCount count = new CASCount();
        final Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 200; i++) {
                        count.increment();
                    }
                }
        );
        final Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 508; i++) {
                        count.increment();
                    }

                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
        assertThat(708, is(count.get()));
    }

    @Test
    public void whenIncrement800000timeUsingTwoStream() throws InterruptedException {
        CASCount count = new CASCount();
        final Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 400000; i++) {
                        count.increment();
                    }
                }
        );
        final Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 400000; i++) {
                        count.increment();
                    }

                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
        assertThat(800000, is(count.get()));
    }

}