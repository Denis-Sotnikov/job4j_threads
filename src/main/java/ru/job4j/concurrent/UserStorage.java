package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    Map<Integer, User> mapUsers = new HashMap<>();

    public synchronized void transfer(int fromId, int toId, int amount) {
        User first = mapUsers.get(fromId);
        User second = mapUsers.get(toId);
        first.setAmount(first.getAmount() - amount);
        second.setAmount(second.getAmount() + amount);
    }

    public synchronized boolean add(User user) {
        return mapUsers.put(user.getId(), user) != null;
    }
    public synchronized boolean update(User user) {
        return mapUsers.put(user.getId(), user) != null;
    }
    public synchronized boolean delete(User user) {
        return mapUsers.remove(user.getId()) != null;
    }
}
