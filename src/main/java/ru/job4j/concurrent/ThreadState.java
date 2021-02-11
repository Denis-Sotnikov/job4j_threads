package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
        );

        Thread second = new Thread(
                () -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
        );
        first.start();
//        first.join();
        second.start();
//        second.join();
        while (first.getState() != Thread.State.TERMINATED) {
             System.out.println(first.getName() + " " + first.getState());
             while (second.getState() != Thread.State.TERMINATED) {
                 System.out.println(second.getName() + " " + second.getState());
             }
        }
        System.out.println("Work done");
    }

}
