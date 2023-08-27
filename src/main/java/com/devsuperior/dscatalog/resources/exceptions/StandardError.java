package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Instant timestamp;

    private Integer status;

    private String error;

    private String path;

    private String message;

    public StandardError() {

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
