package com.devsuperior.dscatalog.services.exception;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
