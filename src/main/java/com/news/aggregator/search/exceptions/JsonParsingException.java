package com.news.aggregator.search.exceptions;

public class JsonParsingException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public JsonParsingException(String message) {
        super(message);
    }
}
