package net.jest.test;

import net.jest.JestInitializer;
import net.jest.JestServer;
import net.jest.api.JestBootstrap;
import net.jest.test.controller.MetricController;

@JestBootstrap(port = 8080, hostName = "localhost")
public class Main {

    public static void main(String[] args) {
        JestServer jest = JestInitializer.bootServer(Main.class);
        jest.registerRestController(MetricController.class);
    }
}
