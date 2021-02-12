package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder output = null;
            while (reader.ready()) {
                output.append(reader.readLine());
            }
            return output.toString();
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        try (InputStream i = new FileInputStream(file)) {
            StringBuilder output = null;
            int data;
            while ((data = i.read()) > 0) {
                if (data < 128) {
                    output.append((char) data);
                }
            }
        return output.toString();
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file);
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))
        ) {
            writer.write(content);
        }
    }
}
