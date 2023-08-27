package com.devsuperior.dscatalog.services.exception;

import java.io.Serial;

public class EntityNotFound extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFound(String msg) {
        super(msg);
    }
}
