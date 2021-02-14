package ru.job4j.concurrent;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {
    Cache cache = new Cache();
    @Before
    public void setUp() {
        Base first = new Base(1, 0);
        first.setName("First");
        Base second = new Base(2, 0);
        second.setName("Second");
        cache.add(first);
        cache.add(second);
    }

    @Test
    public void whenAdd() {
        Base base = new Base(3, 0);
        base.setName("Three");
        assertThat(cache.add(base), is(true));
    }

    @Test
    public void whenUpdate() {
        Base base = new Base(2, 0);
        base.setName("SecondAfterReplace");
        assertThat(cache.update(base), is(true));
    }

    @Test
    public void whenThrow() {
        Base base = new Base(2, 1);
        base.setName("SecondAfterReplace");
        assertThat(cache.update(base), is(true));
    }
}