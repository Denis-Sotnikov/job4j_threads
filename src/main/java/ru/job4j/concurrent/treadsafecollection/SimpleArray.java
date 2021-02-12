package ru.job4j.concurrent.treadsafecollection;

import java.util.*;

public class SimpleArray<T> implements Iterable<T>, Cloneable {
    private Object[] array;
    private int index = 0;
    private int modCount = 0;

    public SimpleArray() {
        array = new Object[10];
    }

    public SimpleArray(int val) {
        array = new Object[val];
    }

    public T get(int position) {
        Objects.checkIndex(position, index);
        return (T) array[position];
    }

    public void add(T model) {
        increasingArray();
        array[index] = model;
        index++;
        modCount++;
    }

    public void remove(int index) {
        array[index] = null;
    }

    public int size() {
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int count = 0;
            private int saveCount = modCount;
            @Override
            public boolean hasNext() {
                if (saveCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index > count;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) array[count++];
            }
        };
    }

    private void increasingArray() {
        if (!(index < array.length)) {
            array = Arrays.copyOf(array, array.length + 10);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        SimpleArray<Integer> array = new SimpleArray<>();
        array.iterator().next();
        array.add(1);
        array.add(2);
        System.out.println(array.array.length);
        array.add(3);
        System.out.println(array.array.length);
    }
}