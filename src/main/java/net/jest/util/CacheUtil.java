package net.jest.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class CacheUtil {

    public static final Map<Class<?>, Object> classInstanceMap = new LinkedHashMap<>();

    public <T> T putIfAbsent(@NonNull Class<T> tClass) {
        try {
            T t  = (T) classInstanceMap.get(tClass);
            if (t == null) {
                t =  tClass.newInstance();
                classInstanceMap.put(tClass, t);
            }
            return t;
        }
        catch (Exception exception) { return null; }
    }
}
