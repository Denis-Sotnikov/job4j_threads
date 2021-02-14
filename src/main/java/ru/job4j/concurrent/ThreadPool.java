package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() throws InterruptedException {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                    }));
            threads.get(i).start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        notifyAll();
    }

    public void shutdown() throws InterruptedException {
            for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
                    threads.get(i).interrupt();
                }
            }

    private static class MyTask implements Runnable {
        int id;

        public MyTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("work " + id + " done");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.tasks.offer(new MyTask(i));
        }
        pool.shutdown();
    }
}