package net.jest.api;

public @interface TlsSecure {
    String password();
    String keyStore();
    String certificateFile();
    String algorithm();
}
