package net.jest.api;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableParameterAnnotation.class)

public @interface Parameter {
    String value();
}

