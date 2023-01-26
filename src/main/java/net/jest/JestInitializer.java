package net.jest;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.jest.api.JestBootstrap;
import net.jest.implementation.RealJestServer;

@UtilityClass
public class JestInitializer {

    /**
     * Initializing and launching Jest Server instance.
     *
     * @param bootClass class annotated with @JestBootstrap
     * @return - Running JestServer instance
     */
    public JestServer bootServer(@NonNull Class<?> bootClass) {
        JestBootstrap bootstrap = bootClass.getAnnotation(JestBootstrap.class);

        if (bootstrap == null)
            throw new IllegalArgumentException("Not found @JestBootstrap annotation at " + bootClass.getName());

        RealJestServer jestServer = new RealJestServer();

        jestServer.setHost(bootstrap.hostName());
        jestServer.setPort(bootstrap.port());

        jestServer.run();

        return jestServer;
    }
}
