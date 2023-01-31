package net.jest.test.authorization;

import com.sun.net.httpserver.HttpExchange;
import net.jest.api.authorization.AbstractAuthorization;
import net.jest.request.Request;

public class TokenAuthorization implements AbstractAuthorization {

    @Override
    public boolean isAuthorized(HttpExchange exchange, Request request) {
        return request.containsParameter("token") &&
                request.getParameter(String.class, "token").equals("nope");
    }
}
