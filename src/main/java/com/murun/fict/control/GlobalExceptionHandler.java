package com.murun.fict.control;


import com.murun.fict.exception.InternalErrorException;
import com.murun.fict.exception.NotFoundException;
import com.murun.fict.model.ErrorResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final HttpHeaders headers = new HttpHeaders();

    static{
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(RuntimeException e, WebRequest request){
        ErrorResource error = new ErrorResource("InvalidRequest", e.getMessage());
        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e, WebRequest request){
        ErrorResource error = new ErrorResource("NotFound", e.getMessage());
        return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ IllegalStateException.class, InternalErrorException.class })
    public ResponseEntity<Object> internalErrorException(RuntimeException e, WebRequest request){
        ErrorResource error = new ErrorResource("InternalErrorException", e.getMessage());
        return handleExceptionInternal(e, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}
