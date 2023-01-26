package net.jest.test;

import net.jest.JestInitializer;
import net.jest.JestServer;
import net.jest.api.JestBootstrap;
import net.jest.test.controller.UserController;

@JestBootstrap(port = 8080)
public class Main {

    public static void main(String[] args) {
        JestServer jest = JestInitializer.bootServer(Main.class);
        jest.registerRestController(UserController.class);
    }
}
