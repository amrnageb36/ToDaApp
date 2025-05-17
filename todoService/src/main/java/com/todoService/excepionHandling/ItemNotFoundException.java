package com.todoService.excepionHandling;


public class ItemNotFoundException extends RuntimeException{

        public ItemNotFoundException(String message) {
                super(message);
        }
}
