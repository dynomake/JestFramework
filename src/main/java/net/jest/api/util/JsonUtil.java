package net.jest.api.util;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

    private final Gson parser = new Gson();

    public String to(@NonNull Object object) {
        return parser.toJson(object);
    }

    public <T> T from(@NonNull String s, Class<T> tClass) {
        return parser.fromJson(s, tClass);
    }
}