package ru.job4j.concurrent.switcher;

public class Switcher {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    synchronized (Switcher.class) {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.println("Thread A");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                        Switcher.class.notifyAll();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    synchronized (Switcher.class) {
                        if (!first.isInterrupted()) {
                            try {
                                Switcher.class.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                        while (true) {
                            System.out.println("Thread B");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
        );
        second.start();
        first.start();
        Thread.sleep(5000);
        first.interrupt();
        first.join();
        second.join();
    }
}