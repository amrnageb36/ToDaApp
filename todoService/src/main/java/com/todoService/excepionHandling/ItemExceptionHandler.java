package com.todoService.excepionHandling;

import com.todoService.model.ItemExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ItemExceptionResponse> handlerForItemNotFound(ItemNotFoundException exception){
        ItemExceptionResponse itemExceptionResponse = new ItemExceptionResponse();

        itemExceptionResponse.setCode(HttpStatus.NOT_FOUND);
        itemExceptionResponse.setMessage(exception.getMessage());
        itemExceptionResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(itemExceptionResponse,HttpStatus.NOT_FOUND);

    }
}
