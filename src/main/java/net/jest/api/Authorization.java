package net.jest.api;

import net.jest.api.authorization.AbstractAuthorization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    Class<? extends AbstractAuthorization> value();

}
