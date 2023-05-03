package com.news.aggregator.search.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sun.security.pkcs.ParsingException;

@ControllerAdvice
public class NewsCustomExceptionHandler extends ResponseEntityExceptionHandler  {
    private final static String INCORRECT_REQUEST = "INCORRECT_REQUEST";
    private final static String BAD_REQUEST = "BAD_REQUEST";
    private final static String PARSING_FAILED = "BAD_REQUEST";

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonParsingException.class)
    public final ResponseEntity<ErrorResponse> handleParseException
            (JsonParsingException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(PARSING_FAILED, details);
        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(MissingQueryException.class)
    public final ResponseEntity<ErrorResponse> handleParseException
            (MissingQueryException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
