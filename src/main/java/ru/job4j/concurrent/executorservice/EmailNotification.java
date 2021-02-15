package ru.job4j.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
       String subject = new String();
       subject = format("Notification %s to email %s", user.getUsername(), user.getEmail());
       String body = format("Add a new event to %s", user.getUsername());
       send(subject, body, user.getEmail());
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
       pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.pool.submit(new Runnable() {
            @Override
            public void run() {
                emailNotification.emailTo(new User("Vasia", "vasia@mail.ru"));
            }
        });
        emailNotification.close();
    }
}
