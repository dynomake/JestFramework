package net.jest.implementation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.NonNull;
import lombok.Setter;
import net.jest.JestServer;
import net.jest.api.Controller;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;
import net.jest.reflect.MethodParser;
import net.jest.request.Request;
import net.jest.util.ExchangeUtil;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import static net.jest.util.DebugUtil.print;

@Setter
public class RealJestServer implements JestServer {

    // TODO : fix that bad solution
    private final Map<String, Function<Request, Response>> patchRequestHandlerMap = new LinkedHashMap<>();

    private int port;
    private String host;

    @Override
    public void registerRestController(@NonNull Class<?> controllerClass) {
        Controller controller = controllerClass.getDeclaredAnnotation(Controller.class);

        if (controller == null)
            throw new IllegalArgumentException(controllerClass.getName() + " don't has @Controller annotation!");
        try {
            Object controllerInstance = controllerClass.newInstance();

            for (Method method : controllerClass.getDeclaredMethods()) {

                Function<Request, Response> function = MethodParser.parse(controller, controllerInstance, method);
                String path = MethodParser.getName(controller, method);

                if (function != null) {
                    patchRequestHandlerMap.put(path, function);
                    print("Success registered %s restful-method", path);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void registerRestController(@NonNull Object instance) {
        Controller controller = instance.getClass().getDeclaredAnnotation(Controller.class);

        if (controller == null)
            throw new IllegalArgumentException(instance.getClass().getName() + " don't has @Controller annotation!");
        try {

            for (Method method : instance.getClass().getDeclaredMethods()) {

                Function<Request, Response> function = MethodParser.parse(controller, instance, method);
                String path = MethodParser.getName(controller, method);

                if (function != null) {
                    patchRequestHandlerMap.put(path, function);
                    print("Success registered %s restful-method.", path);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        print("Try boot Jest-Server instance on ip `%s:%s`", host, port);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
            server.createContext("/", (exchange -> {
                // metric
                long startMills = System.currentTimeMillis();

                String path = exchange.getRequestURI().getRawPath();
                String query = exchange.getRequestURI().getRawQuery();

                print("Try handle new request: `%s/%s`", path, query);

                Response response = findResponse(path, exchange);

                ExchangeUtil.returnString(exchange, response.getOutput(), response.getStatusCode());

                print("Success handle request: `%s/%s`, with %sms", path, query,
                        System.currentTimeMillis() - startMills);

            }));
            server.setExecutor(null);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Response findResponse(@NonNull String path, HttpExchange exchange) {
        Function<Request, Response> method = patchRequestHandlerMap.get(path.toLowerCase());
        return method == null ? ResponseUtil.createResponse(ResponseUtil.NOT_FOUND, "This method was not found.")
                : method.apply(Request.fromExchange(exchange));
    }
}
