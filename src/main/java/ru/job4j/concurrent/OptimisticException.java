package ru.job4j.concurrent;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}