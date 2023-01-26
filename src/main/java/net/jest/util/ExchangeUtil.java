package net.jest.util;

import com.sun.net.httpserver.HttpExchange;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.OutputStream;

@UtilityClass
public class ExchangeUtil {

    public void returnString(@NonNull HttpExchange exchange, @NonNull String text, int statusCode) {
        try {
            exchange.sendResponseHeaders(statusCode, text.getBytes().length);

            OutputStream output = exchange.getResponseBody();

            output.write(text.getBytes());
            output.flush();

            exchange.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
