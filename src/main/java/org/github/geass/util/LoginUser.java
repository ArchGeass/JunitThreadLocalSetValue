package org.github.geass.util;

import java.util.HashMap;
import java.util.Map;

public class LoginUser {

    private static ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal<>();

    public static void setAttribute(String key, Object value) {
        if (null == LOCAL.get()) {
            Map<String, Object> map = new HashMap<>();
            map.put(key, value);
            LOCAL.set(map);
        } else {
            LOCAL.get().put(key, value);
        }
    }

    public static Object getAttribute(String key) {
        if (null == LOCAL.get()) {
            return null;
        }
        return LOCAL.get().get(key);
    }
}
