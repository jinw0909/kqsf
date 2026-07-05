package com.kqsf.global.exception;

import lombok.Getter;

@Getter
public class PageNotReadyException extends RuntimeException {

    private final String path;
    public PageNotReadyException(String path) {
        super("Page is not ready: " + path);
        this.path = path;
    }

}
