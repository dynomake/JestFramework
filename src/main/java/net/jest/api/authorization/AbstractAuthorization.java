package net.jest.api.authorization;

import com.sun.net.httpserver.HttpExchange;
import lombok.NonNull;
import net.jest.api.RequestSource;

public interface AbstractAuthorization {

    boolean isAuthorized(@NonNull HttpExchange exchange, @NonNull RequestSource requestSource);
}
