package com.example.demo.exception;

public class AppException extends RuntimeException {
    public AppException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
