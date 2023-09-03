package com.devsuperior.dscatalog.services.exception;

import java.io.Serial;

public class RecordAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException(String msg) {
        super(msg);
    }
}
