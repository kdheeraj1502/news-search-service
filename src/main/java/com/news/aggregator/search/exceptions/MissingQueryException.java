package com.news.aggregator.search.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingQueryException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public MissingQueryException(String message) {
        super(message);
    }
}
