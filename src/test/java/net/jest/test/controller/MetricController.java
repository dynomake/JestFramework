package net.jest.test.controller;

import net.jest.api.Controller;
import net.jest.api.Method;
import net.jest.api.Parameter;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;

@Controller(path = "/api/metric")
public class MetricController {

    private int count;

    @Method(name = "/add", type = "POST")
    public Response add(@Parameter("count") int toAdd) {
        count+=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }

    @Method(name = "/subtract", type = "POST")
    public Response subtract(@Parameter("count") int toAdd) {
        count-=toAdd;
        return ResponseUtil.createResponse(ResponseUtil.OK, "new count = " + count);
    }
}
