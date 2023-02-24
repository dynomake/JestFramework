package net.jest.implementation;

import com.sun.net.httpserver.*;
import lombok.NonNull;
import lombok.Setter;
import net.jest.JestServer;
import net.jest.api.Controller;
import net.jest.api.TlsSecure;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;
import net.jest.reflect.MethodParser;
import net.jest.implementation.request.RealRequestSource;
import net.jest.util.ExchangeUtil;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import static net.jest.util.DebugUtil.print;

@Setter
public class RealJestServer implements JestServer {

    // TODO : fix that bad solution
    private final Map<String, Function<RealRequestSource, Response>> patchRequestHandlerMap = new LinkedHashMap<>();

    private int port;
    private TlsSecure tls;
    private String defaultPath;

    private String host;

    @Override
    public void registerRestController(@NonNull Class<?> controllerClass) {
        Controller controller = controllerClass.getDeclaredAnnotation(Controller.class);

        if (controller == null)
            throw new IllegalArgumentException(controllerClass.getName() + " don't has @Controller annotation!");
        try {
            Object controllerInstance = controllerClass.newInstance();

            for (Method method : controllerClass.getDeclaredMethods()) {

                Function<RealRequestSource, Response> function = MethodParser.parse(controller, controllerInstance, method);
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

                Function<RealRequestSource, Response> function = MethodParser.parse(controller, instance, method);
                String path = defaultPath + MethodParser.getName(controller, method);

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
            HttpServer server = tls != null ? HttpsServer.create(new InetSocketAddress(host, port), 0) : HttpServer.create(new InetSocketAddress(host, port), 0);

            if (tls != null) initTLS((HttpsServer) server, tls);

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

    private static void initTLS(@NonNull HttpsServer httpsServer, @NonNull TlsSecure tlsSecure) throws Exception {
        SSLContext SSL_Context = SSLContext.getInstance("TLS");

        // initialise the keystore
        char[] Password = tlsSecure.password().toCharArray();
        KeyStore Key_Store = KeyStore.getInstance(tlsSecure.keyStore());
        FileInputStream Input_Stream = new FileInputStream(tlsSecure.certificateFile());
        Key_Store.load(Input_Stream, Password);

        // setup the key manager factory
        KeyManagerFactory Key_Manager = KeyManagerFactory.getInstance(tlsSecure.algorithm());
        Key_Manager.init(Key_Store, Password);

        // setup the trust manager factory
        TrustManagerFactory Trust_Manager = TrustManagerFactory.getInstance(tlsSecure.algorithm());
        Trust_Manager.init(Key_Store);

        // setup the HTTPS context and parameters
        SSL_Context.init(Key_Manager.getKeyManagers(), Trust_Manager.getTrustManagers(), null);
        httpsServer.setHttpsConfigurator(new HttpsConfigurator(SSL_Context) {
            public void configure(HttpsParameters params) {
                try {
                    // initialise the SSL context
                    SSLContext SSL_Context = getSSLContext();
                    SSLEngine SSL_Engine = SSL_Context.createSSLEngine();
                    params.setNeedClientAuth(true);
                    params.setCipherSuites(SSL_Engine.getEnabledCipherSuites());
                    params.setProtocols(SSL_Engine.getEnabledProtocols());

                    // Set the SSL parameters
                    SSLParameters SSL_Parameters = SSL_Context.getSupportedSSLParameters();
                    params.setSSLParameters(SSL_Parameters);
                } catch (Exception ex) {
                }
            }
        });
    }


    private Response findResponse(@NonNull String path, HttpExchange exchange) {
        Function<RealRequestSource, Response> method = patchRequestHandlerMap.get(path.toLowerCase());
        return method == null ? ResponseUtil.createResponse(ResponseUtil.NOT_FOUND, "This method was not found.")
                : method.apply(RealRequestSource.fromExchange(exchange));
    }
}
