package net.jest.api;

import com.sun.net.httpserver.HttpExchange;
import lombok.NonNull;

import java.net.InetSocketAddress;

public interface RequestSource {


    /**
     * Check for contains query parameter in this request.
     *
     * @param parameterName - name parameter
     * @return - has this parameter?
     */
    boolean containsParameter(@NonNull String parameterName);


    /**
     * Getting the value (def: String) from the request parameters.
     *
     * @param parameterName - required parameter
     * @return - parameter value
     */
    String getParameter(@NonNull String parameterName);

    /**
     * Getting and parsing query parameter to needed type.
     * If query don't has parameter ll return null;
     */
    public <T> T parseParameter(@NonNull Class<T> parameterClass, @NonNull String parameterName);

    /**
     * Getting the value (def: String) from the request headers.
     *
     * @param parameterName - required parameter
     * @return - parameter value
     */
    String getHeader(@NonNull String parameterName);

    /**
     * Getting the value (def: String) from the request headers.
     *
     * @return - remote InetSocketAddress
     */
    InetSocketAddress getRemoteAddress();

    /**
     * If you want to get some other data, just
     * get it from the HttpExchange
     * from the sun.server...
     *
     * @return - default's sun httpexchange
     */
    HttpExchange getHandle();
}
