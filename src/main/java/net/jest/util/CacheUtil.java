package net.jest.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class CacheUtil {

    public static final Map<Class<?>, Object> classInstanceMap = new LinkedHashMap<>();

    public <T> T putIfAbsent(@NonNull Class<T> tClass) {
        try { return (T) classInstanceMap.putIfAbsent(tClass, tClass.newInstance()); }
        catch (Exception exception) { return null; }
    }
}
