package net.jest.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DebugUtil {

    public static void print(@NonNull String line, Object... objects) {
        if (objects == null || objects.length == 0) System.out.println("[Jest-Server] | " + line);
        else System.out.println("[Jest-Server] | " + String.format(line, objects));
    }
}
