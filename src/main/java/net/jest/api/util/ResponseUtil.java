package net.jest.api.util;

import lombok.experimental.UtilityClass;
import net.jest.api.response.Response;
import net.jest.api.response.SimpleResponse;

@UtilityClass
public class ResponseUtil {

    // Informational responses
    public final int CONTINUE = 100;
    public final int SWITCHING_PROTOCOLS = 101;
    public final int PROCESSING = 102;
    public final int EARLY_HINTS = 103;

    // Successful responses
    public final int OK = 200;
    public final int CREATED = 201;
    public final int ACCEPTED = 202;
    public final int NON_AUTHORITATIVE_INFORMATION = 203;
    public final int NO_CONTEXT = 204;
    public final int RESET_CONTEXT = 205;
    public final int PARTIAL_CONTEXT = 205;

    // Redirection messages
    public final int MULTIPLE_CHOICES = 300;
    public final int MOVED_PERMANENTLY = 301;
    public final int FOUND = 302;
    public final int SEE_OTHER = 303;
    public final int NOT_MODIFIED = 304;
    public final int USE_PROXY = 305;
    public final int TEMPORARY_REDIRECT = 307;
    public final int PERMANENT_REDIRECT = 308;

    // Client error responses
    public final int BAD_REQUEST = 400;
    public final int UNAUTHORIZED = 401;
    public final int PAYMENT_REQUIRED = 402;
    public final int FORBIDDEN = 403;
    public final int NOT_FOUND = 404;
    public final int METHOD_NOT_ALLOWED = 405;
    public final int NOT_ACCEPTABLE = 406;
    public final int PROXY_AUTHENTICATION_REQUIRED = 407;
    public final int REQUEST_TIMEOUT = 408;
    public final int CONFLICT = 409;
    public final int GONE = 410; // more later..

    // Server error responses
    public final int INTERNAL_SERVER_ERROR = 500;
    public final int NOT_IMPLEMENTED = 501;
    public final int BAD_GATEWAY = 502;
    public final int SERVICE_UNAVAILABLE = 503;
    public final int GATEWAY_TIMEOUT = 504;


    public Response createResponse(int statusCode, Object object) {
        return new SimpleResponse(statusCode, object);
    }


}
