package ru.job4j.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        memory.computeIfPresent(model.getId(), (integer, base) -> {
            if (base.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
               Base newModel = new Base(model.getId(), model.getVersion() + 1);
               newModel.setName(model.getName());
               base = newModel;
            return base;
        });
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}