package org.example.context;

import java.util.HashMap;
import java.util.Map;

public class AppContext {

    private AppContext() {
    }

    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    public static <T> T get(Class<T> type) {
        return (T) CONTEXT.get(type);
    }

    public static <T> void put(Class<T> type, T value) {
        CONTEXT.put(type, value);
    }
}
