package com.devsuperior.dscatalog.services.exception;

import java.io.Serial;

public class DataBaseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DataBaseException(String msg) {
        super(msg);
    }
}
