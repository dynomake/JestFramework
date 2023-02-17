package net.jest.test.controller;

import net.jest.api.*;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;
import net.jest.test.authorization.TokenAuthorization;

@Controller(path = "/api/metric")
public class MetricController {

    private int count;

    @Parameter("count")
    @Authorization(TokenAuthorization.class)
    @Method(name = "/add", type = "POST")
    public Response add(RequestSource request) {
        count+=request.parseParameter(int.class, "count");
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @Parameter("count")
    @Authorization(TokenAuthorization.class)
    @Method(name = "/subtract", type = "POST")
    public Response subtract(RequestSource request) {
        count-=request.parseParameter(int.class, "count");
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @Parameter("one")
    @Parameter("two")
    @Authorization(TokenAuthorization.class)
    @Method(name = "/print-and-print")
    public Response test(RequestSource request) {
        System.out.println("TEXT1: " + request.getParameter("one"));
        System.out.println("TEXT2: " + request.getParameter("two"));

        return ResponseUtil.createResponse(ResponseUtil.OK, "Success");
    }


    @Method(name = "/get", type = "GET")
    public Response get() {
        return ResponseUtil.createResponse(ResponseUtil.OK, count);
    }
}
