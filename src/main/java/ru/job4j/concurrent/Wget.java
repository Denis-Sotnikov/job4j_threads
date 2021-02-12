package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileOutput;

    public Wget(String url, int speed, String fileOutput) {
        this.url = url;
        this.speed = speed;
        this.fileOutput = fileOutput;
    }

    @Override
    public void run() {
        String file = url;
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileOutput)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            System.out.println("speed = " + this.speed);
            int speedLimit = this.speed;
            long pause = 0;
            long timeFirst = new Date().getTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long result = new Date().getTime() - timeFirst - pause;
                timeFirst = new Date().getTime();
                pause = result < this.speed ? this.speed - result : 0;
                System.out.println("result = " + result);
                System.out.println("pause = " + pause);
                Thread.sleep(pause);
            }
            System.out.println("Done");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(args.length);
        String url;
        int speed;
        String fileOutput;
        if (args.length >= 1) {
            url = args[0];
        } else {
            System.out.println("Необходимо задать аргументы. Использовано значение по умолчанию");
            url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        }

        if (args.length >= 2) {
            speed = Integer.parseInt(args[1]);
        } else {
            System.out.println("Необходимо задать аргументы. Использовано значение по умолчанию");
            speed = 200;
        }

        if (args.length >= 3) {
            fileOutput = args[2];
        } else {
            System.out.println("Необходимо задать аргументы. Использовано значение по умолчанию");
            fileOutput = "file.txt";
        }
        Thread wget = new Thread(new Wget(url, speed, fileOutput));
        wget.start();
        wget.join();
    }
}
