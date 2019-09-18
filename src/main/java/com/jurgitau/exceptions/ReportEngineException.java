package com.jurgitau.exceptions;

public class ReportEngineException extends Exception {

    public ReportEngineException(String message) {
        super(message);
    }

    public ReportEngineException(String message, Throwable cause) {
        super(message, cause);
    }

}
