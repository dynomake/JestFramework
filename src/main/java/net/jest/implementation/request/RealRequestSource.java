package net.jest.implementation.request;

import com.sun.net.httpserver.HttpExchange;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.*;

import net.jest.api.RequestSource;
import sun.net.www.ParseUtil;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RealRequestSource implements RequestSource {

    Map<Object, List<Object>> parameters;
    HttpExchange exchange;

    /**
     * Getting and parsing query parameter to needed type.
     * If query don't has parameter ll return null;
     */
    public <T> T parseParameter(@NonNull Class<T> parameterClass, @NonNull String parameterName) {
        try {
            if (parameterClass.equals(int.class) || parameterClass.equals(long.class) || parameterClass.equals(short.class)) {
                return (T) (Object) Integer.parseInt(parameters.get(parameterName).get(0).toString());
            }

            if (parameterClass.isAssignableFrom(Collection.class)) return (T) parameters.get(parameterName);
            return (T) parameters.get(parameterName).stream().findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getHeader(@NonNull String parameterName) {
        return exchange.getRequestHeaders().getFirst(parameterName);
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return exchange.getRemoteAddress();
    }

    @Override
    public HttpExchange getHandle() {
        return exchange;
    }

    /**
     * Check for contains query parameter in this request.
     *
     * @param parameterName - name parameter
     * @return - has this parameter?
     */
    public boolean containsParameter(@NonNull String parameterName) {
        return parameters.containsKey(parameterName);
    }

    @Override
    public String getParameter(@NonNull String parameterName) {
        return Objects.requireNonNull(parameters.get(parameterName).stream().findFirst().orElse(null)).toString();
    }

    public static RealRequestSource fromExchange(HttpExchange exchange) {
        String query = exchange.getRequestURI().getRawQuery();;
        if (query == null || "".equals(query) || "null".equals(query)) {
            return new RealRequestSource(Collections.emptyMap(), exchange);
        }

        return new RealRequestSource(Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> (Object)ParseUtil.decode(s[0]), mapping(s -> (Object)ParseUtil.decode(s[1]), toList()))), exchange);
    }

}
