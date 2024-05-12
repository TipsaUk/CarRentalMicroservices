package ru.tipsauk.rental.exception;

public class EntityOperationException extends RuntimeException{

    public EntityOperationException() {
        super();
    }

    public EntityOperationException(String message) {
        super(message);
    }
}
