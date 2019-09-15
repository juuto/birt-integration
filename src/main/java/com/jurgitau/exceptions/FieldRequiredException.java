package com.jurgitau.exceptions;

import org.apache.wicket.WicketRuntimeException;

public class FieldRequiredException extends WicketRuntimeException {

    public FieldRequiredException()
    {
        super();
    }

    public FieldRequiredException(final String fieldName) {
        super("Choose '" + fieldName + "'");
    }

}
