package com.murun.fict.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Error")
public class InternalErrorException extends RuntimeException {


    private static final long serialVersionUID = -3332292346834265371L;

    public InternalErrorException(Throwable e, String location, String message) {
        super(location + ": " + message, e);
    }
}

