package com.mynotes.mynotes.exceptions.advice;

import com.mynotes.mynotes.exceptions.FunctionNotAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(RuntimeException e) {
        e.printStackTrace();
        return "{\"succesful\": \"false\", \"cause\": \"" + e.getClass().getSimpleName() + "\"}";
    }

    @ExceptionHandler(FunctionNotAllowed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleFunctionNotAllowed(RuntimeException e) {
        e.printStackTrace();
        return "{\"succesful\": \"false\", \"cause\": \"Method currently not allowed\"}";
    }

}
