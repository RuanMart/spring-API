package com.devsuperior.dscatalog.services.exception;

import java.io.Serial;

public class ResourceAlreadyExists extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExists(String msg) {
        super(msg);
    }
}
