package net.jest.test.authorization;

import com.sun.net.httpserver.HttpExchange;
import net.jest.api.RequestSource;
import net.jest.api.authorization.AbstractAuthorization;

public class TokenAuthorization implements AbstractAuthorization {

    @Override
    public boolean isAuthorized(HttpExchange exchange, RequestSource requestSource) {
        return requestSource.containsParameter("token") &&
                requestSource.getParameter("token").equals("nope");
    }
}
