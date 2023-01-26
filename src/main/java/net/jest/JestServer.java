package net.jest;

import lombok.NonNull;

public interface JestServer {

    /**
     * Registration of a controller that is necessarily
     * marked with the @Controller annotation and has
     * an empty constructor!
     *
     * @param controllerClass - controller class
     */
    void registerRestController(@NonNull Class<?> controllerClass);

    /**
     * The method responsible for starting the server
     * instance does not need to be called, because it starts itself when creating
     * a new server instance via JestBootsrap.
     */
    void run();
}
