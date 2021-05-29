package com.iit.lab2.persist.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class RestException extends Exception {
    private final HttpStatus status;
    private final String type;

    public RestException(HttpStatus status, String message, String type) {
        super(message);
        this.status = status;
        this.type = type;
        log.warn(message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
