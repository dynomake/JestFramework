package net.jest.test.controller;

import net.jest.api.Controller;
import net.jest.api.Method;
import net.jest.api.Parameter;
import net.jest.api.RequiredAuth;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;
import net.jest.test.authorization.TokenAuthorization;

@Controller(path = "/api/metric")
public class MetricController {

    private int count;

    @RequiredAuth(TokenAuthorization.class)
    @Method(name = "/add", type = "POST")
    public Response add(@Parameter("count") int toAdd) {
        count+=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @RequiredAuth(TokenAuthorization.class)
    @Method(name = "/subtract", type = "POST")
    public Response subtract(@Parameter("count") int toAdd) {
        count-=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @RequiredAuth(TokenAuthorization.class)
    @Method(name = "/print-and-print")
    public Response test(@Parameter("text1") String text1, @Parameter("text") String text) {
        System.out.println("TEXT1: " + text1);
        System.out.println("TEXT2: " + text);

        return ResponseUtil.createResponse(ResponseUtil.OK, "Success");
    }


    @Method(name = "/get", type = "POST")
    public Response get() {
        return ResponseUtil.createResponse(ResponseUtil.OK, count);
    }
}
