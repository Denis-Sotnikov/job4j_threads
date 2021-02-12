package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> mapUsers = new HashMap<>();

    public synchronized void transfer(int fromId, int toId, int amount) {
        if (mapUsers.get(fromId) != null && mapUsers.get(toId) != null) {
            User first = mapUsers.get(fromId);
            User second = mapUsers.get(toId);
            if (first.getAmount() >= amount) {
                first.setAmount(first.getAmount() - amount);
                second.setAmount(second.getAmount() + amount);
                System.out.println("Транзакция исполнена");
            } else {
                System.out.println("Транзакция не исполнена");
            }
        } else {
            System.out.println("Транзакция не исполнена");
        }
    }

    public synchronized boolean add(User user) {
        return mapUsers.putIfAbsent(user.getId(), user) == null;
    }
    public synchronized boolean update(User user) {
        return mapUsers.replace(user.getId(), user) != null;
    }
    public synchronized boolean delete(User user) {
        return mapUsers.remove(user.getId(), user);
    }
}
