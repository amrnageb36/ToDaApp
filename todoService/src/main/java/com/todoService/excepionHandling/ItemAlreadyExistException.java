package com.todoService.excepionHandling;

public class ItemAlreadyExistException extends RuntimeException {
    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
