package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(100); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] array = {"\\", "|", "/"};
        int count = 0;
        for (int i = 0; i <= 100; i++) {
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    if (count == 3) {
                        count = 0;
                    }
                    System.out.print("\r load: " + "Loading ..... " + array[count]);
                    Thread.sleep(500);
                    count++;
                } catch (Exception e) {
                    Thread.currentThread().interrupt();

                }
            }
        }
    }
}
