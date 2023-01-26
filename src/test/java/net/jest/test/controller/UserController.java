package net.jest.test.controller;

import net.jest.api.Controller;
import net.jest.api.Method;
import net.jest.api.Parameter;
import net.jest.api.response.Response;
import net.jest.api.util.ResponseUtil;

@Controller(path = "/api/users")
public class UserController {

    @Method(name = "/get-name", type = "POST")
    public Response getUserName(@Parameter("id") int id) {
        return ResponseUtil.createResponse(ResponseUtil.OK, "User id is " + id + ", this is test method.");
    }
}
